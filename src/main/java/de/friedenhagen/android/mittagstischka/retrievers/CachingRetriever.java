/**
 * Copyright 2010 Mirko Friedenhagen 
 */

package de.friedenhagen.android.mittagstischka.retrievers;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.StreamCorruptedException;
import java.util.List;

import de.friedenhagen.android.mittagstischka.model.Eatery;

/**
 * @author mirko
 * 
 */
public class CachingRetriever implements Retriever {

    private static interface CacheAccess {

        Object readCachedObject(final String filename) throws ApiException, NoCacheEntry;

        void writeCachedObject(final String filename, final Object o) throws ApiException;
    }

    private static class StorageCacheAccess implements CacheAccess {
        
        /** Buffersize for reading from store. */
        private static final int BUFFER_SIZE = 8192 * 2;

        /** where to store the cache files. */
        private final File storageDirectory;

        public StorageCacheAccess(final File storageDirectory) {
            this.storageDirectory = storageDirectory;
        }

        public void writeCachedObject(final String filename, final Object o) throws ApiException {
            try {
                final ObjectOutputStream stream = new ObjectOutputStream(new BufferedOutputStream(new FileOutputStream(
                        new File(storageDirectory, filename)), BUFFER_SIZE));
                try {
                    stream.writeObject(o);
                } finally {
                    stream.close();
                }
            } catch (FileNotFoundException e) {
                throw new ApiException(TAG, e);
            } catch (IOException e) {
                throw new ApiException(TAG, e);
            }
        }

        public Object readCachedObject(final String filename) throws ApiException, NoCacheEntry {
            final Object o;
            try {
                final File storageFile = new File(storageDirectory, filename);
                final FileInputStream storageInputStream = new FileInputStream(
                        storageFile);
                final ObjectInputStream stream = new ObjectInputStream(new BufferedInputStream(storageInputStream, BUFFER_SIZE));
                try {
                    o = stream.readObject();
                } catch (ClassNotFoundException e) {
                    throw new ApiException(TAG, e);
                } finally {
                    stream.close();
                }
            } catch (FileNotFoundException e) {
                throw new NoCacheEntry();
            } catch (StreamCorruptedException e) {
                throw new NoCacheEntry();
            } catch (IOException e) {
                throw new NoCacheEntry();
            }
            return o;
        }
    }

    private static class NoCacheAccess implements CacheAccess {

        @Override
        public Object readCachedObject(String filename) throws ApiException, NoCacheEntry {
            throw new NoCacheEntry();
        }

        @Override
        public void writeCachedObject(String filename, Object o) throws ApiException {
            // Just do nothing.

        }

    }

    private static String TAG = CachingRetriever.class.getSimpleName();

    private final HttpRetriever httpRetriever;

    private final CacheAccess cacheAccess;

    public static CachingRetriever createWithStorageCache(final HttpRetriever httpRetriever, final File storageDirectory) {
        return new CachingRetriever(httpRetriever, storageDirectory);
    }

    public static CachingRetriever createWithoutCache(final HttpRetriever httpRetriever) {
        return new CachingRetriever(httpRetriever);
    }

    CachingRetriever(final HttpRetriever httpRetriever, final File storageDirectory) {
        this.httpRetriever = httpRetriever;
        cacheAccess = new StorageCacheAccess(storageDirectory);
    }

    CachingRetriever(final HttpRetriever httpRetriever) {
        this.httpRetriever = httpRetriever;
        cacheAccess = new NoCacheAccess();
    }

    /** {@inheritDoc} */
    @Override
    public List<Eatery> retrieveEateries() throws ApiException {
        final String filename = "index";
        try {
            final Object o = cacheAccess.readCachedObject(filename);
            @SuppressWarnings("unchecked")
            final List<Eatery> eateries = (List<Eatery>) o;
            return eateries;
        } catch (NoCacheEntry ignored) {
            final List<Eatery> eateries = httpRetriever.retrieveEateries();
            cacheAccess.writeCachedObject(filename, eateries);
            return eateries;
        }
    }

    /** {@inheritDoc} */
    @Override
    public String retrieveEateryContent(Integer id) throws ApiException {
        final String filename = String.valueOf(id);
        try {
            return (String) cacheAccess.readCachedObject(filename);
        } catch (NoCacheEntry ignored) {
            final String eateryContent = httpRetriever.retrieveEateryContent(id);
            cacheAccess.writeCachedObject(filename, eateryContent);
            return eateryContent;
        }
    }

    /** {@inheritDoc} */
    @Override
    public byte[] retrieveEateryPicture(Integer id) throws ApiException {
        final String filename = String.valueOf(id) + ".png";
        byte[] buffer;
        try {
            buffer = (byte[]) cacheAccess.readCachedObject(filename);
        } catch (NoCacheEntry ignored) {
            buffer = httpRetriever.retrieveEateryPicture(id);
            cacheAccess.writeCachedObject(filename, buffer);
        }
        return buffer;
    }
}
