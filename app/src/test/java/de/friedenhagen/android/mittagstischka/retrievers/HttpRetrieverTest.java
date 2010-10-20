/**
 * Copyright 2010 Mirko Friedenhagen 
 */

package de.friedenhagen.android.mittagstischka.retrievers;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

import java.io.IOException;

import org.apache.http.Header;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.HttpVersion;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.message.BasicHeader;
import org.apache.http.message.BasicStatusLine;
import org.json.JSONException;
import org.junit.Test;
import org.mockito.Mockito;

import de.friedenhagen.android.mittagstischka.model.Eateries;
import de.friedenhagen.android.mittagstischka.model.TUtils;

/**
 * @author mirko
 *
 */
public class HttpRetrieverTest {

    /**
     * 
     */
    private static final String STRING_RESPONSE = "Hallo";
    private final HttpClient httpClient = Mockito.mock(HttpClient.class);
    private final HttpRetriever retriever = new HttpRetriever(httpClient, "http://foo/");
    private final HttpGet request = new HttpGet("/bar");

    /**
     * Test method for {@link de.friedenhagen.android.mittagstischka.retrievers.HttpRetriever#retrieveBytes(org.apache.http.client.methods.HttpGet)}.
     * @throws IOException 
     * @throws ClientProtocolException 
     * @throws ApiException 
     */
    @Test
    public void testRetrieveBytes() throws ClientProtocolException, IOException, ApiException {
        final HttpResponse responseMock = createResponse(STRING_RESPONSE);
        Mockito.when(httpClient.execute(Mockito.any(HttpGet.class))).thenReturn(responseMock);
        assertEquals(STRING_RESPONSE, new String(retriever.retrieveBytes(request)));
    }

    /**
     * Test method for {@link de.friedenhagen.android.mittagstischka.retrievers.HttpRetriever#getEtag()}.
     * @throws IOException 
     * @throws ClientProtocolException 
     * @throws ApiException 
     */
    @Test
    public void testGetEtag() throws ClientProtocolException, IOException, ApiException {
        final HttpResponse responseMock = createResponse(STRING_RESPONSE);
        Mockito.when(httpClient.execute(Mockito.any(HttpGet.class))).thenReturn(responseMock);
        retriever.retrieveBytes(request);
        assertEquals("123", retriever.getEtag());
    }

    /**
     * Test method for {@link de.friedenhagen.android.mittagstischka.retrievers.HttpRetriever#retrieveEateries()}.
     * @throws IOException 
     * @throws ClientProtocolException 
     * @throws ApiException 
     * @throws JSONException 
     */
    @Test
    public void testRetrieveEateries() throws ClientProtocolException, IOException, ApiException, JSONException {
        final HttpResponse responseMock = createResponse(TUtils.getEateriesString());        
        Mockito.when(httpClient.execute(Mockito.any(HttpGet.class))).thenReturn(responseMock);
        final Eateries eateries = retriever.retrieveEateries();
        assertEquals(TUtils.getEateriesFromJson().size(), eateries.size());
    }

    /**
     * Test method for {@link de.friedenhagen.android.mittagstischka.retrievers.HttpRetriever#retrieveEateryContent(java.lang.Integer)}.
     * @throws IOException 
     * @throws ApiException 
     */
    @Test
    public void testRetrieveEateryContent() throws IOException, ApiException {
        final HttpResponse responseMock = createResponse(STRING_RESPONSE);        
        Mockito.when(httpClient.execute(Mockito.any(HttpGet.class))).thenReturn(responseMock);
        assertEquals(STRING_RESPONSE, retriever.retrieveEateryContent(5));
    }

    /**
     * Test method for {@link de.friedenhagen.android.mittagstischka.retrievers.HttpRetriever#retrieveEateryPicture(java.lang.Integer)}.
     * @throws IOException 
     * @throws ClientProtocolException 
     * @throws ApiException 
     */
    @Test
    public void testRetrieveEateryPicture() throws ClientProtocolException, IOException, ApiException {
        final HttpResponse responseMock = createResponse(STRING_RESPONSE);        
        Mockito.when(httpClient.execute(Mockito.any(HttpGet.class))).thenReturn(responseMock);
        assertArrayEquals(STRING_RESPONSE.getBytes(), retriever.retrieveEateryPicture(5));        
    }
    /**
     * @param stringResponse
     * @return
     */
    private HttpResponse createResponse(final String stringResponse) {
        final HttpResponse responseMock = Mockito.mock(HttpResponse.class);
        Mockito.when(responseMock.getHeaders("ETag")).thenReturn(new Header[]{new BasicHeader("ETag", "123")});
        Mockito.when(responseMock.getStatusLine()).thenReturn(new BasicStatusLine(HttpVersion.HTTP_1_1, HttpStatus.SC_OK, "All good"));        
        Mockito.when(responseMock.getEntity()).thenReturn(new ByteArrayEntity(stringResponse.getBytes()));
        return responseMock;
    }


}
