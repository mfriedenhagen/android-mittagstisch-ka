/**
 * Copyright 2010 Mirko Friedenhagen 
 */

package de.friedenhagen.android.mittagstischka;

import roboguice.activity.GuiceActivity;
import roboguice.inject.InjectResource;
import roboguice.inject.InjectView;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Bundle;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.util.Log;
import android.widget.TextView;

/**
 * @author mirko
 * 
 */
public class InfoActivity extends GuiceActivity {

    private final static String TAG = Constants.LOG_PREFIX + InfoActivity.class.getSimpleName();

    @InjectView(android.R.id.text1)
    private TextView messageView;

    @InjectResource(R.string.info_githash)
    private String gitHash;

    /** {@inheritDoc} */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.info);
        final PackageInfo info;
        final String versionName;
        final int versionCode;
        try {
            info = getPackageManager().getPackageInfo(getPackageName(), 0);
            versionName = info.versionName;
            versionCode = info.versionCode;
        } catch (NameNotFoundException e) {
            throw new RuntimeException("Could not find " + getPackageName(), e);
        }
        final String message = getResources().getString(R.string.info_message, gitHash, gitHash.substring(0, 8),
                versionName, versionCode);
        Log.d(TAG, "raw_message=" + message);
        messageView.setText(Html.fromHtml(message));
        messageView.setMovementMethod(LinkMovementMethod.getInstance());
    }

}
