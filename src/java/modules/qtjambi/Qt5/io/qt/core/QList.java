/****************************************************************************
**
** Copyright (C) 2009-2023 Dr. Peter Droste, Omix Visualization GmbH & Co. KG. All rights reserved.
**
** This file is part of Qt Jambi.
**
** $BEGIN_LICENSE$
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
package io.qt.core;

import java.util.*;
import java.util.function.*;
import io.qt.*;

/**
 * <p>Java wrapper for Qt class <a href="https://doc.qt.io/qt/qlist.html">QList</a></p>
 */
public class QList<T> extends AbstractList<T> implements java.util.List<T>, Cloneable
{
    static {
        QtJambi_LibraryUtilities.initialize();
    }
    
    /**
     * Constructor for internal use only.
     * @param p expected to be <code>null</code>.
     */
    @NativeAccess
    protected QList(QPrivateConstructor p) {
        super(p);
    }
    
    /**
     * Creating a container with given element type.
     * <p>See <a href="https://doc.qt.io/qt/qlist.html#QList">QList::QList()</a></p>
     * @param metaType the type T
     */
    public QList(QMetaType.Type metaType) {
		this(new QMetaType(metaType));
	}
    
    /**
     * Creating a container with given element type.
     * <p>See <a href="https://doc.qt.io/qt/qlist.html#QList">QList::QList()</a></p>
     * @param elementType the type T
     */
    public QList(Class<T> elementType) {
        super(null);
        QMetaType metaType = QMetaType.fromType(elementType);
        initialize(elementType, QtJambi_LibraryUtilities.internal.nativeId(metaType), null);
    }
    
    /**
     * Creating a container with given element type.
     * <p>See <a href="https://doc.qt.io/qt/qlist.html#QList">QList::QList()</a></p>
     * @param metaType the type T
     */
    public QList(QMetaType metaType) {
        super(null);
        initialize(metaType.javaType(), QtJambi_LibraryUtilities.internal.nativeId(metaType), null);
    }
    
    /**
     * Creating a container with given content.
     * <p>See <a href="https://doc.qt.io/qt/qlist.html#QList">QList::QList(const QList&lt;T> &amp;)</a></p>
     * @param other container
     */
    public QList(Collection<T> other) {
        super(null);
        QMetaType metaType = findElementMetaType(Objects.requireNonNull(other));
        initialize(metaType.javaType(), QtJambi_LibraryUtilities.internal.nativeId(metaType), other);
    }
    
    /**
     * Creating a container of type QVariant.
     */
    public static QList<Object> createVariantList(){
        return new QList<>(new QMetaType(QMetaType.Type.QVariant));
    }

    @QtUninvokable
    private native void initialize(Class<?> elementType, long elementMetaType, Collection<T> other);
    
    /**
     * Creates and returns a copy of this object.
     * <p>See <a href="https://doc.qt.io/qt/qlist.html#QList-8">QList::QList(const QList&lt;T> &amp;)</a></p>
     */
    @Override
    public QList<T> clone(){
        return new QList<>(this);
    }
    
    @QtUninvokable
    public final void append(java.util.Collection<T> t) {
        appendList(QtJambi_LibraryUtilities.internal.nativeId(this), Objects.requireNonNull(t));
    }
    @QtUninvokable
    private static native <T> void appendList(long __this__nativeId, java.util.Collection<T> t);

    @QtUninvokable
    public final void append(T t)    {
        try {
            append(QtJambi_LibraryUtilities.internal.nativeId(this), t);
        }catch(QNoNativeResourcesException e) {
            throw e;
        }catch(RuntimeException e) {
            throw QSet.handleException(e, elementMetaType(), t);
        }
    }
	
	@QtUninvokable
    private static native <T> void append(long __this__nativeId, T t);
    
    @QtUninvokable
    public final T at(int i)    {
        return at(QtJambi_LibraryUtilities.internal.nativeId(this), i);
    }
    @QtUninvokable
    private static native <T> T at(long __this__nativeId, int i);

