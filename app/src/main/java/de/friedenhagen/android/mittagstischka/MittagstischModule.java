/**
 * Copyright 2010 Mirko Friedenhagen 
 */

package de.friedenhagen.android.mittagstischka;

import java.io.File;

import org.apache.http.conn.scheme.PlainSocketFactory;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.tsccm.ThreadSafeClientConnManager;
import org.apache.http.params.BasicHttpParams;

import roboguice.config.AbstractAndroidModule;
import android.os.Environment;
import android.util.Log;

import com.google.inject.Provides;
import com.google.inject.name.Named;

import de.friedenhagen.android.mittagstischka.retrievers.CachingRetriever;
import de.friedenhagen.android.mittagstischka.retrievers.HttpRetriever;
import de.friedenhagen.android.mittagstischka.retrievers.Retriever;

/**
 * @author mirko
 * 
 */
public class MittagstischModule extends AbstractAndroidModule {

    private final static String TAG = Constants.LOG_PREFIX + MittagstischModule.class.getSimpleName();

    /** {@inheritDoc} */
    @Override
    protected void configure() {

    }

    @Provides
    Retriever createRetriever() {
        if (hasExternalStorage()) {
            return CachingRetriever.createWithStorageCache(createHttpRetriever(), calculateStorageDirectory());
        } else {
            return CachingRetriever.createWithoutCache(createHttpRetriever());
        }
    }

    /**
     * @return
     */
    @Provides
    public HttpRetriever createHttpRetriever() {
        final BasicHttpParams params = new BasicHttpParams();
        final SchemeRegistry schreg = new SchemeRegistry();
        schreg.register(new Scheme("http", PlainSocketFactory.getSocketFactory(), 80));
        final ThreadSafeClientConnManager connManager = new ThreadSafeClientConnManager(params, schreg);
        return new HttpRetriever(new DefaultHttpClient(connManager, params), Constants.API_LOCATION);
    }

    @Provides
    @Named("storageDirectory")
    public File calculateStorageDirectory() {
        if (hasExternalStorage()) {
            final File storageDirectory = new File(Environment.getExternalStorageDirectory(), Constants.STORAGE_PATH);
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
    private boolean hasExternalStorage() {
        final boolean hasExternalStorage = Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED);
        Log.i(TAG, "hasExternalStorage=" + hasExternalStorage);
        return hasExternalStorage;
    }

}
