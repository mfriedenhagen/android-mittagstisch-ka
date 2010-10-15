/**
 * Copyright 2010 Mirko Friedenhagen 
 */

package de.friedenhagen.android.mittagstischka.model;

import java.util.List;

import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

/**
 * @author mirko
 * 
 */
@Root
public class Eateries {

    @ElementList(name = "list", inline = true)
    private final List<Eatery> list;

    /**
     * @param list
     */
    public Eateries(@ElementList(name = "list", inline = true) final List<Eatery> list) {
        this.list = list;
    }

    public Eatery get(int index) {
        return list.get(index);
    }

    public int size() {
        return list.size();
    }

}
