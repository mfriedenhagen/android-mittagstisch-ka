/**
 * Copyright 2010 Mirko Friedenhagen 
 */

package de.friedenhagen.android.mittagstischka;

import java.util.Comparator;

import android.app.ListActivity;
import android.os.Bundle;
import android.widget.ListView;
import de.friedenhagen.android.mittagstischka.model.Eatery;

/**
 * @author mirko
 */
public abstract class EateriesByAbstractActivity extends ListActivity {

    /** {@inheritDoc} */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.eateries_list);
        final ListView listView = getListView();
        listView.setOnItemClickListener(new EateriesOnItemClickListener(this));
        new EateriesLookupTask(this, Utils.getRetriever(), getComparator()).execute((Void)null);
    }
    
    /**
     * Returns the comparator which will be used to sort the {@link Eatery} entries.
     * 
     * @return comparator
     */
    protected abstract Comparator<Eatery> getComparator();

}
