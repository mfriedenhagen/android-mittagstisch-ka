/**
 * Copyright 2010 Mirko Friedenhagen 
 */

package de.friedenhagen.android.mittagstischka;

import android.app.Activity;
import android.os.Bundle;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.util.Log;
import android.widget.TextView;

/**
 * @author mirko
 * 
 */
public class InfoActivity extends Activity {

    private final static String TAG = Constants.LOG_PREFIX + InfoActivity.class.getSimpleName();

    private TextView messageView;

    /** {@inheritDoc} */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.info);        
        messageView = (TextView) findViewById(android.R.id.text1);
        final String gitHash = getResources().getString(R.string.info_githash);
        final String message = getResources().getString(R.string.info_message, gitHash, gitHash);
        Log.d(TAG, "raw_message=" + message);        
        messageView.setText(Html.fromHtml(message));
        messageView.setMovementMethod(LinkMovementMethod.getInstance());
    }

}
