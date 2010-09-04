/**
 * Copyright 2010 Mirko Friedenhagen 
 */

package de.friedenhagen.android.mittagstischka;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import de.friedenhagen.android.mittagstischka.model.Eatery;

import android.app.Application;

/**
 * @author mirko
 * 
 */
public class MittagstischApplication extends Application {

    private final List<Eatery> eateries = new ArrayList<Eatery>();

    /**
     * @param newEateries
     *            the eateries to set
     */
    public synchronized void setEateries(List<Eatery> newEateries) {
        eateries.clear();
        eateries.addAll(newEateries);
    }

    /**
     * @return the eateries
     */
    public synchronized List<Eatery> getEateries() {
        return new ArrayList<Eatery>(eateries);
    }
    
    public boolean hasEateries() {
        return !eateries.isEmpty();
    }

}
