/****************************************************************************
**
** Copyright (C) 1992-2009 Nokia. All rights reserved.
**
** This file is part of Qt Jambi.
**
** ** $BEGIN_LICENSE$
** Commercial Usage
** Licensees holding valid Qt Commercial licenses may use this file in
** accordance with the Qt Commercial License Agreement provided with the
** Software or, alternatively, in accordance with the terms contained in
** a written agreement between you and Nokia.
**
** GNU Lesser General Public License Usage
** Alternatively, this file may be used under the terms of the GNU Lesser
** General Public License version 2.1 as published by the Free Software
** Foundation and appearing in the file LICENSE.LGPL included in the
** packaging of this file.  Please review the following information to
** ensure the GNU Lesser General Public License version 2.1 requirements
** will be met: http://www.gnu.org/licenses/old-licenses/lgpl-2.1.html.
**
** In addition, as a special exception, Nokia gives you certain
** additional rights. These rights are described in the Nokia Qt LGPL
** Exception version 1.0, included in the file LGPL_EXCEPTION.txt in this
** package.
**
** GNU General Public License Usage
** Alternatively, this file may be used under the terms of the GNU
** General Public License version 3.0 as published by the Free Software
** Foundation and appearing in the file LICENSE.GPL included in the
** packaging of this file.  Please review the following information to
** ensure the GNU General Public License version 3.0 requirements will be
** met: http://www.gnu.org/copyleft/gpl.html.
**
** If you are unsure which license is appropriate for your use, please
** contact the sales department at qt-sales@nokia.com.
** $END_LICENSE$

**
** This file is provided AS IS with NO WARRANTY OF ANY KIND, INCLUDING THE
** WARRANTY OF DESIGN, MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE.
**
****************************************************************************/

package io.qt.autotests;

import io.qt.xml.*;
import static org.junit.Assert.*;
import org.junit.*;
import io.qt.internal.*;

public class TestXmlCloneable extends QApplicationTest {
    @Test
    public void run_clone_QDomAttr() {
        QDomAttr org = new QDomAttr();
        QDomAttr clone = org.clone();
		assertEquals(QtJambiInternal.Ownership.Java, QtJambiInternal.ownership(clone));
        org.dispose();
        QDomAttr clone2 = clone.clone();
		assertEquals(QtJambiInternal.Ownership.Java, QtJambiInternal.ownership(clone2));
        assertEquals(clone, clone2);
    }

    @Test
    public void run_clone_QDomCDATASection() {
        QDomCDATASection org = new QDomCDATASection();
        QDomCDATASection clone = org.clone();
		assertEquals(QtJambiInternal.Ownership.Java, QtJambiInternal.ownership(clone));
        org.dispose();
        QDomCDATASection clone2 = clone.clone();
		assertEquals(QtJambiInternal.Ownership.Java, QtJambiInternal.ownership(clone2));
        assertEquals(clone, clone2);
    }

    @Test
    public void run_clone_QDomCharacterData() {
        QDomCharacterData org = new QDomCharacterData();
        QDomCharacterData clone = org.clone();
		assertEquals(QtJambiInternal.Ownership.Java, QtJambiInternal.ownership(clone));
        org.dispose();
        QDomCharacterData clone2 = clone.clone();
		assertEquals(QtJambiInternal.Ownership.Java, QtJambiInternal.ownership(clone2));
        assertEquals(clone, clone2);
    }

    @Test
    public void run_clone_QDomComment() {
        QDomComment org = new QDomComment();
        QDomComment clone = org.clone();
		assertEquals(QtJambiInternal.Ownership.Java, QtJambiInternal.ownership(clone));
        org.dispose();
        QDomComment clone2 = clone.clone();
		assertEquals(QtJambiInternal.Ownership.Java, QtJambiInternal.ownership(clone2));
        assertEquals(clone, clone2);
    }

    @Test
    public void run_clone_QDomDocument() {
        QDomDocument org = new QDomDocument();
        QDomDocument clone = org.clone();
		assertEquals(QtJambiInternal.Ownership.Java, QtJambiInternal.ownership(clone));
        org.dispose();
        QDomDocument clone2 = clone.clone();
		assertEquals(QtJambiInternal.Ownership.Java, QtJambiInternal.ownership(clone2));
        assertEquals(clone, clone2);
    }

    @Test
    public void run_clone_QDomDocumentFragment() {
        QDomDocumentFragment org = new QDomDocumentFragment();
        QDomDocumentFragment clone = org.clone();
		assertEquals(QtJambiInternal.Ownership.Java, QtJambiInternal.ownership(clone));
        org.dispose();
        QDomDocumentFragment clone2 = clone.clone();
		assertEquals(QtJambiInternal.Ownership.Java, QtJambiInternal.ownership(clone2));
        assertEquals(clone, clone2);
    }

