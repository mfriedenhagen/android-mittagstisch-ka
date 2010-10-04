/**
 * Copyright 2010 Mirko Friedenhagen 
 */

package de.friedenhagen.android.mittagstischka;

import android.test.ActivityInstrumentationTestCase2;
import android.test.suitebuilder.annotation.MediumTest;
import android.util.Log;

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
    }

    /** {@inheritDoc} */
    @Override
    protected void tearDown() throws Exception {
        try {
            solo.finalize();
        } catch (Throwable e) {
            throw new RuntimeException("Message:", e);
        }
        tabActivity.finish();
        super.tearDown();
    }

    private void triggerInfoDialog() {
        solo.sendKey(Solo.MENU);
        solo.clickOnText(tabActivity.getString(R.string.menu_info));
        final String needle = "Homepage";
        assertTrue("Needle '" + needle + "' not found!", solo.searchText(needle));
        solo.assertCurrentActivity("Expected info screen", InfoActivity.class);
    }

    @MediumTest
    public void testClickOnTabs() {
        final String title = tabActivity.getString(R.string.app_name);
        assertEquals(title, tabActivity.getTitle());
        clickOnTab(R.string.tab_alphabetically_title);
        clickOnTab(R.string.tab_bydate_title);
        triggerInfoDialog();
    }

    /**
     * @param titleId
     */
    void clickOnTab(final int titleId) {
        final String tabTitle = tabActivity.getString(titleId);
        Log.d(TAG, "clickOnTab()" + tabTitle);
        solo.clickOnText(tabTitle);
        final MittagstischApplication application = (MittagstischApplication) tabActivity.getApplication();
        new WaitUntil("no eateries", 30000) {
            @Override
            boolean until() {
                return application.hasEateries();
            }
        }.waitUntil();
    }
}
