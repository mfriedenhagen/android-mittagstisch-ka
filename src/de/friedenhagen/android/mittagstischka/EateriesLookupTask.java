/**
 * Copyright 2010 Mirko Friedenhagen 
 */

package de.friedenhagen.android.mittagstischka;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.json.JSONArray;

import android.app.ListActivity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import de.friedenhagen.android.mittagstischka.model.Eatery;
import de.friedenhagen.android.mittagstischka.retrievers.ApiException;
import de.friedenhagen.android.mittagstischka.retrievers.HttpRetriever;
import de.friedenhagen.android.mittagstischka.retrievers.Retriever;

class EateriesLookupTask extends AsyncTask<Void, String, List<Eatery>> {
    
    private final static String TAG = EateriesLookupTask.class.getSimpleName();
    
    /**
     * 
     */
    private final ListActivity listActivity;
    private final Retriever retriever;
    private final Comparator<Eatery> comparator;
    private final CharSequence oldTitle;
    private ProgressDialog progressDialog;
    
    /**
     * @param eateriesByNameActivity
     */
    EateriesLookupTask(final ListActivity listActivity, final Retriever retriever, final Comparator<Eatery> comparator, final boolean revert) {
        this.listActivity = listActivity;
        this.retriever = retriever;
        this.oldTitle = listActivity.getTitle();
        if (revert) {
            this.comparator = Collections.reverseOrder(comparator);
        } else {
            this.comparator = comparator;
        }
    }


    /** {@inheritDoc} */
    @Override
    protected void onPreExecute() {
        final String title = listActivity.getResources().getString(R.string.progress_title);
        final String message = listActivity.getResources().getString(R.string.progress_message);
        progressDialog = ProgressDialog.show(listActivity, title, message);
        listActivity.setTitle(title);            
    }
    
    /** {@inheritDoc} */
    @Override
    protected void onPostExecute(final List<Eatery> eateries) {
        Collections.sort(eateries, comparator);
        listActivity.setListAdapter(new EateryAdapter(eateries));
        listActivity.setTitle(oldTitle);
        progressDialog.dismiss();
    }
    
    /** {@inheritDoc} */
    @Override
    protected List<Eatery> doInBackground(Void... arg0) {
        final JSONArray response;
        try {
            response = retriever.retrieveEateries();
        } catch (ApiException e) {
            throw new RuntimeException(TAG + "Error while retrieving data from " + HttpRetriever.MITTAGSTISCH_INDEX, e);
        }
        return Eatery.fromJsonArray(response);
    }
}