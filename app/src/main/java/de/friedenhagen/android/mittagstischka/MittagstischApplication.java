/**
 * Copyright 2010 Mirko Friedenhagen 
 */

package de.friedenhagen.android.mittagstischka;

import java.util.Collections;
import java.util.List;

import roboguice.application.GuiceApplication;

import com.google.inject.Module;

import de.friedenhagen.android.mittagstischka.model.Eateries;

/**
 * @author mirko
 * 
 */
public class MittagstischApplication extends GuiceApplication {

    private Eateries eateries = new Eateries(Collections.EMPTY_LIST);

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
    public synchronized void setEateries(Eateries eateries) {
        this.eateries = eateries;        
    }

    /**
     * @return the eateries
     */
    public synchronized Eateries getEateries() {
        return eateries;
    }
    
    public boolean hasEateries() {
        return !eateries.isEmpty();
    }

}
