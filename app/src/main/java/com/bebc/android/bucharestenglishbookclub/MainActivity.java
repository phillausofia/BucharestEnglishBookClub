/*
 * *
 *  * Created by Iulian Pralea on 7/19/19 3:46 PM
 *  * Copyright (c) 2019 . All rights reserved.
 *  * Last modified 7/8/19 2:13 PM
 *
 */

package com.bebc.android.bucharestenglishbookclub;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //We set the content of the activity to use the activity_main.xml layout file
        setContentView(R.layout.activity_main);

        //Setting up the AdView
        String admobAppId = "ca-app-pub-3940256099942544~3347511713";
        MobileAds.initialize(this, admobAppId);



         //We'll find all the TextView and set a click listener to each of them


        //Here we find the TextView with about ID and set a click listener on it
        TextView aboutView = (TextView) findViewById(R.id.about);
        aboutView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent aboutIntent = new Intent(MainActivity.this, AboutActivity.class);
                startActivity(aboutIntent);
            }
        });

        //We find the TextView that will get us to the future events list with a click listener
        TextView futureEvents = (TextView) findViewById(R.id.future_events);
        futureEvents.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent futureEventsIntent = new Intent(MainActivity.this, FutureEventsActivity.class);
                startActivity(futureEventsIntent);
            }
        });

        findViewById(R.id.past_events).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, PastEventsActivity.class));
            }
        });

        //We find the TextView with the id past_events and set a click listener on it
        TextView pastEventsView = findViewById(R.id.past_readings);
        //The click listener will take us to a Google Docs
        pastEventsView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(MainActivity.this, PastReadingsActivity.class));

            }
        });

        //Find the TextView with id propose_short_story and set a click listener on it
        TextView proposeShortStoryView = findViewById(R.id.propose);
        proposeShortStoryView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent proposeShortStoryIntent = new Intent(MainActivity.this, ProposeActivity.class);
                startActivity(proposeShortStoryIntent);
            }
        });

        findViewById(R.id.support).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, SupportActivity.class));
            }
        });
    }
}
