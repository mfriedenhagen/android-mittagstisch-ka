/**
 * Copyright 2010 Mirko Friedenhagen 
 */

package de.friedenhagen.android.mittagstischka;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import roboguice.application.GuiceApplication;

import com.google.inject.Module;

import de.friedenhagen.android.mittagstischka.model.Eatery;

/**
 * @author mirko
 * 
 */
public class MittagstischApplication extends GuiceApplication {

    private List<Eatery> eateries = new ArrayList<Eatery>();

    /** {@inheritDoc} */
    @Override
    protected void addApplicationModules(List<Module> modules) {
        modules.add(new MittagstischModule());
        super.addApplicationModules(modules);
    }

    /**
     * @param eateries
     *            the eateries to set
     */
    public synchronized void setEateries(List<Eatery> eateries) {
        eateries.clear();
        Collections.copy(this.eateries, eateries);
    }

    /**
     * @return the eateries
     */
    public synchronized List<Eatery> getEateries() {
        return new ArrayList<Eatery>(this.eateries);
    }

    public boolean hasEateries() {
        return !eateries.isEmpty();
    }

}
