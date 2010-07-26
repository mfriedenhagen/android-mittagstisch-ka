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
final class Utils {

    private final static String TAG = Utils.class.getSimpleName();

    private Utils() {
    };

    final static File calculateStorageDirectory() {
        if (hasExternalStorage()) {
            final File storageDirectory = new File(Environment.getExternalStorageDirectory(), "Android/data/"
                    + Utils.class.getPackage().getName());
            storageDirectory.mkdirs();
            Log.i(TAG, "storageDirectory=" + storageDirectory);
            return storageDirectory;
        } else {
            throw new RuntimeException("No external storage");
        }
    }

    /**
     * @return
     */
    static boolean hasExternalStorage() {
        final boolean hasExternalStorage = Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED);
        Log.i(TAG, "hasExternalStorage=" + hasExternalStorage);
        return hasExternalStorage;
    }

    static Retriever getRetriever() {
        if (hasExternalStorage()) {
            return CachingRetriever.createWithStorageCache(new HttpRetriever(), calculateStorageDirectory());
        } else {
            return CachingRetriever.createWithoutCache(new HttpRetriever());
        }
    }
}
