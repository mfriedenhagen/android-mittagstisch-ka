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

import de.friedenhagen.android.mittagstischka.Constants;

interface CacheAccess {

    /**
     * This implementation will read and write serialized objects to a file in the given storageDirectory.
     */
    static class StorageCacheAccess implements CacheAccess {

        private final String TAG = Constants.LOG_PREFIX + StorageCacheAccess.class.getSimpleName();
        
        /** Buffersize for reading from store. */
        private static final int BUFFER_SIZE = 8192 * 2;

        /** where to store the cache files. */
        private final File storageDirectory;

        /**
         * @param storageDirectory
         *            where to store the cache files.
         */
        public StorageCacheAccess(final File storageDirectory) {
            this.storageDirectory = storageDirectory;
        }

        /** {@inheritDoc} */
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
                final FileInputStream storageInputStream = new FileInputStream(storageFile);
                final ObjectInputStream stream = new ObjectInputStream(new BufferedInputStream(storageInputStream,
                        BUFFER_SIZE));
                try {
                    o = stream.readObject();
                } catch (ClassNotFoundException e) {
                    throw new ApiException(TAG + ":" + storageFile, e);
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

    /**
     * Dummy implementation which will always throw a {@link NoCacheEntry} exception on read and does nothing on write.
     */
    static class NoCacheAccess implements CacheAccess {

        /** {@inheritDoc} */
        @Override
        public Object readCachedObject(String filename) throws ApiException, NoCacheEntry {
            throw new NoCacheEntry();
        }

        /** {@inheritDoc} */
        @Override
        public void writeCachedObject(String filename, Object o) throws ApiException {
            // Just do nothing.
        }

    }

    /**
     * Retrieves an Object from the cache throwing {@link NoCacheEntry} when this is not possible.
     * 
     * @param filename
     *            of the cachefile
     * @return cached object
     * @throws ApiException
     *             when other errors occur.
     * @throws NoCacheEntry
     *             when there is no cache entry.
     */
    Object readCachedObject(final String filename) throws ApiException, NoCacheEntry;

    /**
     * Writes the object to a cache file.
     * 
     * @param filename
     *            of the cachefile
     * @param o
     *            object to cache.
     * @throws ApiException
     *             when other errors occur.
     */
    void writeCachedObject(final String filename, final Object o) throws ApiException;

}
