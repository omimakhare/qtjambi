/****************************************************************************
**
** Copyright (C) 2009-2022 Dr. Peter Droste, Omix Visualization GmbH & Co. KG. All rights reserved.
**
** This file is part of Qt Jambi.
**
** ** $BEGIN_LICENSE$
** GNU Lesser General Public License Usage
** This file may be used under the terms of the GNU Lesser
** General Public License version 2.1 as published by the Free Software
** Foundation and appearing in the file LICENSE.LGPL included in the
** packaging of this file.  Please review the following information to
** ensure the GNU Lesser General Public License version 2.1 requirements
** will be met: http://www.gnu.org/licenses/old-licenses/lgpl-2.1.html.
** 
** GNU General Public License Usage
** Alternatively, this file may be used under the terms of the GNU
** General Public License version 3.0 as published by the Free Software
** Foundation and appearing in the file LICENSE.GPL included in the
** packaging of this file.  Please review the following information to
** ensure the GNU General Public License version 3.0 requirements will be
** met: http://www.gnu.org/copyleft/gpl.html.
** $END_LICENSE$

**
** This file is provided AS IS with NO WARRANTY OF ANY KIND, INCLUDING THE
** WARRANTY OF DESIGN, MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE.
**
****************************************************************************/
package io.qt.qtjambi.deployer;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLClassLoader;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.AbstractMap.SimpleEntry;
import java.util.jar.Attributes;
import java.util.jar.JarFile;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

import io.qt.core.QByteArray;
import io.qt.core.QCborMap;
import io.qt.core.QCborValue;
import io.qt.core.QCommandLineOption;
import io.qt.core.QCommandLineParser;
import io.qt.core.QDir;
import io.qt.core.QFile;
import io.qt.core.QIODevice;
import io.qt.core.QJsonDocument;
import io.qt.core.QJsonObject;
import io.qt.core.QList;
import io.qt.core.QStringList;
import io.qt.core.QTextStream;
import io.qt.core.QJsonParseError.ParseError;
import io.qt.internal.QtJambiPlugins;

class PluginGenerator {

	private static class PluginClassLoader extends URLClassLoader{
		public PluginClassLoader() {
			super(new URL[0]);
		}
		protected void addURLs(Collection<URL> urls) {
			for(URL url : urls) {
				super.addURL(url);
			}
		}
	}
	
