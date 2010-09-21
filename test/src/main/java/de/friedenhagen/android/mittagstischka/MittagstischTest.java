/**
 * Copyright 2010 Mirko Friedenhagen 
 */

package de.friedenhagen.android.mittagstischka;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import android.test.ActivityInstrumentationTestCase2;
import android.test.suitebuilder.annotation.MediumTest;
import android.util.Log;
import android.view.View;

import com.jayway.android.robotium.solo.Solo;

/**
 * @author mirko
 * 
 */
public class MittagstischTest extends ActivityInstrumentationTestCase2<EateriesTabActivity> {

    private final static String TAG = Constants.LOG_PREFIX + MittagstischTest.class.getSimpleName();

    private EateriesTabActivity tabActivity;

    private Solo solo;

    /**
     * @param pkg
     * @param activityClass
     */
    public MittagstischTest() {
        super(MittagstischTest.class.getPackage().getName(), EateriesTabActivity.class);
    }

    /** {@inheritDoc} */
    @Override
    protected void setUp() throws Exception {
        super.setUp();
        tabActivity = this.getActivity();
        solo = new Solo(getInstrumentation(), tabActivity);
        sleepSeconds(2);
    }

    /** {@inheritDoc} */
    @Override
    protected void tearDown() throws Exception {
        try {
            solo.finalize();
        } catch (Throwable e) {
            throw new RuntimeException("Message:", e);
        }
        super.tearDown();
    }

    @MediumTest
    public void testPreconditions() {
        assertNotNull(tabActivity);
    }

    @MediumTest
    public void testTitle() {
        final String title = tabActivity.getString(R.string.app_name);
        assertEquals(title, tabActivity.getTitle());
    }

    @MediumTest
    public void testInfoDialog() {
        solo.sendKey(Solo.MENU);
        sleepOneSecond();
        solo.clickOnText(tabActivity.getString(R.string.menu_info));
        final String needle = "Homepage";
        assertTrue("Needle '" + needle + "' not found!", solo.searchText(needle));
        solo.assertCurrentActivity("Expected info screen", InfoActivity.class);
    }

    @MediumTest
    public void testClickOnDateTab() {
        final ArrayList<View> touchables = tabActivity.getTabHost().getTouchables();
        Log.d(TAG, "touchables = " + touchables);
        sleepOneSecond();
        for (final View touchable : touchables) {
            Log.d(TAG, "touchable = " + touchable);
            solo.clickOnView(touchable);
            sleepOneSecond();
        }
    }

    /**
     * @throws InterruptedException
     */
    void sleepOneSecond() {
        sleepSeconds(1);
    }

    /**
     * @param i
     */
    void sleepSeconds(final int i) {
        try {
            Thread.sleep(TimeUnit.MILLISECONDS.convert(i, TimeUnit.SECONDS));
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new RuntimeException("Could not sleep!", e);
        }
    }
}
