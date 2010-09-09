/**
 * Copyright 2010 Mirko Friedenhagen 
 */

package de.friedenhagen.android.mittagstischka;

import java.util.Comparator;

import com.google.inject.Inject;

import roboguice.activity.GuiceListActivity;
import android.os.Bundle;
import android.widget.ListView;
import de.friedenhagen.android.mittagstischka.model.Eatery;
import de.friedenhagen.android.mittagstischka.retrievers.Retriever;

/**
 * @author mirko
 */
public abstract class EateriesByAbstractActivity extends GuiceListActivity {

    @Inject
    private Retriever retriever;
    
    /** {@inheritDoc} */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.eateries_list);
        final ListView listView = getListView();
        listView.setOnItemClickListener(new EateriesOnItemClickListener(this));
        new EateriesLookupTask(this, retriever, getComparator()).execute((Void)null);
    }
    
    /**
     * Returns the comparator which will be used to sort the {@link Eatery} entries.
     * 
     * @return comparator
     */
    protected abstract Comparator<Eatery> getComparator();

}
