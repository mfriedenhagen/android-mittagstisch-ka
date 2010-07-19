package de.friedenhagen.android.mittagstischka;

import android.app.Activity;
import android.os.Bundle;

public class HomeActivity extends Activity {
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        final MittagsTischRetriever retriever = new MittagsTischRetriever();
//        final TextView t1 = (TextView) findViewById(R.id.textView1);
//        final TextView t2 = (TextView) findViewById(R.id.textView2);
//        final ListView listView = (ListView) findViewById(R.id.listView);
//        try {
//            retriever.retrieve();
//        } catch (ClientProtocolException e) {
//            throw new RuntimeException("Message:", e);
//        } catch (IOException e) {
//            throw new RuntimeException("Message:", e);
//        }
    }
    
    public void quit() {
        super.finish();
    }
}