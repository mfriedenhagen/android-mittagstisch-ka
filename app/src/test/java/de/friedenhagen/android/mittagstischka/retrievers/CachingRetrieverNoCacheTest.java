/**
 * Copyright 2010 Mirko Friedenhagen 
 */

package de.friedenhagen.android.mittagstischka.retrievers;

import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.util.List;

import org.json.JSONException;
import org.junit.Test;
import org.mockito.Mockito;

import de.friedenhagen.android.mittagstischka.model.Eatery;
import de.friedenhagen.android.mittagstischka.model.TUtils;


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
        final String eateriesString = TUtils.getEateriesString();
        final List<Eatery> eateriesFromJson = TUtils.getEateriesFromJson();
        Mockito.when(httpRetrieverMock.retrieveEateriesJson()).thenReturn(eateriesString);
        final List<Eatery> eateries = cachingRetriever.retrieveEateries();
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
        // This returns the remoteUri on consecutive calls (cmp. to CachingRetriever)
        assertEquals(remoteUri, cachingRetriever.retrieveEateryPictureUri(5));
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
