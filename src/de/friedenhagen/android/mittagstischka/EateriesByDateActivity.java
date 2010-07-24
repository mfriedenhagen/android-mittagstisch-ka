/**
 * Copyright 2010 Mirko Friedenhagen 
 */

package de.friedenhagen.android.mittagstischka;

import de.friedenhagen.android.mittagstischka.model.EateryDateComparator;
import android.app.ListActivity;
import android.os.Bundle;
import android.util.Log;

/**
 * @author mirko
 *
 */
public class EateriesByDateActivity extends ListActivity {
        
    
    /** {@inheritDoc} */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getListView());
        getListView().setOnItemClickListener(new EateriesOnItemClickListener(this));
        new EateriesLookupTask(this, new MittagsTischCachingRetriever(), EateryDateComparator.INSTANCE, true).execute((Void)null);
    }

}