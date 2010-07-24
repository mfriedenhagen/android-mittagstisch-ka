/*
 * EateryAdapter.java 22.07.2010
 * 
 * Copyright (c) 2010 1&1 Internet AG. All rights reserved.
 * 
 * $Id$
 */
package de.friedenhagen.android.mittagstischka;

import java.text.SimpleDateFormat;
import java.util.List;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import de.friedenhagen.android.mittagstischka.model.Eatery;

public class EateryAdapter extends BaseAdapter {

    public final List<Eatery> eateries;
    
    public EateryAdapter(final List<Eatery> eateries) {
        this.eateries = eateries;
    }
    @Override
    public int getCount() {
        return eateries.size();
    }

    @Override
    public Object getItem(int position) {
        return eateries.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = new TextView(parent.getContext());                      
        }
        final Eatery eatery = eateries.get(position);
        final SimpleDateFormat format = new SimpleDateFormat("dd.MM.yyyy");
        ((TextView)convertView).setText(eatery.title + "\n" + format.format(eatery.date));
        return convertView;
    }
}
