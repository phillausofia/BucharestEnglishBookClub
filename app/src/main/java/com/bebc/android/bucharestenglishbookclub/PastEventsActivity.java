
/*
 * *
 *  * Created by Iulian Pralea on 8/27/19 10:49 AM
 *  * Copyright (c) 2019 . All rights reserved.
 *  * Last modified 8/27/19 10:49 AM
 *
 */

package com.bebc.android.bucharestenglishbookclub;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.bebc.android.bucharestenglishbookclub.EventAdapter;
import com.bebc.android.bucharestenglishbookclub.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class PastEventsActivity extends AppCompatActivity {

    private DatabaseReference databaseReference;
    private ListView eventsListView;
    private ProgressBar progressBar;
    private TextView emptyView;
    private EventAdapter adapter;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.future_events_activity);

        eventsListView = findViewById(R.id.list);
        progressBar = findViewById(R.id.loading_spinner);
        emptyView = findViewById(R.id.empty_view);

        eventsListView.setEmptyView(emptyView);

        databaseReference = FirebaseDatabase.getInstance().getReference().child("events");

        adapter = new EventAdapter(this, new ArrayList<Event>());
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
            emptyView.setText(getResources().getString(R.string.no_internet_connection));
        }

    }
    @Override
    public void onStart() {

        super.onStart();

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {

                    List<Event> pastEvents = new ArrayList<>();

                    adapter.clear();

                    for (DataSnapshot eventSnapshot: dataSnapshot.getChildren() ) {

                        Event eventBookClub = eventSnapshot.getValue(Event.class);

                        long currentUNIXTime = System.currentTimeMillis() / 1000L;

                        if (currentUNIXTime > eventBookClub.getTimestamp()) {
                            pastEvents.add(eventBookClub);
                        }
                    }
                    if (!pastEvents.isEmpty()) {
                        adapter.addAll(pastEvents);
                    } else {
                        emptyView.setText(getResources().getString(R.string.no_past_events));
                    }
                    progressBar.setVisibility(View.GONE);
                }
                else {
                    progressBar.setVisibility(View.GONE);
                    emptyView.setText(getResources().getString(R.string.no_data_found));
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                progressBar.setVisibility(View.GONE);
            }
        });
    }
}
