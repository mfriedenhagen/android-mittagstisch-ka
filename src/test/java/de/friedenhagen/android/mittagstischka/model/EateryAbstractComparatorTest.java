/*
 * EateryAbstractComparatorTest.java 27.07.2010
 * 
 * Copyright (c) 2010 1&1 Internet AG. All rights reserved.
 * 
 * $Id$
 */
package de.friedenhagen.android.mittagstischka.model;

import java.io.InputStream;
import java.util.List;

import org.json.JSONArray;
import org.junit.Before;

import de.friedenhagen.android.mittagstischka.retrievers.IOUtils;

public abstract class EateryAbstractComparatorTest {

    protected List<Eatery> list;

    /**
     * @throws java.lang.Exception
     */
    @Before
    public void createListFrom() throws Exception {
        final InputStream inputStream = EateryAbstractComparatorTest.class.getResourceAsStream("index");
        try {
            final String index = IOUtils.toUtf8String(IOUtils.toByteArray(inputStream));
            list = Eatery.fromJsonArray(new JSONArray(index));            
        } finally {
            inputStream.close();
        }
    }

    protected Eatery first() {
        return list.get(0);
    }

    protected Eatery last() {
        return list.get(list.size() - 1);
    }
}