    @QtUninvokable
    public final void clear()    {
        clear(QtJambi_LibraryUtilities.internal.nativeId(this));
    }
    @QtUninvokable
    private static native <T> void clear(long __this__nativeId);

    @QtUninvokable
    public final boolean contains(Object t)    {
        try {
            return contains(QtJambi_LibraryUtilities.internal.nativeId(this), t);
        }catch(QNoNativeResourcesException e) {
            throw e;
        }catch(IllegalArgumentException e) {
            return false;
        }catch(RuntimeException e) {
            RuntimeException e1 = QSet.handleException(e, elementMetaType(), t);
            if(e1==e)
                throw e;
            return false;
        }
    }
    @QtUninvokable
    private static native <T> boolean contains(long __this__nativeId, T t);

    @QtUninvokable
    public final int count()    {
        return size();
    }

    @QtUninvokable
    public final int count(T t)    {
        try{
            return count(QtJambi_LibraryUtilities.internal.nativeId(this), t);
        }catch(QNoNativeResourcesException e) {
            throw e;
        }catch(RuntimeException e) {
            throw QSet.handleException(e, elementMetaType(), t);
        }
    }
    @QtUninvokable
    private static native <T> int count(long __this__nativeId, T t);

    @QtUninvokable
    protected final QSequentialIterator<T> begin()    {
        return begin(QtJambi_LibraryUtilities.internal.nativeId(this));
    }
    @QtUninvokable
    private static native <T> QSequentialIterator<T> begin(long __this__nativeId);

    @QtUninvokable
    protected final QSequentialIterator<T> end()    {
        return end(QtJambi_LibraryUtilities.internal.nativeId(this));
    }
    @QtUninvokable
    private static native <T> QSequentialIterator<T> end(long __this__nativeId);

    @QtUninvokable
    protected final QSequentialConstIterator<T> constBegin()    {
        return constBegin(QtJambi_LibraryUtilities.internal.nativeId(this));
    }
    @QtUninvokable
    private static native <T> QSequentialConstIterator<T> constBegin(long __this__nativeId);

    @QtUninvokable
    protected final QSequentialConstIterator<T> constEnd()    {
        return constEnd(QtJambi_LibraryUtilities.internal.nativeId(this));
    }
    @QtUninvokable
    private static native <T> QSequentialConstIterator<T> constEnd(long __this__nativeId);

    @QtUninvokable
    public final boolean endsWith(T t)    {
        try{
            return endsWith(QtJambi_LibraryUtilities.internal.nativeId(this), t);
        }catch(QNoNativeResourcesException e) {
            throw e;
        }catch(RuntimeException e) {
            throw QSet.handleException(e, elementMetaType(), t);
        }
    }
    @QtUninvokable
    private static native <T> boolean endsWith(long __this__nativeId, T t);

    @QtUninvokable
    public final T first()    {
		try{
			return at(0);
		} catch (IndexOutOfBoundsException e) {
			throw new NoSuchElementException();
		}
    }
    
    @QtUninvokable
    public final int indexOf(T t, int from)    {
        try {
            return indexOf(QtJambi_LibraryUtilities.internal.nativeId(this), t, from);
        }catch(QNoNativeResourcesException e) {
            throw e;
        }catch(IllegalArgumentException e) {
            return -1;
        }catch(RuntimeException e) {
            RuntimeException e1 = QSet.handleException(e, elementMetaType(), t);
            if(e1==e)
                throw e;
            return -1;
        }
    }
    @QtUninvokable
    private static native <T> int indexOf(long __this__nativeId, T t, int from);

    @QtUninvokable
    public final void insert(int i, T t)    {
        try {
            insert(QtJambi_LibraryUtilities.internal.nativeId(this), i, 1, t);
        }catch(IndexOutOfBoundsException | QNoNativeResourcesException e) {
            throw e;
        }catch(RuntimeException e) {
            throw QSet.handleException(e, elementMetaType(), t);
        }
    }
    @QtUninvokable
    private static native <T> void insert(long __this__nativeId, int i, int n, T t);

