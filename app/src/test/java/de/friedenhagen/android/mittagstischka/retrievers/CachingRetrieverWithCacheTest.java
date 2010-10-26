/**
 * Copyright 2010 Mirko Friedenhagen 
 */

package de.friedenhagen.android.mittagstischka.retrievers;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URI;

import org.json.JSONException;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import de.friedenhagen.android.mittagstischka.model.TUtils;

/**
 * @author mirko
 * 
 */
public class CachingRetrieverWithCacheTest {

    private final HttpRetriever httpRetrieverMock = Mockito.mock(HttpRetriever.class);

    private final File storageDirectory = new File("target/cache");

    private final CachingRetriever cachingRetriever = CachingRetriever.createWithStorageCache(httpRetrieverMock,
            storageDirectory);;

    @Before
    public void createCacheDirectory() {
        assertTrue(storageDirectory + " could not be created.", storageDirectory.mkdir());
    }

    @After
    public void deleteCacheEntries() {
        final String[] fileNames = storageDirectory.list();
        for (final String fileName : fileNames) {
            final File file = new File(storageDirectory, fileName);
            assertTrue(file + " could not be deleted", file.delete());
        }
        assertTrue(storageDirectory + " could not be deleted.", storageDirectory.delete());
    }

    /**
     * Test method for {@link de.friedenhagen.android.mittagstischka.retrievers.CachingRetriever#retrieveEateries()}.
     * 
     * @throws IOException
     * @throws JSONException
     * @throws ApiException
     */
    @Test
    public void testRetrieveEateries() throws JSONException, IOException, ApiException {
        Mockito.when(httpRetrieverMock.retrieveEateriesJson()).thenReturn(TUtils.getEateriesString());
        cachingRetriever.retrieveEateries();
    }

    /**
     * Test method for {@link de.friedenhagen.android.mittagstischka.retrievers.CachingRetriever#retrieveEateries()}.
     * 
     * @throws IOException
     * @throws JSONException
     * @throws ApiException
     */
    @Test(expected = ApiException.class)
    public void testRetrieveEateriesApiException() throws JSONException, IOException, ApiException {
        Mockito.when(httpRetrieverMock.retrieveEateriesJson()).thenThrow(new ApiException("Jepp"));
        cachingRetriever.retrieveEateries();
    }

    /**
     * Test method for
     * {@link de.friedenhagen.android.mittagstischka.retrievers.CachingRetriever#retrieveEateryContent(java.lang.Integer)}
     * .
     * 
     * @throws ApiException
     */
    @Test
    public void testRetrieveEateryContent() throws ApiException {
        final String content = "HullaHop";
        Mockito.when(httpRetrieverMock.retrieveEateryContent(5)).thenReturn(content);
        assertEquals(content, cachingRetriever.retrieveEateryContent(5));
    }

    /**
     * Test method for
     * {@link de.friedenhagen.android.mittagstischka.retrievers.CachingRetriever#retrieveEateryContent(java.lang.Integer)}
     * .
     * 
     * @throws ApiException
     */
    @Test(expected = ApiException.class)
    public void testRetrieveEateryContentApiException() throws ApiException {
        Mockito.when(httpRetrieverMock.retrieveEateryContent(5)).thenThrow(new ApiException("Jepp"));
        cachingRetriever.retrieveEateryContent(5);
    }

    /**
     * Test method for
     * {@link de.friedenhagen.android.mittagstischka.retrievers.CachingRetriever#retrieveEateryPicture(java.lang.Integer)}
     * .
     * 
     * @throws ApiException
     * @throws UnsupportedEncodingException
     */
    @Test
    public void testRetrieveEateryPicture() throws ApiException, UnsupportedEncodingException {
        final byte[] content = "HullaHop".getBytes("utf-8");
        Mockito.when(httpRetrieverMock.retrieveEateryPicture(5)).thenReturn(content);
        final URI remoteUri = URI.create("http://www.example.com/5.png");
        Mockito.when(httpRetrieverMock.retrieveEateryPictureUri(5)).thenReturn(remoteUri);
        assertEquals(remoteUri, cachingRetriever.retrieveEateryPictureUri(5));
        // This returns the localUrl on consecutive calls (cmp. to CachingRetriever without cache)
        assertEquals(new File(storageDirectory, "5.png").toURI(), cachingRetriever.retrieveEateryPictureUri(5));
    }

    /**
     * Test method for
     * {@link de.friedenhagen.android.mittagstischka.retrievers.CachingRetriever#retrieveEateryPicture(java.lang.Integer)}
     * .
     * 
     * @throws ApiException
     */
    @Test(expected=ApiException.class)
    public void testRetrieveEateryPictureApiException() throws ApiException {
        Mockito.when(httpRetrieverMock.retrieveEateryPicture(5)).thenThrow(new ApiException("Jepp"));
        cachingRetriever.retrieveEateryPictureUri(5);
    }
}
