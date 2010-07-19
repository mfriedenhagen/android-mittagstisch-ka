package de.friedenhagen.android.mittagstischka;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

public class HomeActivity extends Activity {
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        final TextView textView = new TextView(this);
        textView.setText("Mittagstisch Karlsruhe");
        setContentView(textView);
    }
}