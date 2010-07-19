package de.friedenhagen.android.mittagstischka;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ScrollView;
import android.widget.TextView;

public class HomeActivity extends Activity {
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        final MittagsTischRetriever retriever = new MittagsTischRetriever();
        final ScrollView scrollView = new ScrollView(this);
        final TextView textView = new TextView(this);
        textView.setText("Mittagstisch Karlsruhe");
        final TextView textView2 = new TextView(this);
        textView2.setText("Liste");
        scrollView.addView(textView);
        scrollView.addView(textView2);
        setContentView(scrollView);
//        try {
//            retriever.retrieve();
//        } catch (ClientProtocolException e) {
//            throw new RuntimeException("Message:", e);
//        } catch (IOException e) {
//            throw new RuntimeException("Message:", e);
//        }
    }
}