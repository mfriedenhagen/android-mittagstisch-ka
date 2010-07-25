/**
 * Copyright 2010 Mirko Friedenhagen 
 */

package de.friedenhagen.android.mittagstischka;

import java.util.Comparator;

import de.friedenhagen.android.mittagstischka.model.Eatery;
import de.friedenhagen.android.mittagstischka.model.EateryDateComparator;

/**
 * @author mirko
 * 
 */
public class EateriesByDateActivity extends EateriesByAbstractActivity {

    /** {@inheritDoc} */
    @Override
    protected Comparator<Eatery> getComparator() {
        return EateryDateComparator.INSTANCE;
    }

}
