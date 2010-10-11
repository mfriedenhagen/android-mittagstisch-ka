/**
 * Copyright 2010 Mirko Friedenhagen 
 */

package de.friedenhagen.android.mittagstischka.model;

import java.util.Collections;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import android.location.Location;

/**
 * @author mirko
 * 
 */
@PrepareForTest(Location.class)
@RunWith(PowerMockRunner.class)
public class EateryGeoComparatorTest extends EateryAbstractComparatorTest {

    @Test
    public void test() {
        PowerMockito.mockStatic(Location.class);
        Collections.sort(list, new EateryGeoComparator(0, 0));
        PowerMockito.verifyStatic(Mockito.atLeast(list.size()));
    }
}
