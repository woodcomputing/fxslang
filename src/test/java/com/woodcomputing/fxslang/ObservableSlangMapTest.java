/*
 * FXSlang
 * Copyright 2016 Jonathan Wood
 * Licensed under the Apache License, Version 2.0
 */
package com.woodcomputing.fxslang;

import com.woodcomputing.fxslang.collections.ObservableSlangMap;
import javafx.collections.MapChangeListener;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 *
 * @author Jonathan Wood
 * 
 */
public class ObservableSlangMapTest {

    private static final class ChangeHolder {
        boolean changed = false;
    } 
    
    @Test
    @DisplayName("Test adding an element to the map.")
    void addTest() {
        ChangeHolder changed = new ChangeHolder();
        ObservableSlangMap<String, Integer> map = ObservableSlangMap.empty();
        map.addListener((MapChangeListener.Change<? extends String, ? extends Integer> change) -> {
            changed.changed = true;
        });
        map.put("One", 1);
        assertEquals(1, map.size());
        assertTrue(changed.changed);
    }
    
}
