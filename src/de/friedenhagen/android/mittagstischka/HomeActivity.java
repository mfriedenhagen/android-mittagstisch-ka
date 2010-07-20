package de.friedenhagen.android.mittagstischka;

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
import android.widget.ProgressBar;
import android.widget.TextView;
import de.friedenhagen.android.mittagstischka.MittagsTischRetriever.ApiException;

public class HomeActivity extends Activity implements AnimationListener {
    
    private static final String TAG = HomeActivity.class.getSimpleName();
    
    private Animation slideIn;

    private Animation slideOut;

    private ProgressBar progressBar;

    private View titleBar;

    private WebView webView;

    private TextView title;

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
    
    private class LookupTask extends AsyncTask<String, String, String> {
        
        /** {@inheritDoc} */
        @Override
        protected void onPreExecute() {
            titleBar.setAnimation(slideIn);
        }
        /** {@inheritDoc} */
        @Override
        protected void onPostExecute(String result) {
            titleBar.setAnimation(slideOut);
            progressBar.setVisibility(View.INVISIBLE);
            webView.loadDataWithBaseURL(MittagsTischRetriever.MITTAGSTISCH_URL.toString(), result, "text/html",
                    "utf-8", null);
        }
        
        /** {@inheritDoc} */
        @Override
        protected String doInBackground(String... arg0) {
            final MittagsTischRetriever retriever = new MittagsTischRetriever();
            final JSONArray response;
            try {
                response = retriever.retrieveLocals();
            } catch (ApiException e) {
                throw new RuntimeException(TAG + "Error while retrieving data from " + MittagsTischRetriever.MITTAGSTISCH_URL, e);
            }
            final int length = response.length();
            Log.i(TAG, "response.length()" + length);
            final StringBuilder sb = new StringBuilder();
            sb.append("<html><body>");
            for (int i = 0; i < length; i++) {
                Log.d(TAG, "In entry " + i);
                try {
                    JSONObject local = response.getJSONObject(i);
                    final String title = local.getString("title");
                    final Integer id = local.getInt("id");
                    //"http://mittagstisch-ka.de/index.php?/archives/459-Hoepfner-Treff-am-Bannwald.html";
                    String homePage;
                    try {
                        homePage = local.getString("homepage");
                    } catch (JSONException e) {
                        homePage = "UNKNOWN";
                    }
                    Log.d(TAG, title);
                    Log.d(TAG, homePage);
                    sb //
                    .append("<a href=\"").append("http://mittagstisch-ka.de/app/" + id).append("\">") //
                            .append(title) //
                            .append("</a><br/>");
                } catch (JSONException e) {
                    // TODO Auto-generated catch block
                    throw new RuntimeException(TAG + "Error parsing " + response, e);
                }
            }
            sb.append("</body></html>");            
            return sb.toString();
        }
    }

}
