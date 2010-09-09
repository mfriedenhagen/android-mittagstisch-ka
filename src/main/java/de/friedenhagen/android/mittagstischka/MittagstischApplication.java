/**
 * Copyright 2010 Mirko Friedenhagen 
 */

package de.friedenhagen.android.mittagstischka;

import java.util.ArrayList;
import java.util.List;

import com.google.inject.Module;

import roboguice.application.GuiceApplication;
import de.friedenhagen.android.mittagstischka.model.Eatery;

/**
 * @author mirko
 * 
 */
public class MittagstischApplication extends GuiceApplication {

    private final List<Eatery> eateries = new ArrayList<Eatery>();

    /** {@inheritDoc} */
    @Override
    protected void addApplicationModules(List<Module> modules) {
        modules.add(new MittagstischModule());
        super.addApplicationModules(modules);
    }
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
