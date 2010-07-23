package de.friedenhagen.android.mittagstischka;

import java.util.Collections;
import java.util.List;

import org.json.JSONArray;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import de.friedenhagen.android.mittagstischka.MittagsTischHttpRetriever.ApiException;
import de.friedenhagen.android.mittagstischka.model.Eatery;

public class EateriesByNameActivity extends Activity implements AnimationListener {
    
    private static final String TAG = EateriesByNameActivity.class.getSimpleName();
    
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
        setContentView(R.layout.eateries_by_name);
        slideInView = AnimationUtils.loadAnimation(this, R.anim.slide_in);
        slideOutView = AnimationUtils.loadAnimation(this, R.anim.slide_out);
        slideInView.setAnimationListener(this);
        progressBarView = (ProgressBar) findViewById(R.id.main_progress);
        titleBarView = findViewById(R.id.main_title_bar);
        titleView = (TextView) findViewById(R.id.main_title);
        eateriesView = (ListView) findViewById(R.id.main_eateries);
        eateriesView.setTextFilterEnabled(true);
        eateriesView.setOnItemClickListener(new OnItemClickListener() {

            
            /**
             * {@inheritDoc}
             */
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                final Intent eateryIntent = new Intent(parent.getContext(), EateryActivity.class);
                final Eatery eatery = (Eatery)parent.getAdapter().getItem(position);
                Log.i(TAG, "eatery=" + eatery + ", position=" + position);
                eateryIntent.putExtra(Eatery.class.getName(), eatery);                
                //eateryIntent.putExtra(Eatery.class.getName(), view.getAdapter())
                startActivity(eateryIntent);
                
            }
        });
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
        protected void onPostExecute(final List<Eatery> eateries) {
            Collections.sort(eateries, EateryTitleComparator.INSTANCE);
            eateriesView.setAdapter(new EateryAdapter(eateries));
            titleBarView.setAnimation(slideOutView);
            progressBarView.setVisibility(View.INVISIBLE);
            titleView.setText(oldTitle);
        }
        
        /** {@inheritDoc} */
        @Override
        protected List<Eatery> doInBackground(Void... arg0) {
            final JSONArray response;
            try {
                response = getMittagsTischRetriever().retrieveEateries();
            } catch (ApiException e) {
                throw new RuntimeException(TAG + "Error while retrieving data from " + MittagsTischHttpRetriever.MITTAGSTISCH_INDEX, e);
            }
            return Eatery.fromJsonArray(response);
        }
    }
    
    public void onEateriesClick(final ListView view) {
        final long selectedItemId = view.getSelectedItemId();
        final Intent eateryIntent = new Intent(this, EateryActivity.class);
        Eatery eatery = (Eatery)view.getAdapter().getItem((int)selectedItemId);
        eateryIntent.putExtra("title", eatery.title);
        eateryIntent.putExtra("id", eatery.id);
        //eateryIntent.putExtra(Eatery.class.getName(), view.getAdapter())
        startActivity(eateryIntent);
    }

    MittagsTischRetriever getMittagsTischRetriever() {
        return new MittagsTischCachingRetriever();
    }
}
