package de.friedenhagen.android.mittagstischka;

import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import de.friedenhagen.android.mittagstischka.model.Eatery;

/**
 * @author mirko
 * 
 */
final class EateriesOnItemClickListener implements OnItemClickListener {

    private final Activity activity;

    public EateriesOnItemClickListener(final Activity activity) {
        this.activity = activity;
    }

    /** {@inheritDoc} */
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        final Intent eateryIntent = new Intent(parent.getContext(), EateryActivity.class);
        final Eatery eatery = (Eatery) parent.getAdapter().getItem(position);
        eateryIntent.putExtra("eatery", eatery);
        activity.startActivity(eateryIntent);
    }
}
