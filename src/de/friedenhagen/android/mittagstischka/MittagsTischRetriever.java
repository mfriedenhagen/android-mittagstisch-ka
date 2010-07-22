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

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.util.Log;

/**
 * @author mirko
 * 
 */
public class MittagsTischRetriever {

    public static final String MITTAGSTISCH_API = "http://mittagstisch-ka.de/app/";

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
    }

    private String retrieveString(HttpGet whatToGet) throws ApiException {
        try {
            return new String(retrieveBytes(whatToGet), "UTF-8");
        } catch (UnsupportedEncodingException e) {
            throw new ApiException("Conversion to UTF-8 failed!", e);
        }
    }

    /**
     * @param whatToGet
     * @return
     * @throws IOException
     * @throws ClientProtocolException
     * @throws ApiException
     */
    private byte[] retrieveBytes(HttpGet whatToGet) throws ApiException {
        try {
            final HttpResponse response;
            response = httpClient.execute(whatToGet);
            final StatusLine statusLine = response.getStatusLine();
            if (statusLine.getStatusCode() != HttpStatus.SC_OK) {
                throw new ApiException("Status-Line:" + statusLine);
            }
            Log.i("MittagsTischRetriever.retrieve()", String.valueOf(statusLine));
            final HttpEntity entity = response.getEntity();
            final byte[] byteArray = toByteArray(entity.getContent());
            return byteArray;
        } catch (ClientProtocolException e) {
            throw new ApiException("Message:", e);
        } catch (IOException e) {
            throw new ApiException("Message:", e);
        }
    }

    private static byte[] toByteArray(final InputStream inputStream) throws IOException {
        final byte[] sBuffer = new byte[1024];
        final ByteArrayOutputStream content = new ByteArrayOutputStream();
        // Read response into a buffered stream
        int readBytes = 0;
        while ((readBytes = inputStream.read(sBuffer)) != -1) {
            content.write(sBuffer, 0, readBytes);
        }
        // Return result from buffered stream
        return content.toByteArray();
    }

    public JSONArray retrieveEateries() throws ApiException {
        final String response = retrieveString(new HttpGet(MITTAGSTISCH_INDEX));
        try {
            return new JSONArray(response);
        } catch (JSONException e) {
            throw new ApiException("Could not parse " + response, e);
        }
    }

    public String retrieveEateryContent(Integer id) throws ApiException {
        return retrieveString(new HttpGet(MITTAGSTISCH_API + id));
    }

    public Bitmap retrieveEateryPicture(Integer id) throws ApiException {        
        final HttpGet imageGet = new HttpGet(MITTAGSTISCH_API + id + ".jpg");
        final byte[] bytes = retrieveBytes(imageGet);
        return BitmapFactory.decodeByteArray(bytes, 0, bytes.length);        
    }

}
