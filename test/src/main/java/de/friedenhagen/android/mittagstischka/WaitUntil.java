/**
 * Copyright 2010 Mirko Friedenhagen 
 */

package de.friedenhagen.android.mittagstischka;

import junit.framework.Assert;

abstract class WaitUntil {

    private final long timeoutInMilliSeconds;

    private final String message;

    abstract boolean until();

    WaitUntil(final String message, final long timeoutInMilliSeconds) {
        this.message = message;
        this.timeoutInMilliSeconds = timeoutInMilliSeconds;
    }

    void waitUntil() {
        final long endtime = System.currentTimeMillis() + timeoutInMilliSeconds;
        while (!until() && System.currentTimeMillis() < endtime) {
            try {
                Thread.sleep(50);
            } catch (InterruptedException e) {
                throw new RuntimeException("Message:", e);
            }
        }
        Assert.assertTrue(message + " after " + timeoutInMilliSeconds + "ms.", until());
    }

}