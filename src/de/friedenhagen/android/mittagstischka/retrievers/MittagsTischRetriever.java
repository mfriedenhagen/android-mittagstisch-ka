/*
 * MittagsTischRetriever.java 23.07.2010
 * 
 * Copyright (c) 2010 1&1 Internet AG. All rights reserved.
 * 
 * $Id$
 */
package de.friedenhagen.android.mittagstischka.retrievers;

import org.json.JSONArray;

import android.graphics.Bitmap;
import de.friedenhagen.android.mittagstischka.retrievers.MittagsTischHttpRetriever.ApiException;

public interface MittagsTischRetriever {

    /** {@inheritDoc} */
    public abstract JSONArray retrieveEateries() throws ApiException;

    /** {@inheritDoc} */
    public abstract String retrieveEateryContent(Integer id) throws ApiException;

    /** {@inheritDoc} */
    public abstract Bitmap retrieveEateryPicture(Integer id) throws ApiException;

}
