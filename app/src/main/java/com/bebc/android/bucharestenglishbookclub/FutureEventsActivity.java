/*
 * *
 *  * Created by Iulian Pralea on 7/19/19 3:46 PM
 *  * Copyright (c) 2019 . All rights reserved.
 *  * Last modified 7/10/19 2:16 PM
 *
 */

package com.bebc.android.bucharestenglishbookclub;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.view.View;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class FutureEventsActivity extends AppCompatActivity {

    private DatabaseReference databaseReference;
    private ListView eventsListView;
    private ProgressBar progressBar;
    private final static String LOG_TAG = FutureEventsActivity.class.getSimpleName();
    private EventAdapter adapter;
    private TextView emptyTextView;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.future_events_activity);

        //Find a reference to the ListView in the layout
        eventsListView = (ListView) findViewById(R.id.list);

        //Find a reference to the ProgressBar in the layout
        progressBar = findViewById(R.id.loading_spinner);

        //We find a reference to the EmptyView in the layout and set it on the ListView
        emptyTextView = findViewById(R.id.empty_view);
        eventsListView.setEmptyView(emptyTextView);

        //We get the reference for the database that we'll use futher on
        databaseReference = FirebaseDatabase.getInstance().getReference().child("events");

        //We create a new adapter with an empty list of events as input and set it on the ListView
        adapter = new EventAdapter(FutureEventsActivity.this, new ArrayList<Event>());
        eventsListView.setAdapter(adapter);

        //The following lines of code will help us establish if the user is connected to the Internet
        //We get a reference to the ConnectivityManager so we can check the state of network connectivity
        ConnectivityManager cm =
                (ConnectivityManager)getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        //We get details on the current state of network data
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        boolean isConnected = activeNetwork != null && activeNetwork.isConnectedOrConnecting();

        //If there isn't an Internet connection, then we display a message
        if (!isConnected) {
            //The progress bar should also be disabled
            progressBar.setVisibility(View.GONE);
            emptyTextView.setText("No Internet connection.");
        }
    }

    @Override
    protected void onStart() {

        super.onStart();
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                //We retrieve data only if there is any data in the database
                if (dataSnapshot.exists()) {

                    //Every time the data is change a new list of events is created
                    List<Event> futureEvents = new ArrayList<>();
                    //The adapter is cleared so there aren't any duplicate events
                    adapter.clear();

                    //We begin the process of retrieving data
                    for (DataSnapshot eventSnaphot : dataSnapshot.getChildren()) {

                        //We create a new event from the "events" JSON object that contains an array
                        Event eventBookClub = eventSnaphot.getValue(Event.class);

                        //We get the current UNIX time
                        long currentUnixTime = System.currentTimeMillis() / 1000L;

                        //That events prior to our date shouldn't be included in the future events list
                        if (currentUnixTime < eventBookClub.getTimestamp())
                            futureEvents.add(eventBookClub);
                    }

                    //If we the future events list is not null, we add them to adapter's data set
                    if (futureEvents != null && !futureEvents.isEmpty()) {
                        adapter.addAll(futureEvents); }
                    else
                        //If not, we set message to the emptyView
                        emptyTextView.setText(getResources().getString(R.string.no_future_events));

                    //Either way, the progressBar must be disable, so it won't rotate infinitely
                    progressBar.setVisibility(View.GONE);
                } else {
                    //If there isn't any data, the progress bar is disabled and a message is shown
                    progressBar.setVisibility(View.GONE);
                    emptyTextView.setText(getResources().getString(R.string.no_data_found));
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                progressBar.setVisibility(View.GONE);
            }
        });
    }

}
