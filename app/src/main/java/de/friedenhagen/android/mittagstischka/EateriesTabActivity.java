/**
 * Copyright 2010 Mirko Friedenhagen 
 */

package de.friedenhagen.android.mittagstischka;

import roboguice.activity.GuiceTabActivity;
import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TabHost;
import android.widget.TabHost.TabSpec;

/**
 * @author mirko
 * 
 */
public class EateriesTabActivity extends GuiceTabActivity {

    private final static String TAG = Constants.LOG_PREFIX + EateriesTabActivity.class.getSimpleName();

    private TabHost tabHost;

    /** {@inheritDoc} */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        tabHost = getTabHost();
        addSpec("eateries_by_name", R.string.tab_alphabetically_title, android.R.drawable.ic_menu_sort_alphabetically,
                EateriesByNameActivity.class);
        addSpec("eateries_by_date", R.string.tab_bydate_title, android.R.drawable.ic_menu_month,
                EateriesByDateActivity.class);
        addSpec("eateries_by_distance", R.string.tab_bydist_title, android.R.drawable.ic_menu_compass,
                EateriesByDistanceActivity.class);
        tabHost.setCurrentTabByTag("eateries_by_name");
    }

    /** {@inheritDoc} */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        final MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    /** {@inheritDoc} */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.main_info) {
            final Intent info = new Intent(this, InfoActivity.class);
            this.startActivity(info);
            return true;
        } else {
            return super.onOptionsItemSelected(item);
        }
    }

    /**
     * @param tabId
     * @param tabName
     * @param drawableId
     * @param tabClass
     */
    private void addSpec(final String tabId, final int tabNameId, final int drawableId,
            final Class<? extends Activity> tabClass) {
        Log.d(TAG, "Add " + tabId);
        final String tabName = getResources().getString(tabNameId);
        final Intent intent = new Intent(this, tabClass);
        final TabSpec spec = tabHost.newTabSpec(tabId);
        final Drawable drawable = getResources().getDrawable(drawableId);
        spec.setIndicator(tabName, drawable);
        spec.setContent(intent);
        tabHost.addTab(spec);
    }

}
