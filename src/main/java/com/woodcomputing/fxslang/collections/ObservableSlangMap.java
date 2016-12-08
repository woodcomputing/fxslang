/*
 * FXSlang
 * Copyright 2016 Jonathan Wood
 * Licensed under the Apache License, Version 2.0
 */
package com.woodcomputing.fxslang.collections;

import com.sun.javafx.collections.MapListenerHelper;
import java.util.Collection;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;
import javafx.beans.InvalidationListener;
import javafx.collections.MapChangeListener;
import javafx.collections.ObservableMap;
import javaslang.Tuple2;
import javaslang.collection.HashMap;
import javaslang.collection.Map;
import javaslang.control.Option;

/**
 *
 * @author Jonathan Wood
 * 
 */
public class ObservableSlangMap<K,V> implements ObservableMap<K,V> {

    private Map<K,V> backingMap;
    private MapListenerHelper<K, V> listenerHelper;
    
    private ObservableSlangMap() {
        backingMap = HashMap.empty();
    }
    
    private ObservableSlangMap(K key, V value) {
        backingMap = HashMap.of(key, value);
    }
    
    public static <K,V> ObservableSlangMap<K,V> empty() {
        return new ObservableSlangMap<>();
    }
    
    public static <K,V> ObservableSlangMap<K,V> of(K key, V value) {
        return new ObservableSlangMap<>(key, value);
    }
    
    @Override
    public void addListener(MapChangeListener<? super K, ? super V> listener) {
        listenerHelper = MapListenerHelper.addListener(listenerHelper, listener);
    }

    @Override
    public void removeListener(MapChangeListener<? super K, ? super V> listener) {
        listenerHelper = MapListenerHelper.removeListener(listenerHelper, listener);
    }

    @Override
    public void addListener(InvalidationListener listener) {
        listenerHelper = MapListenerHelper.addListener(listenerHelper, listener);
    }

    @Override
    public void removeListener(InvalidationListener listener) {
        listenerHelper = MapListenerHelper.removeListener(listenerHelper, listener);
    }
    
    @Override
    public int size() {
        return backingMap.size();
    }

    @Override
    public boolean isEmpty() {
        return backingMap.isEmpty();
    }

    @Override
    public boolean containsKey(Object key) {
        return backingMap.containsKey((K)key);
    }

    @Override
    public boolean containsValue(Object value) {
        return backingMap.containsValue((V)value);
    }

    @Override
    public V get(Object key) {
        Option<V> option = backingMap.get((K)key);
        return option.getOrElse((V)null);
    }

    @Override
    public V put(K key, V value) {
        Option<V> oldValue = backingMap.get(key);
        backingMap = backingMap.put(key, value);
        if(oldValue.isDefined()) {
            callObservers(new SimpleChange(key, oldValue.get(), value, true, true));
        } else {
            callObservers(new SimpleChange(key, null, value, true, false));
        }
        return oldValue.getOrElse((V)null);
    }

    @Override
    public V remove(Object key) {
        Option<V> removedValue = backingMap.get((K)key);
        if(removedValue.isDefined()) {
            backingMap = backingMap.remove((K)key);
            callObservers(new SimpleChange((K) key, removedValue.get(), null, false, true));
        }
        return removedValue.getOrElse((V)null);
    }

    @Override
    public void clear() {
        Map<K,V> callMap = backingMap;
        backingMap = HashMap.empty();
        for (Tuple2<K, V> t : callMap) {
            callObservers(new SimpleChange(t._1, t._2, null, false, true));
        }
    }

    @Override
    public Collection<V> values() {
        return backingMap.values().toJavaList();
    }

    @Override
    public void putAll(java.util.Map<? extends K, ? extends V> m) {
        backingMap = backingMap.merge(HashMap.ofAll(m));
    }

    @Override
    public Set<K> keySet() {
        return backingMap.keySet().toJavaSet();
    }
    
    private static final class SlangEntry<K,V> implements Entry<K,V> {

        private final K key;
        private final V value;

        public SlangEntry(Tuple2<K,V> tuple) {
            this.key = tuple._1;
            this.value = tuple._2;
        }
        
        @Override
        public K getKey() {
            return key;
        }

        @Override
        public V getValue() {
            return value;
        }

        @Override
        public V setValue(V value) {
            throw new UnsupportedOperationException("Not supported yet.");
        }
        
    }
    
    public Function<Tuple2<K,V>, Entry<K,V>> tuple2entry = (Tuple2<K,V> tuple) -> new SlangEntry<>(tuple); 

    @Override
    public Set<Entry<K, V>> entrySet() {
        return backingMap.toSet().toJavaStream()
                .map(tuple2entry)
                .collect(Collectors.toSet());
    }
    
    private class SimpleChange extends MapChangeListener.Change<K,V> {

        private final K key;
        private final V old;
        private final V added;
        private final boolean wasAdded;
        private final boolean wasRemoved;

        public SimpleChange(K key, V old, V added, boolean wasAdded, boolean wasRemoved) {
            super(ObservableSlangMap.this);
            assert(wasAdded || wasRemoved);
            this.key = key;
            this.old = old;
            this.added = added;
            this.wasAdded = wasAdded;
            this.wasRemoved = wasRemoved;
        }

        @Override
        public boolean wasAdded() {
            return wasAdded;
        }

        @Override
        public boolean wasRemoved() {
            return wasRemoved;
        }

        @Override
        public K getKey() {
            return key;
        }

        @Override
        public V getValueAdded() {
            return added;
        }

        @Override
        public V getValueRemoved() {
            return old;
        }

        @Override
        public String toString() {
            StringBuilder builder = new StringBuilder();
            if (wasAdded) {
                if (wasRemoved) {
                    builder.append("replaced ").append(old).append("by ").append(added);
                } else {
                    builder.append("added ").append(added);
                }
            } else {
                builder.append("removed ").append(old);
            }
            builder.append(" at key ").append(key);
            return builder.toString();
        }

    }

    protected void callObservers(MapChangeListener.Change<K,V> change) {
        MapListenerHelper.fireValueChangedEvent(listenerHelper, change);
    }
    
}
