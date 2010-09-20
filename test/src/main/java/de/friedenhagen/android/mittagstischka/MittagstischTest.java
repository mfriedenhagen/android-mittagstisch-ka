/**
 * Copyright 2010 Mirko Friedenhagen 
 */

package de.friedenhagen.android.mittagstischka;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import com.jayway.android.robotium.solo.Solo;

import android.test.ActivityInstrumentationTestCase2;
import android.test.suitebuilder.annotation.MediumTest;
import android.util.Log;
import android.view.View;
import de.friedenhagen.android.mittagstischka.EateriesTabActivity;
import de.friedenhagen.android.mittagstischka.R;

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
    public void testClickOnDateTab() throws InterruptedException {
        final ArrayList<View> touchables = tabActivity.getTabHost().getTouchables();
        Log.d(TAG, "touchables = " + touchables);
        Thread.sleep(TimeUnit.MILLISECONDS.convert(1, TimeUnit.SECONDS));
        for (final View touchable : touchables) {
            Log.d(TAG, "touchable = " + touchable);
            solo.clickOnView(touchable);
            Thread.sleep(TimeUnit.MILLISECONDS.convert(1, TimeUnit.SECONDS));
        }
    }
}
