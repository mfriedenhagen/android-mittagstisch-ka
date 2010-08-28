/**
 * Copyright 2010 Mirko Friedenhagen 
 */

package de.friedenhagen.android.mittagstischka;

import android.app.Activity;
import android.app.TabActivity;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.widget.TabHost;
import android.widget.TabHost.TabSpec;
import android.widget.TextView;

/**
 * @author mirko
 * 
 */
public class EateriesTabActivity extends TabActivity {

    private final static String TAG = Constants.LOG_PREFIX + EateriesTabActivity.class.getSimpleName();

    private TabHost tabHost;

    /** {@inheritDoc} */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        tabHost = getTabHost();
        addSpec("eateries_by_name", "A-Z", android.R.drawable.ic_menu_sort_alphabetically, EateriesByNameActivity.class);
        addSpec("eateries_by_date", "Datum", android.R.drawable.ic_menu_month, EateriesByDateActivity.class);
        // addSpec("eateries_by_distance", "Entfernung", EateriesByDistanceActivity.class);
        tabHost.setCurrentTabByTag("eateries_by_name");
    }

    /**
     * @param tabId
     * @param tabName
     * @param drawableId
     * @param tabClass
     */
    private void addSpec(final String tabId, final String tabName, final int drawableId,
            final Class<? extends Activity> tabClass) {
        Log.d(TAG, "Add " + tabId);
        final Intent intent = new Intent(this, tabClass);
        final TabSpec spec = tabHost.newTabSpec(tabId);
        final TextView textView = new TextView(this);
        final Drawable drawable = getResources().getDrawable(drawableId);

        textView.setText(tabName);
        spec.setIndicator(tabName, drawable);
        spec.setContent(intent);
        tabHost.addTab(spec);
    }

}
