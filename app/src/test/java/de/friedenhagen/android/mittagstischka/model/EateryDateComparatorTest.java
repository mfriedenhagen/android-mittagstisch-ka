/*
 * EateryDateComparatorTest.java 27.07.2010
 * 
 * Copyright (c) 2010 1&1 Internet AG. All rights reserved.
 * 
 * $Id$
 */
package de.friedenhagen.android.mittagstischka.model;

import static org.junit.Assert.assertEquals;

import java.util.Collections;

import org.junit.Before;
import org.junit.Test;

public class EateryDateComparatorTest extends EateryAbstractComparatorTest {

    @Before
    public void sortList() {
        Collections.sort(list, EateryDateComparator.INSTANCE);
    }

    @Test
    public void checkOrder() {
        assertEquals("Saigon Restaurant", first().title);
        assertEquals("Bombay Palace", last().title);
    }

    @Test
    public void checkSwappedOrder() {
        Collections.sort(list, Collections.reverseOrder(EateryDateComparator.INSTANCE));
        assertEquals("Bombay Palace", first().title);
        assertEquals("Saigon Restaurant", last().title);        
    }

}
