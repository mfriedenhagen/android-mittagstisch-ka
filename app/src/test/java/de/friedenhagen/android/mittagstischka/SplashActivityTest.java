/**
 * Copyright 2010 Mirko Friedenhagen 
 */

package de.friedenhagen.android.mittagstischka;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.modules.junit4.PowerMockRunner;

import android.os.Bundle;

/**
 * @author mirko
 *
 */
@RunWith(PowerMockRunner.class)
public class SplashActivityTest {

    /**
     * Test method for {@link de.friedenhagen.android.mittagstischka.SplashActivity#onCreate(android.os.Bundle)}.
     */
    @Test
    @Ignore
    public void testOnCreateBundle() {
        final Bundle bundle = PowerMockito.mock(Bundle.class);
        
        new SplashActivity();
    }

}
