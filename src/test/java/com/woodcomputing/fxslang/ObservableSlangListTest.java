/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.woodcomputing.fxslang;

import com.woodcomputing.fxslang.collections.ObservableSlangList;
import javafx.collections.ListChangeListener;
import javaslang.collection.List;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.DisplayName;

/**
 *
 * @author jwood
 */
public class ObservableSlangListTest {

    ;
        
    @Test
    @DisplayName("Test adding an element to the list.")
    void addTest() {
        final boolean wasAdded;
            ObservableSlangList<Integer> testList = ObservableSlangList.empty();
        testList.addListener((ListChangeListener.Change<? extends Integer> c) -> {
            c.next();
            assertTrue(c.wasAdded());
        });
        testList.add(1);
        assertEquals(1, testList.size());
    }
    
    @Test
    @DisplayName("Test removal of an element from the list.")
    void removeTest() {
        ObservableSlangList<Integer> testList = ObservableSlangList.of(1);
        testList.addListener((ListChangeListener.Change<? extends Integer> c) -> {
            c.next();
            assertTrue(c.wasRemoved());
        });
        assertEquals(1, testList.size());
        testList.remove(0);
        assertEquals(0, testList.size());
    }

    @Test
    @DisplayName("Test creation of the observable list from a list.")
    void createFromListTest() {
        List origList = List.of("One", "Two", "Three");
        ObservableSlangList<String> testList = ObservableSlangList.ofAll(origList);
        assertEquals(3, testList.size());
    }
    
    @Test
    void createWithOf() {
        ObservableSlangList<String> testList = ObservableSlangList.of("One", "Two", "Three");
        assertEquals(3, testList.size());
    }
    
}
