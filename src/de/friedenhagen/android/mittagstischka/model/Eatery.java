/**
 * Copyright 2010 Mirko Friedenhagen 
 */

package de.friedenhagen.android.mittagstischka.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * @author mirko
 *
 */
public class Eatery implements Serializable {
    
    public final String title;
    
    public final Integer id;

    public Eatery(JSONObject o) {
        try {
            title = o.getString("title");
            id = o.getInt("id");
        } catch (JSONException e) {
            throw new RuntimeException("Message:", e);
        }
//        String homepageUrl;
//        try {
//            homepageUrl = o.getString("homepage");
//        } catch (JSONException e) {
//            homepageUrl = "http://mittagstisch-ka.de/";
//        }
//        try {
//            homepage = new URL(homepageUrl);
//        } catch (MalformedURLException e) {
//            throw new RuntimeException("Message:", e);
//        }
        
    }
    @Override
    public String toString() {        
        return "Eatery(" + title + ", " + id + ")";
    }
    public static Eatery fromJsonObject(JSONObject o) {
        return new Eatery(o);
    }
    
    public static List<Eatery> fromJsonArray(JSONArray jsonArray) {
        final List<Eatery> eateryList = new ArrayList<Eatery>();
        final int length = jsonArray.length();
        for (int i = 0; i < length; i++) {
            try {
                eateryList.add(Eatery.fromJsonObject((JSONObject)jsonArray.get(i)));
            } catch (JSONException e) {
                throw new RuntimeException("Message:", e);
            }
        }        
        return eateryList;
    }

}
