/**
 * Copyright 2010 Mirko Friedenhagen 
 */

package de.friedenhagen.android.mittagstischka;

import java.util.List;

import roboguice.activity.GuiceActivity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.Toast;

import com.google.inject.Inject;

import de.friedenhagen.android.mittagstischka.model.Eatery;
import de.friedenhagen.android.mittagstischka.retrievers.ApiException;
import de.friedenhagen.android.mittagstischka.retrievers.Retriever;

/**
 * @author mirko
 * 
 */
public class SplashActivity extends GuiceActivity {

    @Inject
    private MittagstischApplication application;

    @Inject
    private Retriever retriever;

    /** {@inheritDoc} */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash);
        final Toast toast = Toast.makeText(application, R.string.progress_message, Toast.LENGTH_LONG);
        new AsyncTask<Void, Void, List<Eatery>>() {
            /** {@inheritDoc} */
            @Override
            protected void onPreExecute() {
                toast.show();
            }

            /** {@inheritDoc} */
            @Override
            protected void onPostExecute(List<Eatery> eateries) {
                toast.cancel();
                // android.os.Debug.startMethodTracing("retrieve");
                application.setEateries(eateries);
                // android.os.Debug.stopMethodTracing();
                startActivity(new Intent(SplashActivity.this, EateriesTabActivity.class));
                SplashActivity.this.finish();
            }

            /** {@inheritDoc} */
            @Override
            protected List<Eatery> doInBackground(Void... params) {
                try {
                    return retriever.retrieveEateries();
                } catch (ApiException e) {
                    throw new RuntimeException("Message:", e);
                }
            }

        }.execute();
    }

}
