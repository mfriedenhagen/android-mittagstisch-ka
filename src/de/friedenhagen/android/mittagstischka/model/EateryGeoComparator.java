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

    private final long latitude;
    
    private final long longitude;
    /**
     * 
     */
    public EateryGeoComparator(final long latitude, final long longitude) {
        this.latitude = latitude;
        this.longitude = longitude;
    }
    
    /** {@inheritDoc} */
    @Override
    public int compare(Eatery arg0, Eatery arg1) {
        return 0;
    }

}
