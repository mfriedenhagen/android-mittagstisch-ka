/**
 * Copyright 2010 Mirko Friedenhagen 
 */

package de.friedenhagen.android.mittagstischka.retrievers;

import java.io.IOException;
import java.util.List;

import org.json.JSONException;

import de.friedenhagen.android.mittagstischka.model.Eateries;
import de.friedenhagen.android.mittagstischka.model.Eatery;
import de.friedenhagen.android.mittagstischka.model.EateryTest;

/**
 * @author mirko
 *
 */
public class TUtils {

    /**
     * @return
     * @throws JSONException
     * @throws IOException
     */
    static Eateries getEateriesFromJson() throws JSONException, IOException {
        final List<Eatery> list = Eatery.fromJsonArray(EateryTest.getEateries());
        final Eateries eateries = new Eateries(list);
        return eateries;
    }

}
