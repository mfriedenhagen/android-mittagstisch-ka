/*
 * EateryAbstractComparatorTest.java 27.07.2010
 * 
 * Copyright (c) 2010 1&1 Internet AG. All rights reserved.
 * 
 * $Id$
 */
package de.friedenhagen.android.mittagstischka.model;

import java.util.List;

import org.junit.Before;


public abstract class EateryAbstractComparatorTest {

    protected List<Eatery> list;

    /**
     * @throws java.lang.Exception
     */
    @Before
    public void createListFrom() throws Exception {
        list = Eatery.fromJsonArray(TUtils.getEateries());
    }

    protected Eatery first() {
        return list.get(0);
    }

    protected Eatery last() {
        return list.get(list.size() - 1);
    }
}
