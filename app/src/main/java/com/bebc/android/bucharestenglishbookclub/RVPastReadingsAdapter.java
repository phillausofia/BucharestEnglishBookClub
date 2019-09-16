/*
 * *
 *  * Created by Iulian Pralea on 9/14/19 8:58 PM
 *  * Copyright (c) 2019 . All rights reserved.
 *  * Last modified 9/14/19 8:58 PM
 *
 */

package com.bebc.android.bucharestenglishbookclub;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class RVPastReadingsAdapter extends RecyclerView.Adapter<RVPastReadingsAdapter.ViewHolder>
implements Filterable {


    private List<PastReading> data;
    private List<PastReading> dataFiltered;
    private Context context;

    RVPastReadingsAdapter(Context context, List<PastReading> data) {
        this.data = data;
        this.dataFiltered = data;
        this.context = context;
    }

    @NonNull
    @Override
    public RVPastReadingsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_past_reading, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RVPastReadingsAdapter.ViewHolder holder, int position) {
        PastReading pastReading = dataFiltered.get(position);
        holder.initializeUIComponents(pastReading.getAuthor(), pastReading.getMeetingNumber(),
                pastReading.getTitle());

    }

    @Override
    public int getItemCount() {
        return dataFiltered.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private TextView textView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.pastReading);
        }

        public void initializeUIComponents(String author, String meetingNumber, String title) {
            Locale locale = Locale.getDefault();
            textView.setText(String.format(locale, "%s. %s - %s", meetingNumber, author, title));
        }


    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                String constraintString = constraint.toString();
                if (constraintString.isEmpty()) {
                    dataFiltered = data;
                } else {
                    List<PastReading> filteredList = new ArrayList<>();
                    for (PastReading current: data) {
                        if (current.getAuthor().toLowerCase().contains(constraintString) ||
                        current.getTitle().toLowerCase().contains(constraintString))
                            filteredList.add(current);
                    }
                    dataFiltered = filteredList;
                }
                FilterResults filterResults = new FilterResults();
                filterResults.values = dataFiltered;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                dataFiltered = (ArrayList<PastReading>) results.values;
                notifyDataSetChanged();
            }
        };
    }

}