	static void generate(QCommandLineParser parser, String[] args, QCommandLineOption dirOption, QCommandLineOption classPathOption, QCommandLineOption configurationOption) throws InterruptedException, IOException {
	    QCommandLineOption pluginIIDOption = new QCommandLineOption(QList.of("iid"), "Plugin IID", "iid");
	    QCommandLineOption pluginClassNameOption = new QCommandLineOption(QList.of("class-name"), "Class name", "name");
	    QCommandLineOption pluginNameOption = new QCommandLineOption(QList.of("plugin-name"), "Plugin name", "name");
	    QCommandLineOption pluginLibraryOption = new QCommandLineOption(QList.of("plugin-library"), "Path to qtjambiplugin library.\nExamples:\n--plugin-library=path"+File.separator+"qtjambiplugin.dll\n--plugin-library=macos"+File.pathSeparator+"path"+File.separator+"qtjambiplugin.dylib", "file");
	    QCommandLineOption pluginLibraryLocationOption = new QCommandLineOption(QList.of("plugin-library-location"), "Directory containing qtjambiplugin library", "path");
	    QCommandLineOption pluginMetaDataOption = new QCommandLineOption(QList.of("meta-data"), "Plugin meta data as json code or path to existing json file.", "json|file");
	    QCommandLineOption pluginSourceOption = new QCommandLineOption(QList.of("source"), "Generate C++ source project");
		parser.addOptions(QList.of(
	    		pluginIIDOption,
	    		configurationOption,
	    		pluginClassNameOption,
	    		pluginNameOption,
	    		pluginLibraryOption,
	    		pluginLibraryLocationOption,
	    		pluginMetaDataOption,
	    		pluginSourceOption,
	    		dirOption,
	    		classPathOption
			));
		
		if(args.length==1)
			parser.showHelp();
    	parser.process(new QStringList(args));

		QStringList unusedArguments = new QStringList(parser.positionalArguments());
		if(unusedArguments.size()==1)
			throw new Error("Qt plugin generation, illegal argument: "+unusedArguments.join(", "));
		if(unusedArguments.size()>1)
			throw new Error("Qt plugin generation, illegal argument: "+unusedArguments.join(", "));
		
		String iid = null;
		if(parser.isSet(pluginIIDOption)) 
			iid = parser.value(pluginIIDOption);
		
		String className = null;
		if(parser.isSet(pluginClassNameOption)) 
			className = parser.value(pluginClassNameOption);
		
		String pluginName = null;
		if(parser.isSet(pluginNameOption)) 
			pluginName = parser.value(pluginNameOption);
		
		File dir = null;
		if(parser.isSet(dirOption))
			dir = new File(parser.value(dirOption));
		else
			throw new Error("Missing target directory. Please use --dir=...");
		
		QJsonObject metaData = null;
		if(parser.isSet(pluginMetaDataOption)) {
			String _metaData = parser.value(pluginMetaDataOption);
			if(_metaData.startsWith("\"") && _metaData.endsWith("\"")) {
				_metaData = _metaData.substring(1, _metaData.length()-2);
			}
			QByteArray data;
			if(_metaData.startsWith("{") && _metaData.endsWith("}")) {
				data = new QByteArray(_metaData.replace("''", "\""));
			}else{
				File file = new File(_metaData);
				if(file.exists()) {
					data = new QByteArray(Files.readAllBytes(file.toPath()));
				}else {
					data = null;
				}
			}
			if(data!=null) {
				QJsonDocument.FromJsonResult result = QJsonDocument.fromJson(data);
				if(result.error.error()==ParseError.NoError && result.document.isObject()) {
					metaData = result.document.object();
				}
			}
		}
		
        Boolean isDebug = null;
        if(parser.isSet(configurationOption))
        	isDebug = "debug".equals(parser.value(configurationOption));
        
		List<String> classPath = new ArrayList<>();
		if(parser.isSet(classPathOption))
			Collections.addAll(classPath, parser.value(classPathOption).split(File.pathSeparator));
		if(classPath.isEmpty())
			throw new Error("Missing classpath. Please use --class-path=...");
		
		List<Map.Entry<String,URL>> libraries = new ArrayList<>();
		boolean generateSource = parser.isSet(pluginSourceOption);
		if(parser.isSet(pluginLibraryOption)) {
			String[] libinfo = parser.value(pluginLibraryOption).split(File.pathSeparator);
			if(libinfo.length==2){
				File libFile = new File(libinfo[1]);
				if(!libFile.isFile())
					throw new Error("Specified qtjambiplugin library does not exist: "+libinfo[1]);
				
				libraries.add(new SimpleEntry<>(libinfo[0], libFile.toURI().toURL()));
			}else {
				String os = null;
				if(libinfo[0].endsWith(".dll")) {
					os = "windows";
				}else if(libinfo[0].endsWith(".dylib")) {
					os = "macos";
				}else if(libinfo[0].endsWith(".so")) {
					os = "linux";
				}
				if(os!=null) {
					File libFile = new File(libinfo[0]);
					if(!libFile.isFile()) {
						throw new Error("Specified qtjambiplugin library does not exist: "+libinfo[0]);
					}
					libraries.add(new SimpleEntry<>(os, libFile.toURI().toURL()));
				}else {
					throw new Error("Unable to determine platform for library "+libinfo[0]+". Please use --plugin-library=<platform>"+File.pathSeparatorChar+"<path to library>");
				}
			}
		}else if(parser.isSet(pluginLibraryLocationOption)) {
			File location = new File(parser.value(pluginLibraryLocationOption));
            if(isDebug==null){
                throw new Error("Please specify --configuration=debug or --configuration=release prior to --plugin-library-location");
            }
            if(location.isDirectory())
			for(File entry : location.listFiles()) {
				if(!entry.isFile())
					continue;
                String os = null;
                if(isDebug){
                    if(entry.getName().equals("qtjambiplugind.dll")) {
                        os = "windows";
                    }else if(entry.getName().equals("libqtjambiplugin_debug.dylib")) {
                        os = "macos";
                    }else if(entry.getName().equals("libqtjambiplugin_debug.so")) {
                        os = "linux";
                    }
                }else{
                    if(entry.getName().equals("qtjambiplugin.dll")) {
                        os = "windows";
                    }else if(entry.getName().equals("libqtjambiplugin.dylib")) {
                        os = "macos";
                    }else if(entry.getName().equals("libqtjambiplugin.so")) {
                        os = "linux";
                    }
                }
                if(os!=null && entry.getName().contains("qtjambiplugin")) {
                    libraries.add(new SimpleEntry<>(os, entry.toURI().toURL()));
                }
			}
		}
        
		if(className==null || metaData==null || pluginName==null) {
			try(JarFile jarFile = new JarFile(new File(classPath.get(0)))){
				Attributes mainAttributes = jarFile.getManifest().getMainAttributes();
				if(metaData==null) {
					String _metaData = mainAttributes.getValue("QtJambi-Plugin-Metadata");
					if(_metaData!=null) {
						QByteArray data = new QByteArray(_metaData.replace("''", "\""));
						QJsonDocument.FromJsonResult result = QJsonDocument.fromJson(data);
						if(result.error.error()==ParseError.NoError && result.document.isObject()) {
							metaData = result.document.object();
						}
					}
				}
				if(className==null) {
					className = mainAttributes.getValue("QtJambi-Plugin-Class");
				}
				if(pluginName==null) {
					pluginName = mainAttributes.getValue("QtJambi-Plugin-Name");
				}
			}
		}
		if(className==null || className.isEmpty())
			throw new Error("Missing class name. Please use --class-name=...");
		if(metaData==null)
			throw new Error("Missing metadata. Please use --meta-data=...");
		
		if(libraries.isEmpty() && !generateSource) {
        	Enumeration<URL> specsFound = Main.findSpecs();

            Set<URL> specsUrls = new HashSet<>();
            while (specsFound.hasMoreElements()) {
                URL url = specsFound.nextElement();
                if(specsUrls.contains(url))
                	continue;
                specsUrls.add(url);
                String protocol = url.getProtocol();
                if (protocol.equals("jar")) {
                	String eform = url.toExternalForm();
                	int start = 4;
                	int end = eform.indexOf("!/", start);
                	if (end != -1) {
                		try {
	                        Document doc;
	                        try(InputStream inStream = url.openStream()){
	                        	DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
	                        	factory.setValidating(false);
	                			DocumentBuilder builder = factory.newDocumentBuilder();
	                			doc = builder.parse(inStream);
	                        }
	                        String system = doc.getDocumentElement().getAttribute("system");
	                        String configuration = doc.getDocumentElement().getAttribute("configuration");
	                        if(isDebug==null || isDebug.booleanValue()=="debug".equals(configuration)) {
		                        for(int i=0; i<doc.getDocumentElement().getChildNodes().getLength(); ++i) {
		                        	Node child = doc.getDocumentElement().getChildNodes().item(i);
		                        	if(child instanceof Element && child.getNodeName().equals("library")) {
		                        		Element element = (Element)child;
		                        		String library = element.getAttribute("name");
		                        		if(library.contains("qtjambiplugin")) {
		                        			URL libraryURL = new URL(eform.substring(0, end+2)+library);
		                        			libraries.add(new SimpleEntry<>(system, libraryURL));
		                        		}
		                        	}
		                        }
	                        }
                		}catch (Exception e) {
            				Logger.getLogger("io.qt").log(Level.WARNING, "", e);
            			}
                	}
                }
            }
			if(libraries.isEmpty())
				throw new Error("Missing paths to qtjambiplugin library. Please use --plugin-library=<path to library>, --plugin-library=<platform>"+File.pathSeparatorChar+"<path to library> or --plugin-library-location=<path> in combination with --configuration=debug/release");
		}
		
		if(pluginName==null) {
			if(classPath.size()==1) {
				File file = new File(classPath.get(0));
				String path = file.getName();
				if(path.endsWith(".jar")) {
					pluginName = path.substring(0, path.length()-4);
				}
			}
		}
		if(pluginName==null)
			throw new Error("Missing plugin name. Please use --plugin-name=...");
		
		if(iid==null) {
			//System.out.println("Analyzing IID...");
			//System.out.flush();
			List<URL> urls = new ArrayList<>();
			List<File> libraryPath = new ArrayList<>();
			for(String path : classPath) {
				File file = new File(path);
				if(file.exists()) {
					if(file.isFile() && !file.getName().endsWith(".jar")) {
						libraryPath.add(file.getParentFile());
					}else {
						urls.add(file.toURI().toURL());
					}
				}else {
					throw new Error("Classpath not a file or directory: "+path);
				}
			}
			StringBuilder lp = new StringBuilder(System.getProperty("java.library.path", ""));
			if(!libraryPath.isEmpty()) {
				for(File file : libraryPath) {
					if(lp.length()!=0) {
						lp.append(File.pathSeparator);
					}
					lp.append(file.getAbsolutePath());
				}
				System.setProperty("java.library.path", lp.toString());
			}
			
			@SuppressWarnings("resource")
			PluginClassLoader pluginClassLoader = new PluginClassLoader();
			pluginClassLoader.addURLs(urls);
			Class<?> cls;
			try {
				cls = pluginClassLoader.loadClass(className);
			} catch (ClassNotFoundException | NoClassDefFoundError e) {
				System.err.println("Loading class "+className+" failed. Trying to extend classpath.");
				URL url = QFile.class.getResource("QFile.class");
				if(url!=null) {
					String path = url.toString();
					int idx = -1;
					if(path.startsWith("jar:file:") && (idx = path.indexOf("!/")) >= 0) {
						path = path.substring(4, idx);
						try {
							File jarFile = new File(new URL(path).toURI());
							if(jarFile.getParentFile().isDirectory())
							for(File other : jarFile.getParentFile().listFiles()) {
								if(other.isFile() && other.getName().startsWith("qtjambi")
										 && !other.getName().endsWith("javadoc.jar")
										 && !other.getName().endsWith("sources.jar")
										 && other.getName().endsWith(".jar")) {
									urls.add(other.toURI().toURL());
								}
							}
							pluginClassLoader.close();
							pluginClassLoader = new PluginClassLoader();
							pluginClassLoader.addURLs(urls);
							cls = pluginClassLoader.loadClass(className);
						} catch (Exception e1) {
							throw new Error("Unable to find class: "+className, e1);
						}
					}else {
						System.err.println(url);
						throw new Error("Unable to find class: "+className);
					}
				}else {
					System.err.println(url);
					throw new Error("Unable to find class: "+className);
				}
			}
			iid = QtJambiPlugins.getInterfaceIID(cls);
			if(iid==null) {
				throw new Error("Unable to detect IID from class: "+cls.getName());
			}
		}
		if(iid!=null && className!=null) {
			//System.out.println("Collecting metadata...");
			//System.out.flush();
			QCborMap cborValue = new QCborMap();
			cborValue.setValue(/*QtPluginMetaDataKeys::IID*/ 2, new QCborValue(iid));
			cborValue.setValue(/*QtPluginMetaDataKeys::ClassName*/ 3, new QCborValue(className.replace(".", "::")));
			cborValue.setValue(/*QtPluginMetaDataKeys::MetaData*/ 4, new QCborValue(QCborMap.fromJsonObject(metaData)));
			cborValue.setValue(0x0_CAFEBABE_0L, new QCborValue(pluginName));
			QByteArray cborData = cborValue.toCborValue().toCbor();
			System.gc();
			if(generateSource) {
				File subdir = new File(dir, pluginName);
				subdir.mkdirs();
				if(classPath.size()==1) {
					File file = new File(classPath.get(0));
					Files.copy(file.toPath(), new File(subdir, pluginName+".jar").toPath(), StandardCopyOption.REPLACE_EXISTING);
				}else {
					subdir = new File(subdir, pluginName);
					subdir.mkdirs();
					for (String path : classPath) {
						File target = new File(subdir, path);
						target.getParentFile().mkdirs();
						Files.copy(new File(path).toPath(), target.toPath(), StandardCopyOption.REPLACE_EXISTING);
					}
				}
				generateSource(dir, pluginName, cborData);
			}else {
			
				if(classPath.size()==1) {
					File file = new File(classPath.get(0));
					Files.copy(file.toPath(), new File(dir, pluginName+".jar").toPath(), StandardCopyOption.REPLACE_EXISTING);
				}else {
					File subdir = new File(dir, pluginName);
					subdir.mkdirs();
					for (String path : classPath) {
						File target = new File(subdir, path);
						target.getParentFile().mkdirs();
						Files.copy(new File(path).toPath(), target.toPath(), StandardCopyOption.REPLACE_EXISTING);
					}
				}
				
				final QByteArray QTMETADATA;
				{
					QCborMap map = new QCborMap();
					map.insert(0xCAFEBABE, QCborValue.fromVariant(new QByteArray(1006, (byte)10)));
					map.insert(0xBABECAFE, QCborValue.fromVariant("END"));
					QTMETADATA = map.toCborValue().toCbor();
				}
				if(cborData.size()>QTMETADATA.size()) {
					throw new Error("Plugin metadata exceeds maximum size of "+QTMETADATA.size()+" byte.");
				}else if(cborData.size()<QTMETADATA.size()) {
					cborData.resize(QTMETADATA.size());
				}
				
				for(Map.Entry<String,URL> entry : libraries) {
					String os = entry.getKey();
					if(os!=null) {
						File newFile;
	                    switch(os.toLowerCase()) {
						case "win32":
						case "win64":
						case "windows":
						case "windows-aarch64":
						case "windows-arm64":
						case "windows-x86":
						case "windows-x64":
	                        if(isDebug==null){
								newFile = new File(dir, pluginName + (entry.getValue().toExternalForm().endsWith("d.dll") ? "d.dll" : ".dll"));
							}else{
								newFile = new File(dir, pluginName + (isDebug ? "d.dll" : ".dll"));
	                        }
							System.gc();
							break;
						case "macos":
						case "osx":
//	                        if(isDebug==null){
//								newFile = new File(dir, "lib" + pluginName + (entry.getValue().toExternalForm().endsWith("_debug.dylib") ? "_debug.dylib" : ".dylib"));
//							}else{
//								newFile = new File(dir, "lib" + pluginName + (isDebug ? "_debug.dylib" : ".dylib"));
//	                        }
							newFile = new File(dir, "lib" + pluginName + ".dylib");
	                        break;
						case "linux":
						case "linux32":
						case "linux64":
						case "linux-arm64":
						case "linux-aarch64":
						case "linux-x86":
						case "linux-x64":
//	                        if(isDebug==null){
//								newFile = new File(dir, "lib" + pluginName + (entry.getValue().toExternalForm().endsWith("_debug.so") ? "_debug.so" : ".so"));
//							}else{
//								newFile = new File(dir, "lib" + pluginName + (isDebug ? "_debug.so" : ".so"));
//	                        }
							newFile = new File(dir, "lib" + pluginName + ".so");
							break;
							default: continue;
						}
	                    try{
							QIODevice device = QIODevice.fromInputStream(entry.getValue().openStream());
                            QByteArray libData = device.readAll();
                            device.close();
                            int idx = (int)libData.indexOf(QTMETADATA);
                            if(idx>0) {
                            	QFile _newFile = new QFile(QDir.fromNativeSeparators(newFile.getAbsolutePath()));
                                if(_newFile.open(QIODevice.OpenModeFlag.WriteOnly)) {
                                    _newFile.write(libData);
                                	while(idx>0) {
	                                    _newFile.seek(idx);
	                                    _newFile.write(cborData);
                                    	idx = (int)libData.indexOf(QTMETADATA, idx+QTMETADATA.size());
                                	}
                                    _newFile.close();
//	                                if(_newFile.fileName().endsWith("_debug.dylib")) {
//	                                 	QFile.copy(_newFile.fileName(), _newFile.fileName().replace("_debug.dylib", ".dylib"));
//	                                }
                                }else{
                                    throw new Error("Unable to write file "+_newFile.fileName());
                                }
                            }else{
                                throw new Error("Unable to find plugin meta data in file "+entry.getValue().toExternalForm());
                            }
						}catch(IOException e) {
	                        throw new Error("Unable to read file "+entry.getValue().toExternalForm(), e);
						}
					}
				}
			}
		}
	}
	