    @QtUninvokable
    public final boolean isEmpty()    {
        return size()==0;
    }

    @QtUninvokable
    public final T last() {
		try{
			return last(QtJambi_LibraryUtilities.internal.nativeId(this));
		} catch (IndexOutOfBoundsException e) {
			throw new NoSuchElementException();
		}
    }
    @QtUninvokable
    private static native <T> T last(long __this__nativeId);

    @SuppressWarnings("unchecked")
    @QtUninvokable
    @Override
    public final int lastIndexOf(Object t) {
        return lastIndexOf((T)t, (int)-1);
    }
    @QtUninvokable
    public final int lastIndexOf(T t, int from)    {
        try {
            return lastIndexOf(QtJambi_LibraryUtilities.internal.nativeId(this), t, from);
        }catch(QNoNativeResourcesException e) {
            throw e;
        }catch(IllegalArgumentException e) {
            return -1;
        }catch(RuntimeException e) {
            RuntimeException e1 = QSet.handleException(e, elementMetaType(), t);
            if(e1==e)
                throw e;
            return -1;
        }
    }
    @QtUninvokable
    private static native <T> int lastIndexOf(long __this__nativeId, T t, int from);

    @QtUninvokable
    public final int length() {
        return size();
    }

    @QtUninvokable
    public final QList<T> mid(int pos) {
        return mid(pos, (int)-1);
    }
    @QtUninvokable
    public final QList<T> mid(int pos, int length) {
        return mid(QtJambi_LibraryUtilities.internal.nativeId(this), pos, length);
    }
    @QtUninvokable
    private static native <T> QList<T> mid(long __this__nativeId, int pos, int length);

    @QtUninvokable
    public final void move(int from, int to) {
        move(QtJambi_LibraryUtilities.internal.nativeId(this), from, to);
    }
    @QtUninvokable
    private static native <T> void move(long __this__nativeId, int from, int to);

    @QtUninvokable
    private final boolean operator_equal(java.util.Collection<T> l) {
        return operator_equal(QtJambi_LibraryUtilities.internal.nativeId(this), l);
    }
    @QtUninvokable
    private static native <T> boolean operator_equal(long __this__nativeId, java.util.Collection<T> l);

    @QtUninvokable
    public final void prepend(T t) {
        try {
        	insert(QtJambi_LibraryUtilities.internal.nativeId(this), 0, 1, t);
    	}catch(QNoNativeResourcesException e) {
    		throw e;
    	}catch(RuntimeException e) {
    		throw QSet.handleException(e, elementMetaType(), t);
    	}
    }

    @QtUninvokable
    public final int removeAll(T t) {
        try {
            return removeAll(QtJambi_LibraryUtilities.internal.nativeId(this), t);
        }catch(QNoNativeResourcesException e) {
            throw e;
        }catch(IllegalArgumentException e) {
            return 0;
        }catch(RuntimeException e) {
            RuntimeException e1 = QSet.handleException(e, elementMetaType(), t);
            if(e1==e)
                throw e;
            return 0;
        }
    }
    @QtUninvokable
    private static native <T> int removeAll(long __this__nativeId, T t);

    @QtUninvokable
    public final void removeAt(int i) {
        remove(QtJambi_LibraryUtilities.internal.nativeId(this), i, 1);
    }

    @QtUninvokable
    public final void removeFirst() {
        takeFirst();
    }

    @QtUninvokable
    public final void removeLast() {
        takeLast();
    }

    @QtUninvokable
    public final boolean removeOne(T t) {
        try {
    		return removeOne(QtJambi_LibraryUtilities.internal.nativeId(this), t);
        }catch(QNoNativeResourcesException e) {
            throw e;
        }catch(IllegalArgumentException e) {
        }catch(RuntimeException e) {
            RuntimeException e1 = QSet.handleException(e, elementMetaType(), t);
            if(e1==e)
                throw e;
        }
        return false;
    }
    @QtUninvokable
    private static native <T> boolean removeOne(long __this__nativeId, T t);
	
