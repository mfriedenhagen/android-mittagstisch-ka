/**
 * Copyright 2010 Mirko Friedenhagen 
 */

package de.friedenhagen.android.mittagstischka.retrievers;

import java.io.File;
import java.util.List;

import com.google.inject.Inject;
import com.google.inject.name.Named;

import de.friedenhagen.android.mittagstischka.model.Eatery;

/**
 * Depending on the availability of a SD-card this decorator will write a cache to the SD-card.
 * 
 * @author mirko
 * 
 */
public class CachingRetriever implements Retriever {

    private final HttpRetriever httpRetriever;

    private final CacheAccess cacheAccess;

    public static CachingRetriever createWithStorageCache(final HttpRetriever httpRetriever, final File storageDirectory) {
        return new CachingRetriever(httpRetriever, storageDirectory);
    }

    public static CachingRetriever createWithoutCache(final HttpRetriever httpRetriever) {
        return new CachingRetriever(httpRetriever);
    }

    @Inject
    CachingRetriever(final HttpRetriever httpRetriever, @Named("storageDirectory") final File storageDirectory) {
        this.httpRetriever = httpRetriever;
        cacheAccess = new CacheAccess.StorageCacheAccess(storageDirectory);
    }
    
    @Inject
    CachingRetriever(final HttpRetriever httpRetriever) {
        this.httpRetriever = httpRetriever;
        cacheAccess = new CacheAccess.NoCacheAccess();
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