    @Test
    public void run_clone_QDomDocumentType() {
        QDomDocumentType org = new QDomDocumentType();
        QDomDocumentType clone = org.clone();
		assertEquals(QtJambiInternal.Ownership.Java, QtJambiInternal.ownership(clone));
        org.dispose();
        QDomDocumentType clone2 = clone.clone();
		assertEquals(QtJambiInternal.Ownership.Java, QtJambiInternal.ownership(clone2));
        assertEquals(clone, clone2);
    }

    @Test
    public void run_clone_QDomElement() {
        QDomElement org = new QDomElement();
        QDomElement clone = org.clone();
		assertEquals(QtJambiInternal.Ownership.Java, QtJambiInternal.ownership(clone));
        org.dispose();
        QDomElement clone2 = clone.clone();
		assertEquals(QtJambiInternal.Ownership.Java, QtJambiInternal.ownership(clone2));
        assertEquals(clone, clone2);
    }

    @Test
    public void run_clone_QDomEntity() {
        QDomEntity org = new QDomEntity();
        QDomEntity clone = org.clone();
		assertEquals(QtJambiInternal.Ownership.Java, QtJambiInternal.ownership(clone));
        org.dispose();
        QDomEntity clone2 = clone.clone();
		assertEquals(QtJambiInternal.Ownership.Java, QtJambiInternal.ownership(clone2));
        assertEquals(clone, clone2);
    }

    @Test
    public void run_clone_QDomEntityReference() {
        QDomEntityReference org = new QDomEntityReference();
        QDomEntityReference clone = org.clone();
		assertEquals(QtJambiInternal.Ownership.Java, QtJambiInternal.ownership(clone));
        org.dispose();
        QDomEntityReference clone2 = clone.clone();
		assertEquals(QtJambiInternal.Ownership.Java, QtJambiInternal.ownership(clone2));
        assertEquals(clone, clone2);
    }

    @Test
    public void run_clone_QDomImplementation() {
        QDomImplementation org = new QDomImplementation();
        QDomImplementation clone = org.clone();
		assertEquals(QtJambiInternal.Ownership.Java, QtJambiInternal.ownership(clone));
        org.dispose();
        QDomImplementation clone2 = clone.clone();
		assertEquals(QtJambiInternal.Ownership.Java, QtJambiInternal.ownership(clone2));
        assertEquals(clone, clone2);
    }

    @Test
    public void run_clone_QDomNamedNodeMap() {
        QDomNamedNodeMap org = new QDomNamedNodeMap();
        QDomNamedNodeMap clone = org.clone();
		assertEquals(QtJambiInternal.Ownership.Java, QtJambiInternal.ownership(clone));
        org.dispose();
        QDomNamedNodeMap clone2 = clone.clone();
		assertEquals(QtJambiInternal.Ownership.Java, QtJambiInternal.ownership(clone2));
        assertEquals(clone, clone2);
    }

    @Test
    public void run_clone_QDomNode() {
        QDomNode org = new QDomNode();
        QDomNode clone = org.clone();
		assertEquals(QtJambiInternal.Ownership.Java, QtJambiInternal.ownership(clone));
        org.dispose();
        QDomNode clone2 = clone.clone();
		assertEquals(QtJambiInternal.Ownership.Java, QtJambiInternal.ownership(clone2));
        assertEquals(clone, clone2);
    }

    @Test
    public void run_clone_QDomNodeList() {
        QDomNodeList org = new QDomNodeList();
        QDomNodeList clone = org.clone();
		assertEquals(QtJambiInternal.Ownership.Java, QtJambiInternal.ownership(clone));
        org.dispose();
        QDomNodeList clone2 = clone.clone();
		assertEquals(QtJambiInternal.Ownership.Java, QtJambiInternal.ownership(clone2));
        assertEquals(clone, clone2);
    }

    @Test
    public void run_clone_QDomNotation() {
        QDomNotation org = new QDomNotation();
        QDomNotation clone = org.clone();
		assertEquals(QtJambiInternal.Ownership.Java, QtJambiInternal.ownership(clone));
        org.dispose();
        QDomNotation clone2 = clone.clone();
		assertEquals(QtJambiInternal.Ownership.Java, QtJambiInternal.ownership(clone2));
        assertEquals(clone, clone2);
    }

    @Test
    public void run_clone_QDomProcessingInstruction() {
        QDomProcessingInstruction org = new QDomProcessingInstruction();
        QDomProcessingInstruction clone = org.clone();
		assertEquals(QtJambiInternal.Ownership.Java, QtJambiInternal.ownership(clone));
        org.dispose();
        QDomProcessingInstruction clone2 = clone.clone();
		assertEquals(QtJambiInternal.Ownership.Java, QtJambiInternal.ownership(clone2));
        assertEquals(clone, clone2);
    }

    @Test
    public void run_clone_QDomText() {
        QDomText org = new QDomText();
        QDomText clone = org.clone();
		assertEquals(QtJambiInternal.Ownership.Java, QtJambiInternal.ownership(clone));
        org.dispose();
        QDomText clone2 = clone.clone();
		assertEquals(QtJambiInternal.Ownership.Java, QtJambiInternal.ownership(clone2));
        assertEquals(clone, clone2);
    }

}