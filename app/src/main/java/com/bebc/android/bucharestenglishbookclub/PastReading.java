/*
 * *
 *  * Created by Iulian Pralea on 9/14/19 9:05 PM
 *  * Copyright (c) 2019 . All rights reserved.
 *  * Last modified 9/14/19 9:05 PM
 *
 */

package com.bebc.android.bucharestenglishbookclub;

public class PastReading {

    private String author;
    private String meetingNumber;
    private String title;

    public PastReading(String author, String meetingNumber, String title) {
        this.author = author;
        this.meetingNumber = meetingNumber;
        this.title = title;
    }

    public PastReading() {}

    public String getAuthor() { return author; }

    public void setAuthor(String author) { this.author = author;}

    public String getMeetingNumber() { return meetingNumber;}

    public void setMeetingNumber(String meetingNumber) {this.meetingNumber = meetingNumber;}

    public String getTitle() {return title;}

    public void setTitle(String title) {this.title = title;}

}

