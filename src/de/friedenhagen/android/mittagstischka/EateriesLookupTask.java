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
import android.view.View;
import de.friedenhagen.android.mittagstischka.MittagsTischHttpRetriever.ApiException;
import de.friedenhagen.android.mittagstischka.model.Eatery;

class EateriesLookupTask extends AsyncTask<Void, String, List<Eatery>> {
    
    /**
     * 
     */
    private final ListActivity listActivity;
    private final MittagsTischRetriever retriever;
    private final Comparator<Eatery> comparator;
    private final CharSequence oldTitle;
    private ProgressDialog progressDialog;
    
    /**
     * @param eateriesByNameActivity
     */
    EateriesLookupTask(final ListActivity listActivity, final MittagsTischRetriever retriever, final Comparator<Eatery> comparator, final boolean revert) {
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
        progressDialog = ProgressDialog.show(listActivity, "Loading index...", "About to load the index");
        listActivity.setTitle("Loading ...");            
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
            throw new RuntimeException(EateriesByNameActivityOld.TAG + "Error while retrieving data from " + MittagsTischHttpRetriever.MITTAGSTISCH_INDEX, e);
        }
        return Eatery.fromJsonArray(response);
    }
}