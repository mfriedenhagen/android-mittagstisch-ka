package de.friedenhagen.android.mittagstischka;

import java.util.List;

import org.json.JSONArray;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationUtils;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import de.friedenhagen.android.mittagstischka.MittagsTischRetriever.ApiException;
import de.friedenhagen.android.mittagstischka.model.Eatery;

public class HomeActivity extends Activity implements AnimationListener {
    
    private static final String TAG = HomeActivity.class.getSimpleName();
    
    private Animation slideInView;

    private Animation slideOutView;

    private ProgressBar progressBarView;

    private View titleBarView;

    private TextView titleView;

    private ListView eateriesView;

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        slideInView = AnimationUtils.loadAnimation(this, R.anim.slide_in);
        slideOutView = AnimationUtils.loadAnimation(this, R.anim.slide_out);
        slideInView.setAnimationListener(this);
        progressBarView = (ProgressBar) findViewById(R.id.progress);
        titleBarView = findViewById(R.id.title_bar);
        titleView = (TextView) findViewById(R.id.title);
        eateriesView = (ListView) findViewById(R.id.eateries);
        onNewIntent(getIntent());
    }

    public void quit() {
        super.finish();
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

    /** {@inheritDoc} */
    @Override
    protected void onNewIntent(Intent intent) {
        new LookupTask().execute();
    }
    
    private class LookupTask extends AsyncTask<Void, String, List<Eatery>> {
        
        private CharSequence oldTitle;

        /** {@inheritDoc} */
        @Override
        protected void onPreExecute() {
            oldTitle = titleView.getText();
            titleBarView.setAnimation(slideInView);
            titleView.setText(" ...");
            
        }
        /** {@inheritDoc} */
        @Override
        protected void onPostExecute(List<Eatery> eateries) {
            eateriesView.setAdapter(new EateryAdapter(eateries));
            titleBarView.setAnimation(slideOutView);
            progressBarView.setVisibility(View.INVISIBLE);
            titleView.setText(oldTitle);
        }
        
        /** {@inheritDoc} */
        @Override
        protected List<Eatery> doInBackground(Void... arg0) {
            final MittagsTischRetriever retriever = new MittagsTischRetriever();
            final JSONArray response;
            try {
                response = retriever.retrieveLocals();
            } catch (ApiException e) {
                throw new RuntimeException(TAG + "Error while retrieving data from " + MittagsTischRetriever.MITTAGSTISCH_URL, e);
            }
            return Eatery.fromJsonArray(response);
        }
    }

}