	/**
     * <p>See <a href="https://doc.qt.io/qt/qlist.html#removeIf" class="member-name-class">QList::<wbr>removeIf(Predicate)</a></p>
	 */
    @QtUninvokable
    public final boolean removeIf(Predicate<? super T> predicate)    {
        Objects.requireNonNull(predicate);
        boolean removed = false;
        for(T value : clone()) {
            if (predicate.test(value)) {
                removeAll(value);
                removed = true;
            }
        }
        return removed;
    }
	
    @QtUninvokable
    private static native void remove(long __this__nativeId, int i, int n);

    @QtUninvokable
    public final void replace(int i, T t) {
        try {
            replace(QtJambi_LibraryUtilities.internal.nativeId(this), i, t);
        }catch(QNoNativeResourcesException e) {
            throw e;
        }catch(RuntimeException e) {
            throw QSet.handleException(e, elementMetaType(), t);
        }
    }
    @QtUninvokable
    private static native <T> void replace(long __this__nativeId, int i, T t);

    @QtUninvokable
    public final void reserve(int size) {
        reserve(QtJambi_LibraryUtilities.internal.nativeId(this), size);
    }
    @QtUninvokable
    private static native <T> void reserve(long __this__nativeId, int size);

    @QtUninvokable
    public final int size() {
        return size(QtJambi_LibraryUtilities.internal.nativeId(this));
    }
    @QtUninvokable
    private static native <T> int size(long __this__nativeId);

    @QtUninvokable
    public final boolean startsWith(T t) {
        try{
            return startsWith(QtJambi_LibraryUtilities.internal.nativeId(this), t);
        }catch(QNoNativeResourcesException e) {
            throw e;
        }catch(RuntimeException e) {
            throw QSet.handleException(e, elementMetaType(), t);
        }
    }
    @QtUninvokable
    private static native <T> boolean startsWith(long __this__nativeId, T t);

    @QtUninvokable
    public final T takeAt(int i) {
        return takeAt(QtJambi_LibraryUtilities.internal.nativeId(this), i);
    }
    @QtUninvokable
    private static native <T> T takeAt(long __this__nativeId, int i);

    @QtUninvokable
    public final T takeFirst() {
		try{
			return takeAt(0);
		} catch (IndexOutOfBoundsException e) {
			throw new NoSuchElementException();
		}
    }
    
    @QtUninvokable
    public final T takeLast() {
        try{
        	return takeLast(QtJambi_LibraryUtilities.internal.nativeId(this));
		} catch (IndexOutOfBoundsException e) {
			throw new NoSuchElementException();
		}
    }
    @QtUninvokable
    private static native <T> T takeLast(long __this__nativeId);

    @QtUninvokable
    public final T value(int i) {
        return value(QtJambi_LibraryUtilities.internal.nativeId(this), i);
    }
    @QtUninvokable
    private static native <T> T value(long __this__nativeId, int i);

    @QtUninvokable
    public final T value(int i, T defaultValue) {
        try {
            return valueDefault(QtJambi_LibraryUtilities.internal.nativeId(this), i, defaultValue);
        }catch(QNoNativeResourcesException e) {
            throw e;
        }catch(RuntimeException e) {
            throw QSet.handleException(e, elementMetaType(), defaultValue);
        }
    }
    @QtUninvokable
    private static native <T> T valueDefault(long __this__nativeId, int i, T defaultValue);

    /**
     * <p>See <code><a href="https://doc.qt.io/qt/qlist.html#operator-eq-eq" class="member-name-class">QList::<wbr>operator==(QList&lt;T>)const</a></code></p>
     */
    @SuppressWarnings({ "rawtypes", "unchecked" })
    @Override
    @QtUninvokable
    public boolean equals(Object other) {
        if (other instanceof java.util.Collection && checkContainerType(this.elementMetaType(), (java.util.Collection<?>) other)) {
            return operator_equal((java.util.Collection) other);
        }
        return false;
    }

