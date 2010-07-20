/**
 * Copyright 2010 Mirko Friedenhagen 
 */

package de.friedenhagen.android.mittagstischka;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
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

    public final static URI MITTAGSTISCH_URL;

    private final HttpClient httpClient;

    private final HttpGet get;

    static {
        try {
            MITTAGSTISCH_URL = new URI("http://mittagstisch-ka.de/app/index");
        } catch (URISyntaxException e) {
            throw new RuntimeException("Message:", e);
        }
    }

    /**
     * 
     */
    public MittagsTischRetriever() {
        httpClient = new DefaultHttpClient();
        get = new HttpGet(MITTAGSTISCH_URL);
    }

    public String retrieve() throws ApiException {
        final HttpResponse response;
        try {
            response = httpClient.execute(get);
            final StatusLine statusLine = response.getStatusLine();
            if (statusLine.getStatusCode() != HttpStatus.SC_OK) {
                throw new ApiException("Status-Line:" + statusLine);
            }
            Log.i("MittagsTischRetriever.retrieve()", String.valueOf(statusLine));
            final HttpEntity entity = response.getEntity();
            final byte[] sBuffer = new byte[512];
            final InputStream inputStream = entity.getContent();
            final ByteArrayOutputStream content = new ByteArrayOutputStream();
            // Read response into a buffered stream
            int readBytes = 0;
            while ((readBytes = inputStream.read(sBuffer)) != -1) {
                content.write(sBuffer, 0, readBytes);
            }
            // Return result from buffered stream
            return new String(content.toByteArray(), "UTF-8");
        } catch (ClientProtocolException e) {
            throw new ApiException("Message:", e);
        } catch (IOException e) {
            throw new ApiException("Message:", e);
        }
    }
    public JSONArray retrieveLocals() throws ApiException {
        final String response = retrieve();
        try {
            return new JSONArray(response);
        } catch (JSONException e) {
            // TODO Auto-generated catch block
            throw new ApiException("Could not parse " + response, e);
        }
    }
}
