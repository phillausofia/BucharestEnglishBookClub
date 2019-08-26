/*
 * *
 *  * Created by Iulian Pralea on 7/19/19 3:44 PM
 *  * Copyright (c) 2019 . All rights reserved.
 *  * Last modified 7/18/19 5:24 PM
 *
 */

package com.bebc.android.bucharestenglishbookclub;

import java.util.List;

public class Event {
    /**
     * This is the class that defines the structure of a book club event_item
     */

    private String author;
    private String description;
    private int id;
    private long timestamp;
    private String title;
    private String location;
    private List<String> eventsSocialMedia;


    /**
     * The constructor of the class
     * @param author is the name of the author who's short story we'll discuss
     * @param description is the description of the event_item
     * @param id is the code of the event_item
     * @param timestamp is the date and time of the event_item in UNIX time, UTC timezone;
     * @param title is the title of the short story that we'll discuss (or some other
     *                        title is we'll discuss more than one short story in a single meeting)
     * @param location this String will hold the name and address where the event is going to be
     * @param eventsSocialMedia the list holds the links as Strings to the events on social media
     *
     */
    public Event(String author, String description, int id, long timestamp, String title,
                 String location, List<String> eventsSocialMedia) {
        this.author = author;
        this.description = description;
        this.id = id;
        this.timestamp = timestamp;
        this.title = title;
        this.location = location;
        this.eventsSocialMedia = eventsSocialMedia;
    }

    public Event() { }

    /** Returns the author of the short story */
    public String getAuthor() { return author;}

    /** Returns the description of the event */
    public String getDescription() { return description;}

    /** Returns the ID of the event */
    public int getId() { return id;}

    /** Returns the time of the event in UNIX time */
    public long getTimestamp() { return timestamp;}

    /** Returns the title of the short story that we'll discuss at the event */
    public String getTitle() { return title;}

    /** Returns the name and address of the location */
    public String getLocation() { return location;}


    /** Returns a list with the links to the events on social media */
    public List<String> getEventsSocialMedia() {return eventsSocialMedia;}

    /** Helps us set a value to the author variable */
    public void setAuthor(String author) { this.author = author;}

    /** Helps us set a value to the description variable */
    public void setDescription(String description) { this.description = description;}

    /** Helps us set a value to the id variable */
    public void setId(int id) { this.id = id;}

    /** Helps us set a value to the timestamp variable */
    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    /** This method will set a value to the location variable equal with that of the parameter. */
    public void setLocation(String location) { this.location = location;}


    /** The method set a List<String> value to the variable eventsSocialMedia */
    public void setEventsSocialMedia(List<String> eventsSocialMedia) {
        this.eventsSocialMedia = eventsSocialMedia;
    }
}
