/**
 * Copyright 2010 Mirko Friedenhagen 
 */

package de.friedenhagen.android.mittagstischka;

import android.app.ListActivity;
import android.os.Bundle;
import de.friedenhagen.android.mittagstischka.model.EateryTitleComparator;
import de.friedenhagen.android.mittagstischka.retrievers.MittagsTischCachingRetriever;

/**
 * @author mirko
 *
 */
public class EateriesByNameActivity extends ListActivity {
    
    /** {@inheritDoc} */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getListView());
        getListView().setOnItemClickListener(new EateriesOnItemClickListener(this));
        new EateriesLookupTask(this, new MittagsTischCachingRetriever(), EateryTitleComparator.INSTANCE, false).execute((Void)null);
    }

}
