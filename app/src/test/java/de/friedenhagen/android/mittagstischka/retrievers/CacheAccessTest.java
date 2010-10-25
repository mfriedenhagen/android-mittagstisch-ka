/**
 * Copyright 2010 Mirko Friedenhagen 
 */

package de.friedenhagen.android.mittagstischka.retrievers;

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.json.JSONException;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import de.friedenhagen.android.mittagstischka.model.Eatery;
import de.friedenhagen.android.mittagstischka.model.TUtils;
import de.friedenhagen.android.mittagstischka.retrievers.CacheAccess.NoCacheAccess;
import de.friedenhagen.android.mittagstischka.retrievers.CacheAccess.StorageCacheAccess;

/**
 * @author mirko
 * 
 */
public class CacheAccessTest {

    private File tmpDir;

    private StorageCacheAccess access;

    @Before
    public void prepareCacheDir() {
        tmpDir = new File("target/tmp");
        tmpDir.mkdirs();
        access = new CacheAccess.StorageCacheAccess(tmpDir);
    }

    @After
    public void deleteCacheDir() {
        String[] fileNames = tmpDir.list();
        for (final String fileName : fileNames) {
            final File file = new File(tmpDir, fileName);
            file.delete();
        }
        tmpDir.delete();
    }

    @Test(expected = NoCacheEntry.class)
    public void testNoCacheAccessRead() throws ApiException, NoCacheEntry {
        final NoCacheAccess access = new CacheAccess.NoCacheAccess();
        access.readCachedIndex();
    }

    @Test
    public void testNoCacheAccessWrite() throws ApiException {
        final NoCacheAccess access = new CacheAccess.NoCacheAccess();
        access.writeCachedIndex(null);
    }

    @Test
    public void testCacheAccessSuccessful() throws ApiException, NoCacheEntry, JSONException, IOException {
        final String jsonString = TUtils.getEateriesString();
        final List<Eatery> eateries = Eatery.fromJsonString(jsonString);
        access.writeCachedIndex(jsonString);
        final List<Eatery> eateries2 = access.readCachedIndex();
        assertEquals(eateries.size(), eateries2.size());
        final String string = "Hallo";
        access.writeCachedText("foo", string);
        assertEquals(string, access.readCachedText("foo"));
    }

    @Test(expected = NoCacheEntry.class)
    public void testCacheAccessUnSuccessfulIndex() throws ApiException, NoCacheEntry {
        access.readCachedIndex();
    }

    @Test(expected = NoCacheEntry.class)
    public void testCacheAccessUnSuccessfulText() throws ApiException, NoCacheEntry {
        access.readCachedText("foo");
    }
}
