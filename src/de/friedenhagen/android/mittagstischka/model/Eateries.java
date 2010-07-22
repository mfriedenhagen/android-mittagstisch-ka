/**
 * Copyright 2010 Mirko Friedenhagen 
 */

package de.friedenhagen.android.mittagstischka.model;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * @author mirko
 *
 */
public class Eateries {
    
    public final List<Eatery> eateryList = new ArrayList<Eatery>();
    
    public static Eateries fromJsonObject(JSONArray jsonArray) {        
        final Eateries instance = new Eateries();        
        final int length = jsonArray.length();
        for (int i = 0; i < length; i++) {
            try {
                instance.eateryList.add(Eatery.fromJsonObject((JSONObject)jsonArray.get(i)));
            } catch (JSONException e) {
                throw new RuntimeException("Message:", e);
            }
        }        
        return instance;
    }

}
