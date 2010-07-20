package de.friedenhagen.android.mittagstischka;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationUtils;
import android.widget.ProgressBar;
import android.widget.TextView;
import de.friedenhagen.android.mittagstischka.MittagsTischRetriever.ApiException;

public class HomeActivity extends Activity implements AnimationListener {
    private Animation slideIn;

    private Animation slideOut;

    private ProgressBar progressBar;

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        slideIn = AnimationUtils.loadAnimation(this, R.anim.slide_in);
        slideOut = AnimationUtils.loadAnimation(this, R.anim.slide_out);
        slideIn.setAnimationListener(this);
        progressBar = (ProgressBar) findViewById(R.id.progress);
        final MittagsTischRetriever retriever = new MittagsTischRetriever();
        final JSONArray response;
        try {
            response = retriever.retrieveLocals();
        } catch (ApiException e) {
            throw new RuntimeException("Message:", e);
        }
        final int length = response.length();
        final StringBuilder sb = new StringBuilder();
        for (int i = 0; i < length; i++) {
            try {
                JSONObject local = response.getJSONObject(i);
                sb.append(local.getString("title")).append("\n");
            } catch (JSONException e) {
                // TODO Auto-generated catch block
                throw new RuntimeException("Message:", e);
            }
        }
        final TextView textView2 = (TextView) findViewById(R.id.textView2);
        textView2.setText(sb.toString());
        // final TextView t1 = (TextView) findViewById(R.id.textView1);
        // final TextView t2 = (TextView) findViewById(R.id.textView2);
        // final ListView listView = (ListView) findViewById(R.id.listView);
        // try {
        // retriever.retrieve();
        // } catch (ClientProtocolException e) {
        // throw new RuntimeException("Message:", e);
        // } catch (IOException e) {
        // throw new RuntimeException("Message:", e);
        // }
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
}
