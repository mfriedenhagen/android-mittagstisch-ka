package de.friedenhagen.android.mittagstischka;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationUtils;
import android.webkit.WebView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import de.friedenhagen.android.mittagstischka.MittagsTischRetriever.ApiException;
import de.friedenhagen.android.mittagstischka.model.Eateries;
import de.friedenhagen.android.mittagstischka.model.Eatery;

public class HomeActivity extends Activity implements AnimationListener {
    
    private static final String TAG = HomeActivity.class.getSimpleName();
    
    private Animation slideIn;

    private Animation slideOut;

    private ProgressBar progressBar;

    private View titleBar;

    private WebView webView;

    private TextView title;

    private ListView eateriesList;

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        slideIn = AnimationUtils.loadAnimation(this, R.anim.slide_in);
        slideOut = AnimationUtils.loadAnimation(this, R.anim.slide_out);
        slideIn.setAnimationListener(this);
        progressBar = (ProgressBar) findViewById(R.id.progress);
        titleBar = findViewById(R.id.title_bar);
        title = (TextView) findViewById(R.id.title);
        eateriesList = (ListView) findViewById(R.id.eateriesList);
        webView = (WebView) findViewById(R.id.webview);
        webView.setBackgroundColor(0);
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
        progressBar.setVisibility(View.VISIBLE);
    }

    /** {@inheritDoc} */
    @Override
    protected void onNewIntent(Intent intent) {
        new LookupTask().execute(null);
    }
    
    private class LookupTask extends AsyncTask<String, String, Eateries> {
        
        /** {@inheritDoc} */
        @Override
        protected void onPreExecute() {
            titleBar.setAnimation(slideIn);
        }
        /** {@inheritDoc} */
        @Override
        protected void onPostExecute(Eateries result) {
            ArrayList<View> list = new ArrayList<View>();
            for (final Eatery eatery : result.eateryList) {
                final TextView view = new TextView(HomeActivity.this);
                view.setTag(eatery.id);
                view.setText(eatery.title);
                list.add(view);
            }
            eateriesList.addTouchables(list);
            titleBar.setAnimation(slideOut);
            progressBar.setVisibility(View.INVISIBLE);
        }
        
        /** {@inheritDoc} */
        @Override
        protected Eateries doInBackground(String... arg0) {
            final MittagsTischRetriever retriever = new MittagsTischRetriever();
            final JSONArray response;
            try {
                response = retriever.retrieveLocals();
            } catch (ApiException e) {
                throw new RuntimeException(TAG + "Error while retrieving data from " + MittagsTischRetriever.MITTAGSTISCH_URL, e);
            }
            final Eateries eateries = Eateries.fromJsonObject(response);
            return eateries;
        }
    }

}
