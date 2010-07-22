/**
 * Copyright 2010 Mirko Friedenhagen 
 */

package de.friedenhagen.android.mittagstischka;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;

/**
 * @author mirko
 * 
 */
public class MittagsTischRetriever {
    private static final String MITTAGSTISCH_API = "http://mittagstisch-ka.de/app/";
    /**
     * Thrown when there were problems contacting the remote API server, either because of a network error, or the
     * server returned a bad status code.
     */
    public static class ApiException extends Exception {
        public ApiException(String detailMessage, Throwable throwable) {
            super(detailMessage, throwable);
        }

        public ApiException(String detailMessage) {
            super(detailMessage);
        }
    }

    /**
     * Thrown when there were problems parsing the response to an API call, either because the response was empty, or it
     * was malformed.
     */
    public static class ParseException extends Exception {
        public ParseException(String detailMessage, Throwable throwable) {
            super(detailMessage, throwable);
        }
    }

    public final static URI MITTAGSTISCH_INDEX;

    private final HttpClient httpClient;

    private final HttpGet indexGet;

    static {
        try {
            MITTAGSTISCH_INDEX = new URI(MITTAGSTISCH_API + "index");
        } catch (URISyntaxException e) {
            throw new RuntimeException("Message:", e);
        }
    }

    /**
     * 
     */
    public MittagsTischRetriever() {
        httpClient = new DefaultHttpClient();
        indexGet = new HttpGet(MITTAGSTISCH_INDEX);
    }

    private String retrieve(HttpGet whatToGet) throws ApiException {
        final HttpResponse response;
        try {
            response = httpClient.execute(whatToGet);
            final StatusLine statusLine = response.getStatusLine();
            if (statusLine.getStatusCode() != HttpStatus.SC_OK) {
                throw new ApiException("Status-Line:" + statusLine);
            }
            Log.i("MittagsTischRetriever.retrieve()", String.valueOf(statusLine));
            final HttpEntity entity = response.getEntity();
            final InputStream inputStream = entity.getContent();
            return toString(inputStream);
        } catch (ClientProtocolException e) {
            throw new ApiException("Message:", e);
        } catch (IOException e) {
            throw new ApiException("Message:", e);
        }
    }

    private static String toString(final InputStream inputStream) throws IOException {
        final byte[] sBuffer = new byte[1024];
        final ByteArrayOutputStream content = new ByteArrayOutputStream();
        // Read response into a buffered stream
        int readBytes = 0;
        while ((readBytes = inputStream.read(sBuffer)) != -1) {
            content.write(sBuffer, 0, readBytes);
        }
        // Return result from buffered stream
        return new String(content.toByteArray(), "UTF-8");
    }
    
    public JSONArray retrieveEateries() throws ApiException {
        final String response = retrieve(indexGet);
        try {
            return new JSONArray(response);
        } catch (JSONException e) {
            throw new ApiException("Could not parse " + response, e);
        }
    }
}
