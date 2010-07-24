package de.friedenhagen.android.mittagstischka;



import android.app.Activity;
import android.content.Intent;
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
import de.friedenhagen.android.mittagstischka.model.Eatery;
import de.friedenhagen.android.mittagstischka.model.EateryTitleComparator;

public class EateriesByNameActivityOld extends Activity implements AnimationListener {
    
    static final String TAG = EateriesByNameActivityOld.class.getSimpleName();
    
    Animation slideInView;

    Animation slideOutView;

    ProgressBar progressBarView;

    View titleBarView;

    TextView titleView;

    ListView eateriesView;

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
        //new EateriesLookupTask(eateriesView, getMittagsTischRetriever(), EateryTitleComparator.INSTANCE).execute();
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
