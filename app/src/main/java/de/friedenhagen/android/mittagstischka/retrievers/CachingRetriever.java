/**
 * Copyright 2010 Mirko Friedenhagen 
 */

package de.friedenhagen.android.mittagstischka.retrievers;

import java.io.File;
import java.net.URI;
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
        try {
            return cacheAccess.readCachedIndex();
        } catch (NoCacheEntry ignored) {
            final String eateriesJson = httpRetriever.retrieveEateriesJson();
            cacheAccess.writeCachedIndex(eateriesJson);
            return Eatery.fromJsonString(eateriesJson);
        }
    }

    /** {@inheritDoc} */
    @Override
    public String retrieveEateryContent(Integer id) throws ApiException {
        final String filename = String.valueOf(id);
        try {
            return cacheAccess.readCachedText(filename);
        } catch (NoCacheEntry ignored) {
            final String eateryContent = httpRetriever.retrieveEateryContent(id);
            cacheAccess.writeCachedText(filename, eateryContent);
            return eateryContent;
        }
    }

    /** {@inheritDoc} */
    @Override
    public URI retrieveEateryPictureUri(Integer id) throws ApiException {
        final String filename = String.valueOf(id) + ".png";
        try {
            return cacheAccess.getCacheFile(filename).toURI();
        } catch (NoCacheEntry ignored) {
            final byte[] buffer = httpRetriever.retrieveEateryPicture(id);
            cacheAccess.writeCachedBytes(filename, buffer);
            return httpRetriever.retrieveEateryPictureUri(id);
        }
    }
}
