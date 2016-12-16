/*
 * FXSlang
 * Copyright 2016 Jonathan Wood
 * Licensed under the Apache License, Version 2.0
 */

import java.util.Collection;
import java.util.Iterator;
import javafx.beans.InvalidationListener;
import javafx.collections.ObservableSet;
import javafx.collections.SetChangeListener;
import javaslang.collection.HashSet;
import javaslang.collection.Set;

/**
 *
 * @author Jonathan Wood
 * 
 */
public class ObservableSlangSet<E> implements ObservableSet<E> {
    
    private Set<E> backingSet;
    
    private ObservableSlangSet() {
        backingSet = HashSet.empty();
    }
    
    public ObservableSlangSet<E> empty() {
        return new ObservableSlangSet<>();
    }

    @Override
    public void addListener(SetChangeListener<? super E> listener) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void removeListener(SetChangeListener<? super E> listener) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    @Override
    public void addListener(InvalidationListener listener) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void removeListener(InvalidationListener listener) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    @Override
    public int size() {
        return backingSet.size();
    }

    @Override
    public boolean isEmpty() {
        return backingSet.isEmpty();
    }

    @Override
    public boolean contains(Object o) {
        return backingSet.contains((E)o);
    }

    @Override
    public Iterator<E> iterator() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Object[] toArray() {
//        return backingSet.toArray();
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public <E> E[] toArray(E[] a) {
//        return backingSet.toArray();
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean add(E e) {
        boolean exists = backingSet.contains(e);
        if(!exists) {
            backingSet = backingSet.add(e);
        }
        return !exists;
    }

    @Override
    public boolean remove(Object o) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean addAll(Collection<? extends E> c) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
//        for()
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        boolean empty = backingSet.isEmpty();
        if(!empty) {
            backingSet = HashSet.empty();
        }
        return !empty;
    }

    @Override
    public void clear() {
        backingSet = HashSet.empty();
    }
    
}
