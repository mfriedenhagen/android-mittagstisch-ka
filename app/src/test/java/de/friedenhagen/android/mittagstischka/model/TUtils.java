/**
 * Copyright 2010 Mirko Friedenhagen 
 */

package de.friedenhagen.android.mittagstischka.model;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;

import de.friedenhagen.android.mittagstischka.retrievers.IOUtils;

/**
 * @author mirko
 *
 */
public class TUtils {

    /**
     * @return
     * @throws JSONException
     * @throws IOException
     */
    public static Eateries getEateriesFromJson() throws JSONException, IOException {
        final List<Eatery> list = Eatery.fromJsonArray(getEateries());
        final Eateries eateries = new Eateries(list);
        return eateries;
    }

    public static JSONArray getEateries() throws JSONException, IOException {
        return new JSONArray(getEateriesString());
    }
    
    public static String getEateriesString() throws IOException {
        final InputStream inputStream = TUtils.class.getResourceAsStream("index");
        try {
            return IOUtils.toUtf8String(IOUtils.toByteArray(inputStream));
        } finally {
            inputStream.close();
        }   
    }

}
