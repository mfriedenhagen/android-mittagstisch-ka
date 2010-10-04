/**
 * Copyright 2010 Mirko Friedenhagen 
 */

package de.friedenhagen.android.mittagstischka;

import com.jayway.android.robotium.solo.Solo;

import android.test.ActivityInstrumentationTestCase2;
import android.test.suitebuilder.annotation.MediumTest;

/**
 * @author mirko
 * 
 */
public class SplashActivityTest extends ActivityInstrumentationTestCase2<SplashActivity> {

    private SplashActivity splashActivity;

    private Solo solo;

    /**
     * @param pkg
     * @param activityClass
     */
    public SplashActivityTest() {
        super(SplashActivityTest.class.getPackage().getName(), SplashActivity.class);
    }

    /** {@inheritDoc} */
    @Override
    protected void setUp() throws Exception {
        super.setUp();
        splashActivity = getActivity();
        solo = new Solo(getInstrumentation(), splashActivity);
    }

    /** {@inheritDoc} */
    @Override
    protected void tearDown() throws Exception {
        try {
            solo.finalize();
        } catch (Throwable e) {
            throw new RuntimeException("Message:", e);
        }
        splashActivity.finish();
        super.tearDown();
    }

    @MediumTest
    public void testSplash() {
        final String title = splashActivity.getString(R.string.app_name);
        assertEquals(title, splashActivity.getTitle());
        final MittagstischApplication application = (MittagstischApplication) splashActivity.getApplication();
        new WaitUntil("no eateries", 30000) {
            @Override
            boolean until() {
                return application.hasEateries();
            }
        }.waitUntil();
    }
}