	private static void generateSource(File dir, String pluginName, QByteArray cborData) {
		QDir subdir = new QDir(dir.getAbsolutePath());
		subdir.mkpath(pluginName);
		subdir.cd(pluginName);
		String fileName = subdir.absoluteFilePath("plugin.cpp");
		QFile filein = new QFile(":io/qt/qtjambi/deployer/plugin.cpp");
		QFile fileout = new QFile(fileName);
		if(filein.open(QIODevice.OpenModeFlag.ReadOnly)) {
			try {
				if(fileout.open(QIODevice.OpenModeFlag.WriteOnly, QIODevice.OpenModeFlag.Text)) {
					QTextStream s = new QTextStream(fileout);
					try {
						s.append("#include <QtCore/QtPlugin>").endl()
						 .append("#include <QtCore/QPluginLoader>").endl().endl()
						 .append("#define CBOR_DATA \\").endl()
						 .append("    ");
						byte[] data = cborData.toByteArray();
						for (int i = 0, j = 0; i < data.length; ++i, ++j) {
							byte b = data[i];
							if(Character.isAlphabetic(b)) {
								s.append('\'').append((char)b).append("', ");
							}else {
								String hex = Integer.toHexString(Byte.toUnsignedInt(b));
								while(hex.length()>2 && hex.charAt(0)=='0') {
									hex = hex.substring(1);
								}
								s.append("0x").append(hex).append(", ");
							}
							if(j==7) {
								s.append("\\").endl().append("    ");
								j = 0;
							}
						}
						s.endl().endl().flush();
						QTextStream in = new QTextStream(filein);
						while(!in.atEnd()) {
							s.append(in.readLine()).endl();
						}
						in.dispose();
					}finally {
						s.flush();
						s.dispose();
						fileout.close();
					}
				}else {
					throw new Error("Unable to write file "+fileName);
				}
			}finally {
				filein.close();
			}
		}else {
			throw new Error("Unable to read resource plugin.cpp");
		}
		
		
		fileName = subdir.absoluteFilePath("plugin.pro");
		fileout = new QFile(fileName);
		filein = new QFile(":io/qt/qtjambi/deployer/plugin.pro");
		if(filein.open(QIODevice.OpenModeFlag.ReadOnly)) {
			try {
				if(fileout.open(QIODevice.OpenModeFlag.WriteOnly, QIODevice.OpenModeFlag.Text)) {
					QTextStream s = new QTextStream(fileout);
					try {
						s.append("TARGET = ").append(pluginName).endl();
						QTextStream in = new QTextStream(filein);
						while(!in.atEnd()) {
							s.append(in.readLine()).endl();
						}
						in.dispose();
					}finally {
						s.flush();
						s.dispose();
						fileout.close();
					}
				}else {
					throw new Error("Unable to write file "+fileName);
				}
			}finally {
				filein.close();
			}
		}else {
			throw new Error("Unable to read resource plugin.pro");
		}
	}
}