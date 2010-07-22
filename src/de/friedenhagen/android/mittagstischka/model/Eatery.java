/**
 * Copyright 2010 Mirko Friedenhagen 
 */

package de.friedenhagen.android.mittagstischka.model;

import java.net.MalformedURLException;
import java.net.URL;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * @author mirko
 *
 */
public class Eatery {
    
    public final String title;
    
//    public final URL homepage;
    
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
    
    public static Eatery fromJsonObject(JSONObject o) {
        return new Eatery(o);
    }

}
