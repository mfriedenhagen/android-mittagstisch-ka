/**
 * Copyright 2010 Mirko Friedenhagen 
 */

package de.friedenhagen.android.mittagstischka;

import android.app.ListActivity;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import de.friedenhagen.android.mittagstischka.model.EateryGeoComparator;
import de.friedenhagen.android.mittagstischka.retrievers.CachingRetriever;

/**
 * @author mirko
 *
 */
public class EateriesByDistanceActivity extends ListActivity {
    
    /** {@inheritDoc} */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.eateries_list);
        getListView().setOnItemClickListener(new EateriesOnItemClickListener(this));
        final LocationManager locationService = (LocationManager) getSystemService(LOCATION_SERVICE);
        final Location location = locationService.getLastKnownLocation(LocationManager.GPS_PROVIDER);        
        new EateriesLookupTask(this, new CachingRetriever(), new EateryGeoComparator(location.getLatitude(), location.getLongitude()), false).execute((Void)null);
    }

}
