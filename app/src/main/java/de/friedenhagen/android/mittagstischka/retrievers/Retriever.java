/*
 * MittagsTischRetriever.java 23.07.2010
 * 
 * Copyright (c) 2010 1&1 Internet AG. All rights reserved.
 * 
 * $Id$
 */
package de.friedenhagen.android.mittagstischka.retrievers;

import de.friedenhagen.android.mittagstischka.model.Eateries;

public interface Retriever {

    /** {@inheritDoc} */
    public abstract Eateries retrieveEateries() throws ApiException;

    /** {@inheritDoc} */
    public abstract String retrieveEateryContent(Integer id) throws ApiException;

    /** {@inheritDoc} */
    public abstract byte[] retrieveEateryPicture(Integer id) throws ApiException;

}
