/**
 * Copyright 2010 Mirko Friedenhagen 
 */

package de.friedenhagen.android.mittagstischka;

import java.util.Locale;

import roboguice.activity.GuiceActivity;
import roboguice.inject.InjectExtra;
import roboguice.inject.InjectView;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.inject.Inject;

import de.friedenhagen.android.mittagstischka.model.Eatery;
import de.friedenhagen.android.mittagstischka.retrievers.ApiException;
import de.friedenhagen.android.mittagstischka.retrievers.Retriever;

/**
 * @author mirko
 * 
 */
public class EateryActivity extends GuiceActivity {

    private final static String TAG = Constants.LOG_PREFIX + EateryActivity.class.getSimpleName();

    @InjectView(R.id.eatery_title)
    private TextView titleView;

    @InjectView(R.id.eatery_content)
    private TextView contentView;

    @InjectView(R.id.eatery_image)
    private ImageView imageView;

    @InjectExtra(value = "eatery")
    private Eatery eatery;

    @InjectView(R.id.eatery_button_homepage)
    private ImageButton homePageButton;

    @Inject
    private Retriever retriever;

    /** {@inheritDoc} */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.eatery);
        if (eatery.homepage == null) {
            homePageButton.setEnabled(false);
        }
        titleView.setText(eatery.title);
        Log.d(TAG, "Getting text and picture for " + String.valueOf(eatery));
        new GetContentTask().execute(eatery.id);
        new GetImageTask().execute(eatery.id);
    }

    private class GetContentTask extends AsyncTask<Integer, Void, String> {

        /** {@inheritDoc} */
        @Override
        protected void onPostExecute(String result) {
            contentView.setText(result);
        }

        /** {@inheritDoc} */
        @Override
        protected String doInBackground(Integer... params) {
            try {
                return retriever.retrieveEateryContent(params[0]);
            } catch (ApiException e) {
                throw new RuntimeException("Message:", e);
            }
        }

    }

    private class GetImageTask extends AsyncTask<Integer, Void, Uri> {

        /** {@inheritDoc} */
        @Override
        protected void onPostExecute(Uri result) {
            imageView.setImageURI(result);
        }

        /** {@inheritDoc} */
        @Override
        protected Uri doInBackground(Integer... params) {
            try {
                return Uri.parse(retriever.retrieveEateryPictureUri(params[0]).toASCIIString());
            } catch (ApiException e) {
                throw new RuntimeException("Message:", e);
            }
        }

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
