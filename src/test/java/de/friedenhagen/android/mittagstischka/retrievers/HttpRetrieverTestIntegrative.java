/**
 * Copyright 2010 Mirko Friedenhagen 
 */

package de.friedenhagen.android.mittagstischka.retrievers;

import static org.junit.Assert.assertTrue;

import java.util.Collections;
import java.util.List;

import org.apache.http.impl.client.DefaultHttpClient;
import org.junit.Before;
import org.junit.Test;

import de.friedenhagen.android.mittagstischka.model.Eatery;
import de.friedenhagen.android.mittagstischka.model.EateryTitleComparator;

/**
 * @author mirko
 * 
 */
public class HttpRetrieverTestIntegrative {

    private HttpRetriever retriever;

    @Before
    public void createRealHttpClient() {
        retriever = new HttpRetriever(new DefaultHttpClient());
    }

    /**
     * Test method for {@link de.friedenhagen.android.mittagstischka.retrievers.HttpRetriever#getEtag()}.
     * 
     * @throws ApiException
     */
    @Test
    public void testGetEtag() throws ApiException {
        testRetrieveEateries();
        final String etag = retriever.getEtag();
        assertTrue("Emtpy Etag?", etag.length() > 0);
    }

    /**
     * Test method for {@link de.friedenhagen.android.mittagstischka.retrievers.HttpRetriever#retrieveEateries()}.
     * 
     * @throws ApiException
     */
    @Test
    public void testRetrieveEateries() throws ApiException {
        final List<Eatery> eateries = retriever.retrieveEateries();
        assertTrue("Got no eateries?" + eateries, eateries.size() > 0);
    }

    /**
     * Test method for
     * {@link de.friedenhagen.android.mittagstischka.retrievers.HttpRetriever#retrieveEateryContent(java.lang.Integer)}.
     * 
     * @throws ApiException
     */
    @Test
    public void testRetrieveEateryContent() throws ApiException {
        final Integer id = getFirstId();
        final String content = retriever.retrieveEateryContent(id);
        assertTrue("No content for " + HttpRetriever.MITTAGSTISCH_API + id, content.length() > 0);
    }

    /**
     * Test method for
     * {@link de.friedenhagen.android.mittagstischka.retrievers.HttpRetriever#retrieveEateryPicture(java.lang.Integer)}.
     * 
     * @throws ApiException
     */
    @Test
    public void testRetrieveEateryPicture() throws ApiException {
        final Integer id = getFirstId();
        final byte[] pictureBytes = retriever.retrieveEateryPicture(id);
        assertTrue("No picture at " + HttpRetriever.MITTAGSTISCH_API + id + ".jpg", pictureBytes.length > 0);
    }

    /**
     * @return
     * @throws ApiException
     */
    Integer getFirstId() throws ApiException {
        final List<Eatery> eateries = retriever.retrieveEateries();
        Collections.sort(eateries, EateryTitleComparator.INSTANCE);
        final Integer id = eateries.get(0).id;
        return id;
    }

}
