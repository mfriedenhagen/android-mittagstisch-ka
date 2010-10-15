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
import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Root;

/**
 * @author mirko
 * 
 */
@Root
public class Eatery implements Serializable {

    @Attribute(name = "title")
    public final String title;

    @Attribute(name = "id")
    public final Integer id;

    @Attribute(name = "latitude")
    public final Double latitude;

    @Attribute(name = "longitude")
    public final Double longitude;

    @Attribute(name = "homepage", required = false)
    public final String homepage;

    @Attribute(name = "date")
    public final Date date;

    /**
     * @param title
     * @param id
     * @param latitude
     * @param longitude
     * @param homepage
     * @param date
     */
    public Eatery(@Attribute(name = "title") String title, @Attribute(name = "id") Integer id,
            @Attribute(name = "latitude") Double latitude, @Attribute(name = "longitude") Double longitude,
            @Attribute(name = "homepage", required = false) String homepage, @Attribute(name = "date") Date date) {
        this.title = title;
        this.id = id;
        this.latitude = latitude;
        this.longitude = longitude;
        this.homepage = homepage;
        this.date = date;
    }

    /**
     * @param title
     * @param id
     * @param latitude
     * @param longitude
     * @param date
     */
    public Eatery(String title, Integer id, Double latitude, Double longitude, Date date) {
        this.title = title;
        this.id = id;
        this.latitude = latitude;
        this.longitude = longitude;
        this.homepage = null;
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

}
