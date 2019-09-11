/*
 * *
 *  * Created by Iulian Pralea on 9/10/19 9:27 PM
 *  * Copyright (c) 2019 . All rights reserved.
 *  * Last modified 9/10/19 9:27 PM
 *
 */

package com.bebc.android.bucharestenglishbookclub;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

public class SupportActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_support);
        AdView myAdView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        myAdView.loadAd(adRequest);

    }
}