    /**
     * Returns the objects's hash code computed by <code>qHash(QList&lt;T>)</code>.
     */
    @Override
    @QtUninvokable
    public int hashCode() { 
    	try {
    		return hashCode(QtJambi_LibraryUtilities.internal.nativeId(this));
		} catch (QNoNativeResourcesException e) {
			return 0;
		}
    }
    @QtUninvokable
    private static native int hashCode(long __this__nativeId);
    
    @Override
    @QtUninvokable
    public String toString() {
    	try {
			return toString(QtJambi_LibraryUtilities.internal.nativeId(this));
		} catch (QNoImplementationException e) {
			return super.toString();
		} catch (QNoNativeResourcesException e) {
			return "null";
		}
    }
    @QtUninvokable
    private static native String toString(long __this__nativeId);
    
    @Override
    @QtUninvokable
    public boolean add(T e){
        append(e);
        return true;
    }

    @Override
    @QtUninvokable
    public void add(int index, T e){
            insert(index, e);
    }

    @Override
    @QtUninvokable
    public T set(int index, T e){
        if(e!=null){
            T el = at(index);
            replace(index, e);
            return el;
        } else return null;
    }
    
    @SuppressWarnings("unchecked")
    @Override
    @QtUninvokable
    public boolean remove(Object e){
        return removeOne((T)e);
    }

    @SuppressWarnings("unchecked")
    @Override
    @QtUninvokable
    public int indexOf(Object e){
        return indexOf((T)e, (int)0);
    }

    @Override
    @QtUninvokable
    public T get(int index) {
        return at(index);
    }
    
    @QtUninvokable
    public final void swapItemsAt(int i, int j) {
        swapItemsAt(QtJambi_LibraryUtilities.internal.nativeId(this), i, j);
    }
    
    @QtUninvokable
    private static native <T> void swapItemsAt(long __this__nativeId, int i, int j);
    
    @QtUninvokable
    public final QSet<T> toSet() {
		QSet<T> set = new QSet<>(elementMetaType());
		set.unite(this);
        return set;
    }
	
	public QList<T> reversed(){
		QList<T> result = new QList<>(elementMetaType());
		for(T t : this) {
			result.add(0, t);
		}
    	return result;
    }
    
    /**
     * <p>See <code>operator&lt;&lt;(QDataStream&amp;,<wbr>QList&lt;T>)</code></p>
     */
    @io.qt.QtUninvokable
    public void writeTo(io.qt.core.QDataStream stream){
        writeTo(QtJambi_LibraryUtilities.internal.nativeId(this), QtJambi_LibraryUtilities.internal.nativeId(stream));
    }
    
    @io.qt.QtUninvokable
    private native void writeTo(long __this__nativeId, long stream);
    
    /**
     * <p>See <code>operator&gt;&gt;(QDataStream&amp;,<wbr>QList&lt;T>&amp;)</code></p>
     */
    @io.qt.QtUninvokable
    public void readFrom(io.qt.core.QDataStream stream){
        readFrom(QtJambi_LibraryUtilities.internal.nativeId(this), QtJambi_LibraryUtilities.internal.nativeId(stream));
    }
    
    @io.qt.QtUninvokable
    private native void readFrom(long __this__nativeId, long stream);
    
    @io.qt.QtUninvokable
    QMetaType elementMetaType() {
        return elementMetaType(QtJambi_LibraryUtilities.internal.nativeId(this));
    }
    @io.qt.QtUninvokable
    private static native QMetaType elementMetaType(long containerAccess);
    
