/**
 * Copyright 2010 Mirko Friedenhagen 
 */

package de.friedenhagen.android.mittagstischka.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;
import java.util.Collections;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Before;
import org.junit.Test;

import de.friedenhagen.android.mittagstischka.retrievers.IOUtils;

/**
 * @author mirko
 * 
 */
public class EateryTest {

    private JSONArray jsonArray;

    /**
     * @throws java.lang.Exception
     */
    @Before
    public void setUp() throws Exception {
        jsonArray = getEateries();
    }

    public static JSONArray getEateries() throws JSONException, IOException {
        final InputStream inputStream = EateryTest.class.getResourceAsStream("index");
        try {
            final String index = IOUtils.toUtf8String(IOUtils.toByteArray(inputStream));
            return new JSONArray(index);
        } finally {
            inputStream.close();
        }

    }

    /**
     * Test method for {@link de.friedenhagen.android.mittagstischka.model.Eatery#Eatery(org.json.JSONObject)}.
     */
    @Test
    public void testEatery() {
        final Eatery eatery = createEateryList().get(0);
        assertEquals("Afrika", eatery.title);
        assertEquals(Integer.valueOf(78), eatery.id);
    }

    /**
     * Test method for {@link de.friedenhagen.android.mittagstischka.model.Eatery#toString()}.
     */
    @Test
    public void testToString() {
        final Eatery eatery = createEateryList().get(0);
        assertEquals("Eatery(Afrika, 78)", eatery.toString());
    }

    /**
     * Test method for {@link de.friedenhagen.android.mittagstischka.model.Eatery#fromJsonArray(org.json.JSONArray)}.
     */
    @Test
    public void testFromJsonArray() {
        assertEquals(320, createEateryList().size());
    }

    @Test
    public void testInvalidJSONObject() throws JSONException {
        final JSONObject jsonObject = new JSONObject("{}");
        try {
            Eatery.fromJsonObject(jsonObject);
            fail("expected RuntimeException");
        } catch (RuntimeException e) {
            assertEquals(JSONException.class, e.getCause().getClass());
        }
    }

    @Test
    public void testInvalidDate() throws JSONException {
        final JSONObject jsonObject = new JSONObject("{'title': 'title', 'id': 1, 'lat': 0.1, 'long': 0.2, 'date': ''}");
        try {
            Eatery.fromJsonObject(jsonObject);
            fail("expected ParseException");
        } catch (RuntimeException e) {
            assertEquals(ParseException.class, e.getCause().getClass());
        }
    }

    @Test
    public void testInvalidJSONArray() throws JSONException {
        final JSONArray jsonArray = new JSONArray("[{}]");
        try {
            Eatery.fromJsonArray(jsonArray);
            fail("expected JSONException");
        } catch (RuntimeException e) {
            assertEquals(JSONException.class, e.getCause().getClass());
        }
    }

    /**
     * @return
     */
    List<Eatery> createEateryList() {
        final List<Eatery> list = Eatery.fromJsonArray(jsonArray);
        Collections.sort(list, EateryTitleComparator.INSTANCE);
        return list;
    }

}
