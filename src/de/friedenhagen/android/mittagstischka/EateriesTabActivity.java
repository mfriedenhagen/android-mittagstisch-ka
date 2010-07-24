/**
 * Copyright 2010 Mirko Friedenhagen 
 */

package de.friedenhagen.android.mittagstischka;

import android.app.Activity;
import android.app.TabActivity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TabHost;
import android.widget.TabHost.TabSpec;

/**
 * @author mirko
 *
 */
public class EateriesTabActivity extends TabActivity {
    
    private TabHost tabHost;

    /** {@inheritDoc} */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        tabHost = getTabHost();
        addSpec("eateries_by_name", "A-Z", EateriesByNameActivity.class);
        addSpec("eateries_by_date", "Datum", EateriesByDateActivity.class);
        tabHost.setCurrentTabByTag("eateries_by_name");
    }

    /**
     * @param tabId
     * @param tabClass
     */
    void addSpec(final String tabId, final String tabName, final Class<? extends Activity> tabClass) {
        final Intent intent = new Intent(this, tabClass);        
        final TabSpec spec = tabHost.newTabSpec(tabId);
        spec.setIndicator(tabName);
        spec.setContent(intent);
        tabHost.addTab(spec);
    }

}