    /**
     * Returns a QList containing given elements.
     *
     * @param <T> the {@code QList}'s element type
     * @param element0 the first element
     * @param elements subsequent elements
     * @return a {@code QList} containing the specified element
     * @throws NullPointerException if elements are {@code null}
     *
     */
    @SuppressWarnings({ "unchecked" })
    @SafeVarargs
    public static <T> QList<T> of(T element0, T...elements) {
        QMetaType metaType = findElementMetaType(element0, elements);
        QList<T> result;
        if(metaType.id()==QMetaType.Type.QString.value())
            result = (QList<T>)(QList<?>)new QStringList();
        else
            result = new QList<>(metaType);
        result.reserve(elements.length+1);
        result.append(element0);
        for (T t : elements) {
            result.append(t);
        }
        return result;
    }
    
    /**
     * Returns a QStringList containing given elements.
     *
     * @param <T> the {@code QStringList}'s element type
     * @param element0 the first element
     * @param elements subsequent elements
     * @return a {@code QStringList} containing the specified element
     */
    @SafeVarargs
    public static QStringList of(String element0, String...elements) {
        QStringList result = new QStringList();
        result.reserve(elements.length+1);
        result.append(element0);
        for (String element : elements) {
            result.append(element);
        }
        return result;
    }

    static String pairPrefix() {
    	return "QPair";
    }

    static boolean checkContainerType(QMetaType elementType, java.util.Collection<?> container) {
        if (container instanceof QList && elementType.equals(((QList<?>) container).elementMetaType())) {
            return true;
        } else if (container instanceof QSet && elementType.equals(((QSet<?>) container).elementMetaType())) {
            return true;
        } else if (container instanceof QVector && elementType.equals(((QVector<?>) container).elementMetaType())) {
            return true;
        } else if (container instanceof QLinkedList && elementType.equals(((QLinkedList<?>) container).elementMetaType())) {
            return true;
        } else {
            Class<?> type = elementType.javaType();
            if (type.isPrimitive()) {
                if (type == int.class)
                    type = Integer.class;
                if (type == short.class)
                    type = Short.class;
                if (type == byte.class)
                    type = Byte.class;
                if (type == long.class)
                    type = Long.class;
                if (type == float.class)
                    type = Float.class;
                if (type == double.class)
                    type = Double.class;
                if (type == boolean.class)
                    type = Boolean.class;
                if (type == char.class)
                    type = Character.class;
            }
            for (Object entry : container) {
                if (entry != null && !type.isInstance(entry))
                    return false;
            }
        }
        return true;
    }
    
    @SafeVarargs
    static QMetaType findElementMetaType(Object element0, Object... elements){
        QMetaType result = getMetaType(element0);
        Class<?> type = element0==null ? null : element0.getClass();
        for(Object e : elements) {
            if(result!=null) {
                QMetaType _result = getMetaType(e);
                if(!result.isValid() || result.id()==QMetaType.Type.Nullptr.value())
                    result = _result;
                if(!_result.equals(result)) {
                    result = null;
                }
            }
            if(e!=null) {
                if(type==null) {
                    type = e.getClass();
                }else {
                    Class<?> type2 = e.getClass();
                    if(type2!=type) {
                        if(type2.isAssignableFrom(type)) {
                            type = type2;
                        }else if(!type.isAssignableFrom(type2)) {
                            do{
                                type = type.getSuperclass();
                            }while(!type.isAssignableFrom(type2));
                        }
                    }
                }
            }
        }
        if(result==null)
            result = QMetaType.fromType(type);
        return result;
    }
    
