/**
 * Copyright 2010 Mirko Friedenhagen 
 */

package de.friedenhagen.android.mittagstischka.test;

import android.test.ActivityInstrumentationTestCase2;
import de.friedenhagen.android.mittagstischka.EateriesTabActivity;
import de.friedenhagen.android.mittagstischka.R;

/**
 * @author mirko
 *
 */
public class MittagstischTest extends ActivityInstrumentationTestCase2<EateriesTabActivity> {

    private EateriesTabActivity tabActivity;

    /**
     * @param pkg
     * @param activityClass
     */
    public MittagstischTest(String pkg, Class<EateriesTabActivity> activityClass) {
        super(activityClass.getPackage().getName(), activityClass);
    }

    /** {@inheritDoc} */
    @Override
    protected void setUp() throws Exception {
        super.setUp();
        tabActivity = this.getActivity();
    }
    
    public void testPreconditions() {
        assertNotNull(tabActivity);
    }
    
    public void testTitle() {
        final String title = tabActivity.getString(R.string.app_name);
        assertEquals(title, tabActivity.getTitle());
    }

}
