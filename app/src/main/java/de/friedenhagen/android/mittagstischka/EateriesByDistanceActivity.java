/**
 * Copyright 2010 Mirko Friedenhagen 
 */

package de.friedenhagen.android.mittagstischka;

import java.util.Comparator;

import android.location.Location;
import android.location.LocationManager;
import android.util.Log;
import de.friedenhagen.android.mittagstischka.model.Eatery;
import de.friedenhagen.android.mittagstischka.model.EateryGeoComparator;

/**
 * @author mirko
 * 
 */
public class EateriesByDistanceActivity extends EateriesByAbstractActivity {

    private final static String TAG = Constants.LOG_PREFIX + EateriesByDistanceActivity.class.getSimpleName();

    /** {@inheritDoc} */
    @Override
    protected Comparator<Eatery> getComparator() {
        final LocationManager locationService = (LocationManager) getSystemService(LOCATION_SERVICE);
        final Location locationGPS = locationService.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        final Location locationNET = locationService.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
        final Location location;
        if (locationGPS != null) {
            location = locationGPS;
            Log.d(TAG, "using GPS");
        } else if (locationNET != null) {
            location = locationNET;
            Log.d(TAG, "using NETWORK");
        } else {
            throw new RuntimeException("Could not get geolocation");
        }
        return new EateryGeoComparator(location.getLatitude(), location.getLongitude());
    }

}
