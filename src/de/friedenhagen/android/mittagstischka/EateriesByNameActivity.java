/**
 * Copyright 2010 Mirko Friedenhagen 
 */

package de.friedenhagen.android.mittagstischka;

import android.app.ListActivity;
import android.os.Bundle;
import android.widget.ListView;
import de.friedenhagen.android.mittagstischka.model.EateryTitleComparator;
import de.friedenhagen.android.mittagstischka.retrievers.CachingRetriever;

/**
 * @author mirko
 *
 */
public class EateriesByNameActivity extends ListActivity {
    
    /** {@inheritDoc} */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.eateries_list);
        getListView().setOnItemClickListener(new EateriesOnItemClickListener(this));
        new EateriesLookupTask(this, new CachingRetriever(), EateryTitleComparator.INSTANCE, false).execute((Void)null);
    }
}
