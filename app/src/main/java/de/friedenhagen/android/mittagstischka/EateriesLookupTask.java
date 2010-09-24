/**
 * Copyright 2010 Mirko Friedenhagen 
 */

package de.friedenhagen.android.mittagstischka;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import android.app.ListActivity;
import android.os.AsyncTask;
import android.widget.Toast;
import de.friedenhagen.android.mittagstischka.model.Eatery;
import de.friedenhagen.android.mittagstischka.retrievers.ApiException;
import de.friedenhagen.android.mittagstischka.retrievers.Retriever;

class EateriesLookupTask extends AsyncTask<Void, String, List<Eatery>> {

    private final static String TAG = Constants.LOG_PREFIX + EateriesLookupTask.class.getSimpleName();

    /**
     * 
     */
    private final ListActivity listActivity;

    private final Retriever retriever;

    private final Comparator<Eatery> comparator;

    private final MittagstischApplication application;

    private final Toast toast;

    /**
     * @param eateriesByNameActivity
     */
    EateriesLookupTask(final ListActivity listActivity, final Retriever retriever, final Comparator<Eatery> comparator) {
        this.listActivity = listActivity;
        this.retriever = retriever;
        this.comparator = comparator;
        application = (MittagstischApplication) listActivity.getApplication();
        toast = Toast.makeText(application, R.string.progress_message, Toast.LENGTH_LONG);
    }

    /** {@inheritDoc} */
    @Override
    protected void onPreExecute() {
        toast.show();
    }

    /** {@inheritDoc} */
    @Override
    protected void onPostExecute(final List<Eatery> eateries) {
        Collections.sort(eateries, comparator);
        listActivity.setListAdapter(new EateryAdapter(eateries));
        toast.cancel();
    }

    /** {@inheritDoc} */
    @Override
    protected List<Eatery> doInBackground(Void... arg0) {
        if (application.hasEateries()) {
            return application.getEateries();
        } else {
            try {
                final List<Eatery> eateries = retriever.retrieveEateries();
                application.setEateries(eateries);
                return eateries;
            } catch (ApiException e) {
                throw new RuntimeException(
                        TAG + "Error while retrieving data", e);
            }
        }
    }
}
