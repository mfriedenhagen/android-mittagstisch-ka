/**
 * Copyright 2010 Mirko Friedenhagen 
 */

package de.friedenhagen.android.mittagstischka;

import java.io.IOException;
import java.io.InputStream;

import android.app.Activity;
import android.os.Bundle;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.util.Log;
import android.widget.TextView;
import de.friedenhagen.android.mittagstischka.retrievers.IOUtils;

/**
 * @author mirko
 *
 */
public class InfoActivity extends Activity {
    
    private final static String TAG = Constants.LOG_PREFIX + InfoActivity.class.getSimpleName();
    
    private TextView titleView;
    
    private TextView messageView;

    /** {@inheritDoc} */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.info);
        titleView = (TextView) findViewById(android.R.id.text1);
        messageView = (TextView) findViewById(android.R.id.text2);
        final String gitHash;
        try {
            final InputStream stream = getAssets().open("GITHASH");
            try {
            gitHash = IOUtils.toUtf8String(IOUtils.toByteArray(stream));
            } finally {
                stream.close();
            }
        } catch (IOException e) {
            throw new RuntimeException("Message:", e);
        }
        final String message = getResources().getString(R.string.info_message, gitHash, gitHash);
        Log.d(TAG, "raw_message=" + message);        
        titleView.setText(R.string.info_title);
        messageView.setText(Html.fromHtml(message));
        messageView.setMovementMethod(LinkMovementMethod.getInstance());
    }

}
