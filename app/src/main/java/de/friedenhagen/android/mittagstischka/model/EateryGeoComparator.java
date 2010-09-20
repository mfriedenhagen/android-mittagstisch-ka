/**
 * Copyright 2010 Mirko Friedenhagen 
 */

package de.friedenhagen.android.mittagstischka.model;

import java.util.Comparator;

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
    public int compare(Eatery arg0, Eatery arg1) {
        return 0;
    }

}
