/**
 * Copyright 2010 Mirko Friedenhagen 
 */

package de.friedenhagen.android.mittagstischka;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

/**
 * @author mirko
 * 
 */
public class MittagsTischRetriever {

    public final static URI MITTAGSTISCH_URL;

    static {
        try {
            MITTAGSTISCH_URL = new URI("http://mittagstisch-ka.de/app/index");
        } catch (URISyntaxException e) {
            throw new RuntimeException("Message:", e);
        }
    }

    public static void main(String[] args) throws ClientProtocolException, IOException {
        final HttpClient httpClient = new DefaultHttpClient();
        final HttpGet get = new HttpGet(MITTAGSTISCH_URL);
        try {
            final HttpResponse response = httpClient.execute(get);
            final StatusLine statusLine = response.getStatusLine();
            System.out.println("MittagsTischRetriever.main()" + statusLine);
        } finally {

        }
    }

}
