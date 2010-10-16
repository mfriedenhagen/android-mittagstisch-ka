/**
 * Copyright 2010 Mirko Friedenhagen 
 */

package de.friedenhagen.android.mittagstischka.model;

import java.util.Comparator;

/**
 * @author mirko
 * 
 */
public class EateryDateComparator implements Comparator<Eatery> {

    public final static EateryDateComparator INSTANCE = new EateryDateComparator();

    private EateryDateComparator() {
    }

    /** {@inheritDoc} */
    @Override
    public int compare(Eatery arg0, Eatery arg1) {
        return arg0.date.compareTo(arg1.date);
    }

}
