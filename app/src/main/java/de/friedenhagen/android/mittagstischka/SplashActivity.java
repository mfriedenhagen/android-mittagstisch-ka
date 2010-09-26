/**
 * Copyright 2010 Mirko Friedenhagen 
 */

package de.friedenhagen.android.mittagstischka;

import roboguice.activity.GuiceActivity;
import android.content.Intent;
import android.os.Bundle;

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
            @Override
            public void run() {
                try {
                    application.setEateries(retriever.retrieveEateries());
                    startActivity(new Intent(SplashActivity.this, EateriesTabActivity.class));
                    SplashActivity.this.finish();
                } catch (ApiException e) {
                    throw new RuntimeException("Message:", e);
                }
            }
        }).start();
    }

}
