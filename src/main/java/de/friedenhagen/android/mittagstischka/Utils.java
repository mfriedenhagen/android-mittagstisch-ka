/**
 * Copyright 2010 Mirko Friedenhagen 
 */

package de.friedenhagen.android.mittagstischka;

import java.io.File;

import android.os.Environment;
import android.util.Log;
import de.friedenhagen.android.mittagstischka.retrievers.CachingRetriever;
import de.friedenhagen.android.mittagstischka.retrievers.HttpRetriever;
import de.friedenhagen.android.mittagstischka.retrievers.Retriever;

/**
 * @author mirko
 * 
 */
public final class Utils {

    private final static String TAG = Utils.class.getSimpleName();

    private Utils() {
    };

    public final static File calculateStorageDirectory() {
        if (hasExternalStorage()) {
            return new File(Environment.getExternalStorageDirectory(), "Android/data/"
                    + CachingRetriever.class.getName());
        } else {
            throw new RuntimeException("No external storage");
        }
    }

    /**
     * @return
     */
    public static boolean hasExternalStorage() {
        final boolean hasExternalStorage = Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED);
        Log.i(TAG, "hasExternalStorage=" + hasExternalStorage);
        return hasExternalStorage;
    }

    static Retriever getRetriever() {
        if (hasExternalStorage()) {
            return new CachingRetriever(new HttpRetriever(), calculateStorageDirectory());
        } else {
            return new CachingRetriever(new HttpRetriever());
        }
    }
}
