/**
 * Copyright 2010 Mirko Friedenhagen 
 */

package de.friedenhagen.android.mittagstischka;

/**
 * @author mirko
 */
public final class Constants {

    /**
     * Prefix to be used in Log messages.
     */
    public static final String LOG_PREFIX = "dfamka.";

    /**
     * Location of the REST-API.
     */
    public static final String API_LOCATION = "http://www.mittagstisch-ka.de/app/";

    /**
     * Where on the SDCARD to store the Data
     */
    public static final String STORAGE_PATH = "Android/data/" + Constants.class.getPackage().getName();

    /**
     * Do not instantiate.
     */
    private Constants() {
    }

    /**
     * Returns a Tag including {@link Constants#LOG_PREFIX} and the simple name of klass.
     * 
     * @param klass
     *            the class to return the tag for.
     * @return tag
     */
    public static String getTag(Class<?> klass) {
        return LOG_PREFIX + klass.getSimpleName();
    }
}
