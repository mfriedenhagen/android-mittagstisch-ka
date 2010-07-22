/**
 * Copyright 2010 Mirko Friedenhagen 
 */

package de.friedenhagen.android.mittagstischka;

import de.friedenhagen.android.mittagstischka.model.Eatery;
import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

/**
 * @author mirko
 *
 */
public class EateryActivity extends Activity {
    
    private static final String NAME = Eatery.class.getName();

    private final static String TAG = EateryActivity.class.getSimpleName();
    
    private TextView titleView;
    
    private TextView contentView;

    /** {@inheritDoc} */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.eatery);
        titleView = (TextView) findViewById(R.id.eatery_title);
        contentView = (TextView) findViewById(R.id.eatery_content);
        final Bundle extras = getIntent().getExtras();
        Log.d(TAG, String.valueOf(extras.keySet()));
        Log.d(TAG, "isinbundle:" + extras.containsKey(NAME));
        final Eatery eatery = (Eatery) extras.getSerializable(NAME);
        Log.d(TAG, "eatery:" + eatery);
        assert eatery != null;
        titleView.setText(eatery.title);
        contentView.setText(String.valueOf(eatery.id));
    }

}
