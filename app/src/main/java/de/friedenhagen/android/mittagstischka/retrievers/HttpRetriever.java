/**
 * Copyright 2010 Mirko Friedenhagen 
 */

package de.friedenhagen.android.mittagstischka.retrievers;

import java.io.IOException;
import java.util.List;

import org.apache.http.Header;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;

import de.friedenhagen.android.mittagstischka.Constants;
import de.friedenhagen.android.mittagstischka.model.Eatery;

/**
 * @author mirko
 * 
 */
public class HttpRetriever implements Retriever {

    private final static String TAG = Constants.LOG_PREFIX + HttpRetriever.class.getSimpleName();

    public String etag;

    private final HttpClient httpClient;

    private final String apiLocation;

    public HttpRetriever(final HttpClient httpClient, final String apiLocation) {
        this.httpClient = httpClient;
        this.apiLocation = apiLocation;
    }

    private String retrieveString(HttpGet whatToGet) throws ApiException {
        return IOUtils.toUtf8String(retrieveBytes(whatToGet));
    }

    /**
     * @param whatToGet
     * @return
     * @throws IOException
     * @throws ClientProtocolException
     * @throws ApiException
     */
    byte[] retrieveBytes(HttpGet whatToGet) throws ApiException {
        try {
            final HttpResponse response;
            response = httpClient.execute(whatToGet);
            final StatusLine statusLine = response.getStatusLine();
            final Header[] etags = response.getHeaders("ETag");
            etag = etags[0].getValue();
            if (statusLine.getStatusCode() != HttpStatus.SC_OK) {
                throw new ApiException("Status-Line:" + statusLine);
            }
            final byte[] byteArray = EntityUtils.toByteArray(response.getEntity());
            return byteArray;
        } catch (ClientProtocolException e) {
            throw new ApiException(TAG, e);
        } catch (IOException e) {
            throw new ApiException(TAG, e);
        }
    }

    /**
     * @return the etag
     */
    public String getEtag() {
        return etag;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Eatery> retrieveEateries() throws ApiException {
        final String response = retrieveString(new HttpGet(apiLocation + "index"));
        return Eatery.fromJsonArray(retrieveEateries(response));
    }

    /** {@inheritDoc} */
    JSONArray retrieveEateries(final String response) throws ApiException {
        try {
            return new JSONArray(response);
        } catch (JSONException e) {
            throw new ApiException(TAG + "Could not parse " + response, e);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String retrieveEateryContent(Integer id) throws ApiException {
        return retrieveString(new HttpGet(apiLocation + id));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public byte[] retrieveEateryPicture(Integer id) throws ApiException {
        final HttpGet imageGet = new HttpGet(apiLocation + id + ".jpg");
        return retrieveBytes(imageGet);
    }

}