    static QMetaType findElementMetaType(Collection<?> elements){
		Class<?> cls = QtJambi_LibraryUtilities.internal.getClass(elements);
        if(cls==QList.class) {
            return ((QList<?>)elements).elementMetaType();
        }else if(cls==QQueue.class) {
            return ((QQueue<?>)elements).elementMetaType();
        }else if(cls==QStack.class) {
            return ((QStack<?>)elements).elementMetaType();
        }else if(cls==QSet.class) {
            return ((QSet<?>)elements).elementMetaType();
        }else {
            QMetaType result = new QMetaType();
            Class<?> type = null;
            for(Object e : elements) {
                if(result!=null) {
                    QMetaType _result = getMetaType(e);
                    if(!result.isValid() || result.id()==QMetaType.Type.Nullptr.value())
                        result = _result;
                    if(!_result.equals(result)) {
                        result = null;
                    }
                }
                if(e!=null) {
                    if(type==null) {
                        type = e.getClass();
                    }else {
                        Class<?> type2 = e.getClass();
                        if(type2!=type) {
                            if(type2.isAssignableFrom(type)) {
                                type = type2;
                            }else if(!type.isAssignableFrom(type2)) {
                                do{
                                    type = type.getSuperclass();
                                }while(!type.isAssignableFrom(type2));
                            }
                        }
                    }
                }
            }
            if(result==null)
                result = QMetaType.fromType(type);
            return result;
        }
    }
    
    static QMetaType getMetaType(Object obj){
        if(obj==null)
            return new QMetaType(QMetaType.Type.Nullptr);
    	Class<?> objClass = QtJambi_LibraryUtilities.internal.getClass(obj);
        if(objClass==QList.class) {
            return QMetaType.fromType(QList.class, ((QList<?>)obj).elementMetaType());
        }else if(objClass==QQueue.class) {
            return QMetaType.fromType(QQueue.class, ((QQueue<?>)obj).elementMetaType());
        }else if(objClass==QStack.class) {
            return QMetaType.fromType(QStack.class, ((QStack<?>)obj).elementMetaType());
        }else if(objClass==QSet.class) {
            return QMetaType.fromType(QSet.class, ((QSet<?>)obj).elementMetaType());
        }else if(objClass==QMultiMap.class) {
            return QMetaType.fromType(QMultiMap.class, ((QMultiMap<?,?>)obj).keyMetaType(), ((QMultiMap<?,?>)obj).valueMetaType());
        }else if(objClass==QMap.class) {
            return QMetaType.fromType(QMap.class, ((QMap<?,?>)obj).keyMetaType(), ((QMap<?,?>)obj).valueMetaType());
        }else if(objClass==QMultiHash.class) {
            return QMetaType.fromType(QMultiHash.class, ((QMultiHash<?,?>)obj).keyMetaType(), ((QMultiHash<?,?>)obj).valueMetaType());
        }else if(objClass==QHash.class) {
            return QMetaType.fromType(QHash.class, ((QHash<?,?>)obj).keyMetaType(), ((QHash<?,?>)obj).valueMetaType());
        }else if(objClass==QPair.class) {
            return QMetaType.fromType(QPair.class, getMetaType(((QPair<?,?>)obj).first), getMetaType(((QPair<?,?>)obj).second));
        }else if(obj instanceof java.util.List) {
            java.util.List<?> list = (java.util.List<?>)obj;
            QMetaType elementMetaType = findElementMetaType(list);
            if(elementMetaType.isValid()) {
                return QMetaType.fromType(QList.class, elementMetaType);
            }
        }else if(obj instanceof java.util.Set) {
            java.util.Set<?> list = (java.util.Set<?>)obj;
            QMetaType elementMetaType = findElementMetaType(list);
            if(elementMetaType.isValid()) {
                return QMetaType.fromType(QSet.class, elementMetaType);
            }
        }else if(obj instanceof java.util.Map) {
            java.util.Map<?,?> map = (java.util.Map<?,?>)obj;
            QPair<QMetaType, QMetaType> metaTypes = QMap.findMapMetaType(map);
            if(metaTypes.first.isValid() && metaTypes.second.isValid()) {
                if(obj instanceof java.util.NavigableMap)
                    return QMetaType.fromType(QMap.class, metaTypes.first, metaTypes.second);
                else
                    return QMetaType.fromType(QHash.class, metaTypes.first, metaTypes.second);
            }
        }
        QMetaType metaType = QMetaType.fromType(objClass);
        return metaType;
    }
}
