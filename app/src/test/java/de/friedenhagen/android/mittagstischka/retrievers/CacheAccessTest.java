/**
 * Copyright 2010 Mirko Friedenhagen 
 */

package de.friedenhagen.android.mittagstischka.retrievers;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.io.Externalizable;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

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
        String[] files = tmpDir.list();
        for (final String file : files) {
            new File(file).delete();
        }
        tmpDir.delete();
    }

    @Test(expected = NoCacheEntry.class)
    public void testNoCacheAccessRead() throws ApiException, NoCacheEntry {
        final NoCacheAccess access = new CacheAccess.NoCacheAccess();
        access.readCachedObject("foo");
    }

    @Test
    public void testNoCacheAccessWrite() throws ApiException {
        final NoCacheAccess access = new CacheAccess.NoCacheAccess();
        access.writeCachedObject("foo", new Object());
    }

    @Test
    public void testCacheAccessSuccessfulRead() throws ApiException, NoCacheEntry {
        final Integer i5 = Integer.valueOf(5);
        access.writeCachedObject("foo", i5);
        Integer readCachedObject = (Integer) access.readCachedObject("foo");
        assertEquals(i5, readCachedObject);
    }

    @Test(expected = NoCacheEntry.class)
    public void testCacheAccessUnSuccessfulReadFileNotFound() throws ApiException, NoCacheEntry {
        access.readCachedObject("foo/bar");
    }

    @Test(expected = NoCacheEntry.class)
    public void testCacheAccessUnSuccessfulReadIOException() throws ApiException, NoCacheEntry {
        final Externalizable externalizable = new Externalizable() {
            @Override
            public void writeExternal(ObjectOutput out) throws IOException {
                out.writeUTF("foo");
                out.flush();
            }

            @Override
            public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
                // do nothing
            }
        };
        access.writeCachedObject("bar", externalizable);
        access.readCachedObject("bar");
    }

    @Test(expected = NoCacheEntry.class)
    public void testCacheAccessUnSuccessfulReadStreamCorrupted() throws ApiException, NoCacheEntry, IOException {
        final FileOutputStream outputStream = new FileOutputStream(new File(tmpDir, "bar"));
        try {
            outputStream.write("Hallo".getBytes());
        } finally {
            outputStream.close();
        }
        access.readCachedObject("bar");
    }

    @Test
    public void testCacheAccessUnSuccessfulWrite() throws NoCacheEntry {
        try {
            final Integer i5 = Integer.valueOf(5);
            access.writeCachedObject("foo/bar", i5);
            fail("Expected " + ApiException.class.getSimpleName());
        } catch (ApiException e) {
            assertEquals(FileNotFoundException.class, e.getCause().getClass());
        }
    }
}
