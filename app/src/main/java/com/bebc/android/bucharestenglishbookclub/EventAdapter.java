/*
 * *
 *  * Created by Iulian Pralea on 7/19/19 3:43 PM
 *  * Copyright (c) 2019 . All rights reserved.
 *  * Last modified 7/19/19 3:34 PM
 *
 */

package com.bebc.android.bucharestenglishbookclub;


import android.content.Intent;
import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import static androidx.core.content.ContextCompat.startActivity;

public class EventAdapter extends ArrayAdapter<Event> {

    public EventAdapter(Context context, List<Event> events) {
        super(context, 0, events);
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        View listItemView = convertView;
        if (listItemView == null)
            listItemView = LayoutInflater.from(getContext()).inflate(R.layout.event_item, parent, false);

        //We get the event at the current position in the list
        final Event currentEvent = getItem(position);

        //We find the TextView that corresponds to the author_and_title ID
        TextView authorAndTitleView = (TextView) listItemView.findViewById(R.id.author_and_title);
        //We extract the author and title of the short story for the current event and display them
        authorAndTitleView.setText(currentEvent.getAuthor() + " - " + currentEvent.getTitle());

        //We find the TextView with the id description and set on it the description text
        final TextView descriptionView = (TextView) listItemView.findViewById(R.id.description);
        descriptionView.setText(currentEvent.getDescription());

        //The description TextView will only show a summary
        descriptionView.setMaxLines(5);
        //And we create a boolean variable that tells us if the user sees only a summary or not
        final boolean[] shortDescription = {true};
        final TextView readMoreTextView = listItemView.findViewById(R.id.read_more);
        //The description TextView will expand or contract when the users presses Read More
        readMoreTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (shortDescription[0]) {
                    descriptionView.setMaxLines(Integer.MAX_VALUE);
                    shortDescription[0] = false;}
                else
                {
                    descriptionView.setMaxLines(5);
                    shortDescription[0] = true;
                }
            }
        });


        //We create a date object from the timestamp in seconds
        Date dateObject = new Date(currentEvent.getTimestamp() * 1000L);
        //We find the TextView that we'll hold the date and time in a user readable format
        TextView dateAndTimeView = listItemView.findViewById(R.id.date_and_time);
        //We'll assign the text to the view
        dateAndTimeView.setText(formatDate(dateObject) + ", " + formatTime(dateObject) + " at");

        //We find the TextView with the id location and assigned it a String value
        TextView locationTextView = listItemView.findViewById(R.id.location);
        locationTextView.setText(currentEvent.getLocation());

        //Now we can set a clickListener on the locationTextView
        locationTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //The intent will open a Google Maps page with the location's coordinates
                Uri googleMapsUri = Uri.parse("geo:0,0?q=" + currentEvent.getLocation());
                Intent googleMapsIntent = new Intent(Intent.ACTION_VIEW);
                googleMapsIntent.setData(googleMapsUri);
                googleMapsIntent.setPackage("com.google.android.apps.maps");

                //Checking if there is at least one app to handle the intent
                if (googleMapsIntent.resolveActivity(getContext().getPackageManager()) != null)
                    startActivity(getContext(), googleMapsIntent, null);

            }
        });

        //Next we'll find the ImageViews that correspond to a social media site
        final ImageView facebookImageView = listItemView.findViewById(R.id.facebook_event);
        ImageView meetupImageView = listItemView.findViewById(R.id.meetup_event);
        ImageView couchsurfingImageView = listItemView.findViewById(R.id.couchsurfing_event);

        //We'll set clickListener to each ImageView that will open a link with the event
        //First for the Facebook ImageView that will redirect to a Facebook event
        facebookImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //We'll create a new intent that will open a web page
                Intent facebookIntent = new Intent(Intent.ACTION_VIEW,
                        Uri.parse(currentEvent.getEventsSocialMedia().get(0)));
                if (facebookIntent.resolveActivity(getContext().getPackageManager()) != null)
                    startActivity(getContext(), facebookIntent, null);
            }
        });

        //For Meetup ImageView that will redirect to a Meetup event
        meetupImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //We'll create a new intent that will open a web page
                Intent meetupIntent = new Intent(Intent.ACTION_VIEW,
                        Uri.parse(currentEvent.getEventsSocialMedia().get(1)));
                if (meetupIntent.resolveActivity(getContext().getPackageManager()) != null)
                    startActivity(getContext(), meetupIntent, null);
            }
        });

        //For Couchsurfing ImageView that will redirect to a Couchsurfing event
        couchsurfingImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //We'll create a new intent that will open a web page
                Intent couchsurfingIntent = new Intent(Intent.ACTION_VIEW,
                        Uri.parse(currentEvent.getEventsSocialMedia().get(2)));
                if (couchsurfingIntent.resolveActivity(getContext().getPackageManager()) != null)
                    startActivity(getContext(), couchsurfingIntent, null);
            }
        });



        return listItemView;
    }

    /**
     * Return the date as a string (''February 24, 2019'') from a Date object
     */
    private String formatDate(Date date) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("E, d MMMM yyyy");
        return dateFormat.format(date);
    }

    /**
     * Returns time as a string ("19:00") from a Date object
     */
    private String formatTime(Date date) {

        SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm");
        return timeFormat.format(date);
    }
}
