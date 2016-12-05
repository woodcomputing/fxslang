/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.woodcomputing.fxslang.collections;

import java.util.function.Predicate;
import javafx.collections.ModifiableObservableListBase;
import javaslang.collection.List;

/**
 *
 * @author jwood
 */
public class ObservableSlangList<T> extends ModifiableObservableListBase<T> {

    private List<T> delegate;

    private ObservableSlangList() {
        delegate = List.empty();
    }

    private ObservableSlangList(T element) {
        delegate = List.of(element);
    }
    
    private ObservableSlangList(T... elements) {
        delegate = List.of(elements);
    }
    
    private ObservableSlangList(Iterable<? extends T> elements) {
        delegate = List.ofAll(elements);
    }
    
    public static <T> ObservableSlangList<T> empty() {
        return new ObservableSlangList();
    }
    
    public static <T> ObservableSlangList<T> of(T element) {
        return new ObservableSlangList(element);
    }
    
    public static <T> ObservableSlangList<T> of(T... elements) {
        return new ObservableSlangList(elements);
    }
    
    public static <T> ObservableSlangList<T> ofAll(Iterable<? extends T> elements) {
        return new ObservableSlangList(elements);
    }
    
    public ObservableSlangList<T> filter(Predicate<? super T> predicate) {
        ObservableSlangList<T> obsList = ObservableSlangList.ofAll(delegate.filter(predicate));
        return obsList;
    }
    
    @Override
    public T get(int index) {
       return delegate.get(index);
    }

    @Override
    public int size() {
        return delegate.size();
    }

    @Override
    protected void doAdd(int index, T element) {
        delegate = delegate.insert(index, element);
    }

    @Override
    protected T doSet(int index, T element) {
        T oldElement = delegate.get(index);
        delegate = delegate.update(index, element);
        return oldElement;
    }

    @Override
    protected T doRemove(int index) {
        T removedElement = delegate.get(index);
        delegate = delegate.removeAt(index);
        return removedElement;
    }
    
}
