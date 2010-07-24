/*
 * EateryTitleComparator.java 22.07.2010
 * 
 * Copyright (c) 2010 1&1 Internet AG. All rights reserved.
 * 
 * $Id$
 */
package de.friedenhagen.android.mittagstischka.model;

import java.util.Comparator;


public class EateryTitleComparator implements Comparator<Eatery> {

    public final static EateryTitleComparator INSTANCE = new EateryTitleComparator();

    private EateryTitleComparator() {
        // no need for instances
    }

    @Override
    public int compare(Eatery arg0, Eatery arg1) {
        return arg0.title.compareToIgnoreCase(arg1.title);
    }

}
