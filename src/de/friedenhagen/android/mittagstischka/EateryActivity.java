/**
 * Copyright 2010 Mirko Friedenhagen 
 */

package de.friedenhagen.android.mittagstischka;

import java.net.URI;

import de.friedenhagen.android.mittagstischka.MittagsTischHttpRetriever.ApiException;
import de.friedenhagen.android.mittagstischka.model.Eatery;
import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

/**
 * @author mirko
 *
 */
public class EateryActivity extends Activity implements AnimationListener {
    
    private static final String NAME = Eatery.class.getName();

    private final static String TAG = EateryActivity.class.getSimpleName();
    
    private TextView titleView;
    
    private TextView contentView;

    private Animation slideInView;

    private ProgressBar progressBarView;

    private Animation slideOutView;

    private View titleBarView;

    private ImageView imageView;

    /** {@inheritDoc} */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.eatery);
        slideInView = AnimationUtils.loadAnimation(this, R.anim.slide_in);
        slideOutView = AnimationUtils.loadAnimation(this, R.anim.slide_out);
        slideInView.setAnimationListener(this);
        progressBarView = (ProgressBar) findViewById(R.id.eatery_progress);
        titleBarView = findViewById(R.id.eatery_title_bar);
        titleView = (TextView) findViewById(R.id.eatery_title);
        contentView = (TextView) findViewById(R.id.eatery_content);
        imageView = (ImageView) findViewById(R.id.eatery_image);        
        final Bundle extras = getIntent().getExtras();
        Log.d(TAG, String.valueOf(extras.keySet()));
        Log.d(TAG, "isinbundle:" + extras.containsKey(NAME));
        final Eatery eatery = (Eatery) extras.getSerializable(NAME);
        Log.i(TAG, "eatery:" + eatery);
        assert eatery != null;        
        titleView.setText(eatery.title);                
        new GetContentTask().execute(eatery.id);
        new GetImageTask().execute(eatery.id);
    }
    
    /** {@inheritDoc} */
    @Override
    public void onAnimationEnd(Animation animation) {
        // Not needed
    }

    /** {@inheritDoc} */
    @Override
    public void onAnimationRepeat(Animation animation) {
        // Not needed
    }

    /** {@inheritDoc} */
    @Override
    public void onAnimationStart(Animation animation) {
        progressBarView.setVisibility(View.VISIBLE);
    }

    
    private class GetContentTask extends AsyncTask<Integer, Void, String> {
        
        /** {@inheritDoc} */
        @Override
        protected void onPreExecute() {            
            titleBarView.setAnimation(slideInView);
        }

        /** {@inheritDoc} */
        @Override
        protected void onPostExecute(String result) {
            contentView.setText(result);            
            titleBarView.setAnimation(slideOutView);
            progressBarView.setVisibility(View.INVISIBLE);
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
            imageView.setImageBitmap(result) ;           
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

}
