/*
 * *
 *  * Created by Iulian Pralea on 9/14/19 8:51 PM
 *  * Copyright (c) 2019 . All rights reserved.
 *  * Last modified 9/14/19 8:51 PM
 *
 */

package com.bebc.android.bucharestenglishbookclub;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.SearchView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class PastReadingsActivity extends AppCompatActivity {

    private ProgressBar progressBar;
    private TextView emptyView;
    private RVPastReadingsAdapter adapter;
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_past_readings);

        progressBar = findViewById(R.id.rv_loading_spinner);
        emptyView = findViewById(R.id.rv_empty_view);

        recyclerView = findViewById(R.id.my_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));


        ConnectivityManager cm = (ConnectivityManager)getApplicationContext().
                getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = cm.getActiveNetworkInfo();
        boolean isConnected = networkInfo != null && networkInfo.isConnected();
        if (!isConnected) {
            progressBar.setVisibility(View.GONE);
            emptyView.setText(getResources().getString(R.string.no_internet_connection));
        }


    }

    @Override
    public void onStart() {

        super.onStart();

        final List<PastReading> pastReadings = new ArrayList<>();
        final List<Event> pastEvents = new ArrayList<>();

        DatabaseReference pastEventsRef = FirebaseDatabase.getInstance().getReference().
                child("events");

        pastEventsRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot eventSnapShot: dataSnapshot.getChildren()) {
                    pastEvents.add(eventSnapShot.getValue(Event.class));
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        DatabaseReference pastReadingsRef = FirebaseDatabase.getInstance().
                getReference().child("pastReadings");

        pastReadingsRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    for (DataSnapshot pastReadingSnapshot: dataSnapshot.getChildren()) {
                        PastReading data = pastReadingSnapshot.getValue(PastReading.class);
                        pastReadings.add(data);
                    }
                    for(Event pastEvent: pastEvents) {
                        pastReadings.add(new PastReading(pastEvent.getAuthor(),
                                pastEvent.getId() + 142 + "", pastEvent.getTitle()));
                    }
                    adapter = new RVPastReadingsAdapter(PastReadingsActivity.this, pastReadings);
                    recyclerView.setAdapter(adapter);
                    progressBar.setVisibility(View.GONE);

                }
                else {
                    progressBar.setVisibility(View.GONE);
                    emptyView.setText(getResources().getString(R.string.no_past_readings));
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                progressBar.setVisibility(View.GONE);
                emptyView.setText(databaseError.getMessage());
            }
        });


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_past_readings_menu, menu);
        final SearchView searchView = (SearchView) menu.findItem(R.id.searchPastReadings).getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                adapter.getFilter().filter(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                adapter.getFilter().filter(newText);
                return false;
            }
        });
        return true;
    }

}
