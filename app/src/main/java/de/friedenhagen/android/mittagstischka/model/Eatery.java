/**
 * Copyright 2010 Mirko Friedenhagen 
 */

package de.friedenhagen.android.mittagstischka.model;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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

    public final Double latitude;

    public final Double longitude;

    public final String homepage;

    public final Date date;

    /**
     * @param title
     * @param id
     * @param latitude
     * @param longitude
     * @param homepage
     * @param date
     */
    Eatery(final String title, final Integer id,
            Double latitude, Double longitude,
            String homepage, Date date) {
        this.title = title;
        this.id = id;
        this.latitude = latitude;
        this.longitude = longitude;
        this.homepage = homepage;
        this.date = date;
    }

    @Override
    public String toString() {
        return "Eatery(" + title + ", " + id + ")";
    }

    public static Eatery fromJsonObject(JSONObject o) {
        try {
            final String title = o.getString("title");
            final Integer id = o.getInt("id");
            final Double latitude = o.getDouble("lat");
            final Double longitude = o.getDouble("long");
            final String homepage;
            if (o.has("homepage")) {
                homepage = o.getString("homepage");
            } else {
                homepage = null;
            }
            final String dateAsString = o.getString("date");
            final SimpleDateFormat format = new SimpleDateFormat("dd.MM.yyyy");
            final Date date = format.parse(dateAsString);
            return new Eatery(title, id, latitude, longitude, homepage, date);
        } catch (JSONException e) {
            throw new RuntimeException("Message:", e);
        } catch (ParseException e) {
            throw new RuntimeException("Message:", e);
        }
    }

    public static List<Eatery> fromJsonArray(JSONArray jsonArray) {
        final int length = jsonArray.length();
        final List<Eatery> eateryList = new ArrayList<Eatery>(length);
        for (int i = 0; i < length; i++) {
            try {
                eateryList.add(Eatery.fromJsonObject((JSONObject) jsonArray.get(i)));
            } catch (JSONException e) {
                throw new RuntimeException("Message:", e);
            }
        }
        return eateryList;
    }
    
    public static List<Eatery> fromJsonString(final String jsonString) {
        try {
            return fromJsonArray(new JSONArray(jsonString));
        } catch (JSONException e) {
            throw new RuntimeException("Message:", e);
        }
    }

}
