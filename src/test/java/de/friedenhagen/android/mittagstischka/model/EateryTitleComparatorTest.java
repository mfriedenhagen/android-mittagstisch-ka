/*
 * EateryTitleComparatorTest.java 27.07.2010
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

public class EateryTitleComparatorTest extends EateryAbstractComparatorTest {
    
    @Before
    public void sortList() {
        Collections.sort(list, EateryTitleComparator.INSTANCE);
    }
    @Test
    public void checkOrder() {
        assertEquals("Afrika", first().title);
        assertEquals("Zwitscherstube im KGV Kuhlager-Seele e.V. ", last().title);
    }

    @Test
    public void checkSwappedOrder() {
        Collections.sort(list, Collections.reverseOrder(EateryTitleComparator.INSTANCE));
        assertEquals("Zwitscherstube im KGV Kuhlager-Seele e.V. ", first().title);
        assertEquals("Afrika", last().title);        
    }

}
