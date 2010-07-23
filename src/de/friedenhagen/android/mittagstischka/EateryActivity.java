/**
 * Copyright 2010 Mirko Friedenhagen 
 */

package de.friedenhagen.android.mittagstischka;

import java.util.Locale;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationUtils;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import de.friedenhagen.android.mittagstischka.MittagsTischHttpRetriever.ApiException;
import de.friedenhagen.android.mittagstischka.model.Eatery;

/**
 * @author mirko
 * 
 */
public class EateryActivity extends Activity {

    private static final String NAME = Eatery.class.getName();

    private final static String TAG = EateryActivity.class.getSimpleName();

    private TextView titleView;

    private TextView contentView;

    private ImageView imageView;

    private Eatery eatery;

    /** {@inheritDoc} */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.eatery);
        final Bundle extras = getIntent().getExtras();
        Log.d(TAG, String.valueOf(extras.keySet()));
        Log.d(TAG, "isinbundle:" + extras.containsKey(NAME));
        eatery = (Eatery) extras.getSerializable(NAME);
        Log.i(TAG, "eatery:" + eatery);
        assert eatery != null;
        titleView = (TextView) findViewById(R.id.eatery_title);
        contentView = (TextView) findViewById(R.id.eatery_content);
        imageView = (ImageView) findViewById(R.id.eatery_image);
        final ImageButton homePageButton = (ImageButton) findViewById(R.id.eatery_button_homepage);
        if (eatery.homepage == null) {
            homePageButton.setEnabled(false);
        }
        titleView.setText(eatery.title);
        new GetContentTask().execute(eatery.id);
        new GetImageTask().execute(eatery.id);
    }

    private class GetContentTask extends AsyncTask<Integer, Void, String> {

        /** {@inheritDoc} */
        @Override
        protected void onPostExecute(String result) {
            contentView.setText(result);
            contentView.setMovementMethod(new ScrollingMovementMethod());
        }

        /** {@inheritDoc} */
        @Override
        protected String doInBackground(Integer... params) {
            try {
                return getMittagsTischRetriever().retrieveEateryContent(params[0]);
            } catch (ApiException e) {
                throw new RuntimeException("Message:", e);
            }
        }

    }

    private class GetImageTask extends AsyncTask<Integer, Void, Bitmap> {

        /** {@inheritDoc} */
        @Override
        protected void onPostExecute(Bitmap result) {
            imageView.setImageBitmap(result);
        }

        /** {@inheritDoc} */
        @Override
        protected Bitmap doInBackground(Integer... params) {
            try {
                return getMittagsTischRetriever().retrieveEateryPicture(params[0]);
            } catch (ApiException e) {
                throw new RuntimeException("Message:", e);
            }
        }

    }

    MittagsTischRetriever getMittagsTischRetriever() {
        return new MittagsTischCachingRetriever();
    }

    public void onClickGotoMap(final View clickedButton) {
        final String uriString = String.format(Locale.ENGLISH, "geo:%s,%s?z=19", eatery.latitude, eatery.longitude);
        final Uri uri = Uri.parse(uriString);
        final Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(uri);
        startActivity(intent);
    }
    public void onClickGotoHomepage(final View clickedButton) {        
        final Uri uri = Uri.parse(eatery.homepage);
        final Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(uri);
        startActivity(intent);
    }

}
