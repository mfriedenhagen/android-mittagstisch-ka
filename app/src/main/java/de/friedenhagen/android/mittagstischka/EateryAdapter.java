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

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;
import de.friedenhagen.android.mittagstischka.model.Eatery;

public class EateryAdapter extends BaseAdapter {

    public final List<Eatery> eateries;

    private static class ViewHolder {

        final TextView titleView;

        final TextView dateView;

        /**
         * @param titleView
         * @param dateView
         */
        private ViewHolder(TextView titleView, TextView dateView) {
            this.titleView = titleView;
            this.dateView = dateView;
        }
    }

    public EateryAdapter(final List<Eatery> list) {
        this.eateries = list;
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
        final LinearLayout listItemView;
        final ViewHolder holder;
        if (convertView == null) {
            final Context context = parent.getContext();
            listItemView = (LinearLayout) LayoutInflater.from(context).inflate(R.layout.eateries_list_item, parent,
                    false);
            final TextView titleView = (TextView) listItemView.findViewById(R.id.eateries_list_item_title);
            final TextView dateView = (TextView) listItemView.findViewById(R.id.eateries_list_item_date);
            holder = new ViewHolder(titleView, dateView);
            listItemView.setTag(holder);
        } else {
            listItemView = (LinearLayout) convertView;
            holder = (ViewHolder) listItemView.getTag();
        }
        final Eatery eatery = eateries.get(position);
        final SimpleDateFormat format = new SimpleDateFormat("dd.MM.yyyy");
        holder.titleView.setText(eatery.title);
        holder.dateView.setText(format.format(eatery.date));
        return listItemView;
    }
}
