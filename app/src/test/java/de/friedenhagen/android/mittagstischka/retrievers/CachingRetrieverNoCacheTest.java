/**
 * Copyright 2010 Mirko Friedenhagen 
 */

package de.friedenhagen.android.mittagstischka.retrievers;

import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

import org.json.JSONException;
import org.junit.Test;
import org.mockito.Mockito;

import de.friedenhagen.android.mittagstischka.model.Eateries;


/**
 * @author mirko
 *
 */
public class CachingRetrieverNoCacheTest {

    private final HttpRetriever httpRetrieverMock = Mockito.mock(HttpRetriever.class);
    private final CachingRetriever cachingRetriever = CachingRetriever.createWithoutCache(httpRetrieverMock);;

    /**
     * Test method for {@link de.friedenhagen.android.mittagstischka.retrievers.CachingRetriever#retrieveEateries()}.
     * @throws IOException 
     * @throws JSONException 
     * @throws ApiException 
     */
    @Test
    public void testRetrieveEateries() throws JSONException, IOException, ApiException {        
        final Eateries eateriesFromJson = TUtils.getEateriesFromJson();
        Mockito.when(httpRetrieverMock.retrieveEateries()).thenReturn(eateriesFromJson);
        final Eateries eateries = cachingRetriever.retrieveEateries();
        assertEquals(eateriesFromJson.size(), eateries.size());
    }

    /**
     * Test method for {@link de.friedenhagen.android.mittagstischka.retrievers.CachingRetriever#retrieveEateries()}.
     * @throws IOException 
     * @throws JSONException 
     * @throws ApiException 
     */
    @Test(expected=ApiException.class)
    public void testRetrieveEateriesApiException() throws JSONException, IOException, ApiException {
        Mockito.when(httpRetrieverMock.retrieveEateries()).thenThrow(new ApiException("Jepp"));
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
        assertEquals(content, cachingRetriever.retrieveEateryPicture(5));
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
        cachingRetriever.retrieveEateryPicture(5);
    }
}
