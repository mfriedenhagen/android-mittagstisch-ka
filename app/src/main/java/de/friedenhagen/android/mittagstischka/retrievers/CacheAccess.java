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
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;

import de.friedenhagen.android.mittagstischka.Constants;
import de.friedenhagen.android.mittagstischka.model.Eatery;

interface CacheAccess {

    /**
     * This implementation will read and write serialized objects to a file in the given storageDirectory.
     */
    static class StorageCacheAccess implements CacheAccess {

        private final static String TAG = Constants.LOG_PREFIX + StorageCacheAccess.class.getSimpleName();

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
        @Override
        public void writeCachedIndex(final String json) throws ApiException {
            writeCachedText("index", json);
        }

        /** {@inheritDoc} */
        @Override
        public List<Eatery> readCachedIndex() throws ApiException, NoCacheEntry {
            try {
                return Eatery.fromJsonArray(new JSONArray(readCachedText("index")));
            } catch (JSONException e) {
                throw new ApiException("Message:", e);
            }
        }

        /**
         * @param child
         * @return
         */
        private File getStorageFile(final String child) {
            return new File(storageDirectory, child);
        }

        /** {@inheritDoc} */
        @Override
        public String readCachedText(String fileName) throws ApiException, NoCacheEntry {
            return IOUtils.toUtf8String(readCachedBytes(fileName));
        }

        /** {@inheritDoc} */
        @Override
        public byte[] readCachedBytes(String fileName) throws ApiException, NoCacheEntry {
            try {
                BufferedInputStream stream = new BufferedInputStream(new FileInputStream(getStorageFile(fileName)),
                        BUFFER_SIZE);
                try {
                    return IOUtils.toByteArray(stream);
                } finally {
                    stream.close();
                }
            } catch (FileNotFoundException e) {
                throw new NoCacheEntry();
            } catch (IOException e) {
                throw new ApiException(TAG, e);
            }
        }

        /** {@inheritDoc} */
        @Override
        public void writeCachedText(String filename, String o) throws ApiException {
            writeCachedBytes(filename, IOUtils.toUtf8Bytes(o));
        }

        /** {@inheritDoc} */
        @Override
        public void writeCachedBytes(String filename, byte[] o) throws ApiException {
            try {
                final BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(
                        getStorageFile(filename)), BUFFER_SIZE);
                try {
                    IOUtils.write(o, stream);
                } finally {
                    stream.close();
                }
            } catch (FileNotFoundException e) {
                throw new ApiException(TAG, e);
            } catch (IOException e) {
                throw new ApiException(TAG, e);
            }
        }
    }

    /**
     * Dummy implementation which will always throw a {@link NoCacheEntry} exception on read and does nothing on write.
     */
    static class NoCacheAccess implements CacheAccess {

        /** {@inheritDoc} */
        @Override
        public List<Eatery> readCachedIndex() throws ApiException, NoCacheEntry {
            throw new NoCacheEntry();
        }

        /** {@inheritDoc} */
        @Override
        public String readCachedText(String fileName) throws ApiException, NoCacheEntry {
            throw new NoCacheEntry();
        }

        /** {@inheritDoc} */
        @Override
        public byte[] readCachedBytes(String fileName) throws ApiException, NoCacheEntry {
            throw new NoCacheEntry();
        }

        /** {@inheritDoc} */
        @Override
        public void writeCachedIndex(final String json) throws ApiException {
            // Do nothing
        }

        /** {@inheritDoc} */
        @Override
        public void writeCachedText(String filename, String o) throws ApiException {
            // Do nothing
        }

        /** {@inheritDoc} */
        @Override
        public void writeCachedBytes(String filename, byte[] o) throws ApiException {
            // Do nothing
        }

    }

    /**
     * Retrieves the index from the cache throwing {@link NoCacheEntry} when this is not possible.
     * 
     * @return cached object
     * @throws ApiException
     *             when other errors occur.
     * @throws NoCacheEntry
     *             when there is no cache entry.
     */
    List<Eatery> readCachedIndex() throws ApiException, NoCacheEntry;

    /**
     * Retrieves the text from the cache throwing {@link NoCacheEntry} when this is not possible.
     * 
     * @param fileName
     *            name of the text file
     * @return cached object
     * @throws ApiException
     *             when other errors occur.
     * @throws NoCacheEntry
     *             when there is no cache entry.
     */
    String readCachedText(final String fileName) throws ApiException, NoCacheEntry;

    /**
     * Retrieves the text from the cache throwing {@link NoCacheEntry} when this is not possible.
     * 
     * @param fileName
     *            name of the image file
     * @return cached object
     * @throws ApiException
     *             when other errors occur.
     * @throws NoCacheEntry
     *             when there is no cache entry.
     */
    byte[] readCachedBytes(final String fileName) throws ApiException, NoCacheEntry;

    /**
     * Writes the index to a cache file.
     * 
     * @param o
     *            Eateries to cache.
     * @throws ApiException
     *             when other errors occur.
     */
    void writeCachedIndex(final String json) throws ApiException;

    /**
     * Writes the index to a cache file.
     * 
     * @param filename
     *            of the cachefile
     * @param o
     *            object to cache.
     * @throws ApiException
     *             when other errors occur.
     */
    void writeCachedText(final String filename, final String o) throws ApiException;

    /**
     * Writes the index to a cache file.
     * 
     * @param filename
     *            of the cachefile
     * @param o
     *            object to cache.
     * @throws ApiException
     *             when other errors occur.
     */
    void writeCachedBytes(final String filename, final byte[] o) throws ApiException;

}
