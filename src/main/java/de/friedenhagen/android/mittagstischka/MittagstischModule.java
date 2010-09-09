/**
 * Copyright 2010 Mirko Friedenhagen 
 */

package de.friedenhagen.android.mittagstischka;

import com.google.inject.Provides;

import de.friedenhagen.android.mittagstischka.retrievers.CachingRetriever;
import de.friedenhagen.android.mittagstischka.retrievers.HttpRetriever;
import de.friedenhagen.android.mittagstischka.retrievers.Retriever;
import roboguice.config.AbstractAndroidModule;

/**
 * @author mirko
 * 
 */
public class MittagstischModule extends AbstractAndroidModule {

    /** {@inheritDoc} */
    @Override
    protected void configure() {
    }

    @Provides
    Retriever createRetriever() {
        if (Utils.hasExternalStorage()) {
            return CachingRetriever.createWithStorageCache(createHttpRetriever(), Utils.calculateStorageDirectory());
        } else {
            return CachingRetriever.createWithoutCache(createHttpRetriever());
        }
    }

    /**
     * @return
     */
    @Provides
    HttpRetriever createHttpRetriever() {
        return new HttpRetriever();
    }
    
    
    
}
