/**
 * Copyright 2010 Mirko Friedenhagen 
 */

package de.friedenhagen.android.mittagstischka.model;

import java.util.Comparator;

import android.location.Location;

/**
 * @author mirko
 * 
 */
public class EateryGeoComparator implements Comparator<Eatery> {

    private final double latitude;

    private final double longitude;

    /**
     * 
     */
    public EateryGeoComparator(final double latitude, final double longitude) {
        this.latitude = latitude;
        this.longitude = longitude;
    }

    /** {@inheritDoc} */
    @Override
    public int compare(Eatery eatery0, Eatery eatery1) {
        final float[] eatery0Results = new float[3];
        final float[] eatery1Results = new float[3];
        Location.distanceBetween(latitude, longitude, eatery0.latitude, eatery0.longitude, eatery0Results);
        Location.distanceBetween(latitude, longitude, eatery1.latitude, eatery1.longitude, eatery1Results);
        return Float.valueOf(eatery0Results[0]).compareTo(Float.valueOf(eatery1Results[1]));
    }

}
