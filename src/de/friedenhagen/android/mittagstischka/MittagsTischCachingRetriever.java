/**
 * Copyright 2010 Mirko Friedenhagen 
 */

package de.friedenhagen.android.mittagstischka;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;

import org.json.JSONArray;

import android.app.Application;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.util.Log;
import de.friedenhagen.android.mittagstischka.MittagsTischHttpRetriever.ApiException;

/**
 * @author mirko
 * 
 */
public class MittagsTischCachingRetriever implements MittagsTischRetriever {

    private static String TAG = MittagsTischCachingRetriever.class.getSimpleName();

    public static class NoCacheEntry extends Exception {
    }

    private final MittagsTischHttpRetriever httpRetriever;

    private final boolean hasExternalStorage;

    private final File storageDirectory;

    public MittagsTischCachingRetriever() {
        httpRetriever = new MittagsTischHttpRetriever();
        hasExternalStorage = Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED);
        Log.i(TAG, "hasExternalStorage=" + hasExternalStorage);
        if (hasExternalStorage) {
            storageDirectory = new File(Environment.getExternalStorageDirectory(),
                    MittagsTischCachingRetriever.class.getName());
            storageDirectory.mkdir();
        } else {
            storageDirectory = null;
        }
    }

    /** {@inheritDoc} */
    @Override
    public JSONArray retrieveEateries() throws ApiException {
        final String filename = "index.txt";
        try {
            final String response = IOUtils.toUtf8String(readFromCache(filename));
            Log.i(TAG, "Read " + response.length() + " bytes from " + filename);
            return httpRetriever.retrieveEateries(response);
        } catch (NoCacheEntry e) {
            final JSONArray eateries = httpRetriever.retrieveEateries();
            final byte[] bytes = IOUtils.toUtf8Bytes(eateries.toString());
            writeToCache(filename, bytes);
            Log.i(TAG, "Wrote " + bytes.length + " bytes to " + filename);
            return eateries;
        }
    }

    /**
     * @param filename
     * @param bytes
     * @throws ApiException
     */
    private void writeToCache(final String filename, final byte[] bytes) throws ApiException {
        if (hasExternalStorage) {
            final File cacheFile = new File(storageDirectory, filename);
            Log.d(TAG, "writeToCache: " + cacheFile);
            try {
                final FileOutputStream stream = new FileOutputStream(cacheFile);
                try {

                    IOUtils.toOutputStream(stream, bytes);
                } finally {
                    stream.close();
                }
            } catch (FileNotFoundException e1) {
                throw new ApiException("Message:", e1);
            } catch (IOException e1) {
                throw new ApiException("Message:", e1);
            }
        }
    }

    /**
     * @param filename
     * @return
     * @throws ApiException
     */
    private byte[] readFromCache(final String filename) throws ApiException, NoCacheEntry {
        if (hasExternalStorage) {
            final File cacheFile = new File(storageDirectory, filename);
            if (cacheFile.exists()) {
                Log.d(TAG, "readFromCache: " + cacheFile);
                try {
                    final FileInputStream stream = new FileInputStream(cacheFile);
                    try {
                        return IOUtils.toByteArray(stream);
                    } finally {
                        stream.close();
                    }
                } catch (FileNotFoundException e) {
                    throw new ApiException("Message:", e);
                } catch (IOException e) {
                    throw new ApiException("Message:", e);
                }
            } else {
                throw new NoCacheEntry();
            }
        } else {
            throw new NoCacheEntry();
        }
    }

    /** {@inheritDoc} */
    @Override
    public String retrieveEateryContent(Integer id) throws ApiException {
        final String filename = id + ".txt";
        try {
            return IOUtils.toUtf8String(readFromCache(filename));
        } catch (NoCacheEntry e) {
            final String eateryContent = httpRetriever.retrieveEateryContent(id);
            writeToCache(filename, IOUtils.toUtf8Bytes(eateryContent));
            return eateryContent;
        }
    }

    /** {@inheritDoc} */
    @Override
    public Bitmap retrieveEateryPicture(Integer id) throws ApiException {
        final String filename = id + ".png";
        byte[] bytes;
        try {
            bytes = readFromCache(filename);            
        } catch (NoCacheEntry e) {
            bytes = httpRetriever.retrieveEateryPictureBytes(id);
            writeToCache(filename, bytes);
        }
        return BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
        
    }

}
