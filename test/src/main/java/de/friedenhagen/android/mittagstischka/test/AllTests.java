/**
 * Copyright 2010 Mirko Friedenhagen 
 */

package de.friedenhagen.android.mittagstischka.test;

import de.friedenhagen.android.mittagstischka.Constants;
import android.test.suitebuilder.TestSuiteBuilder;
import android.util.Log;
import junit.framework.Test;
import junit.framework.TestSuite;

/**
 * @author mirko
 * 
 */
public class AllTests extends TestSuite {

    public static Test suite() {
        final TestSuite build = new TestSuiteBuilder(AllTests.class).includeAllPackagesUnderHere().build();
        Log.d(Constants.LOG_PREFIX + AllTests.class.getSimpleName(), "testCount=" + build.testCount());
        return build;
    }
}
