/**
 * Copyright 2010 Mirko Friedenhagen 
 */

package de.friedenhagen.android.mittagstischka;

import java.util.Comparator;

import android.location.Location;
import android.location.LocationManager;
import de.friedenhagen.android.mittagstischka.model.Eatery;
import de.friedenhagen.android.mittagstischka.model.EateryGeoComparator;

/**
 * @author mirko
 *
 */
public class EateriesByDistanceActivity extends EateriesByAbstractActivity {
    
    /** {@inheritDoc} */
    @Override
    protected Comparator<Eatery> getComparator() {
        final LocationManager locationService = (LocationManager) getSystemService(LOCATION_SERVICE);
        final Location location = locationService.getLastKnownLocation(LocationManager.GPS_PROVIDER);        
        return new EateryGeoComparator(location.getLatitude(), location.getLongitude());
    }

}
