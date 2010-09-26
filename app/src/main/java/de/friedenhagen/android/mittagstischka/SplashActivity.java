/**
 * Copyright 2010 Mirko Friedenhagen 
 */

package de.friedenhagen.android.mittagstischka;

import roboguice.activity.GuiceActivity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.google.inject.Inject;

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
        new Thread(new Runnable() {

            private final Toast toast = Toast.makeText(application, R.string.progress_message, Toast.LENGTH_LONG);

            @Override
            public void run() {
                try {
                    toast.show();
                    application.setEateries(retriever.retrieveEateries());
                    toast.cancel();
                    startActivity(new Intent(SplashActivity.this, EateriesTabActivity.class));
                    SplashActivity.this.finish();
                } catch (ApiException e) {
                    throw new RuntimeException("Message:", e);
                }
            }
        }).start();
    }

}
