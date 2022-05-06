package com.example.zeitplan_proyect;

import android.os.Build;

import androidx.annotation.RequiresApi;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;

@RequiresApi(api = Build.VERSION_CODES.O)
public class Event {

    public static ArrayList<Event> eventsList = new ArrayList<>();

    public static ArrayList<Event> eventsForDate(LocalDate date){

        ArrayList<Event> events= new ArrayList<>();
        for(Event event : eventsList)
        {
            if(event.getDate().equals(date))
                events.add(event);
        }
        return  events;
    }

    public static ArrayList<Event> eventsForDateAndTime(LocalDate date, LocalTime time){

        ArrayList<Event> events= new ArrayList<>();
        for(Event event : eventsList)
        {
            int eventHour = event.time.getHour();
            int cellHour = time.getHour();
            if(event.getDate().equals(date) && eventHour == cellHour)
                events.add(event);
        }
        return  events;
    }


    private String name;
    private String description;
    private LocalDate date;
    private LocalTime time;
    private String type;
    private int priority;
    private boolean remember;

    public Event(String name, String description, LocalDate date, LocalTime time, int priority, boolean remember) {
        this.name = name;
        this.description = description;
        this.date = date;
        this.time = time;
        this.priority = priority;
        this.remember = remember;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public LocalTime getTime() {
        return time;
    }

    public void setTime(LocalTime time) {
        this.time = time;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public boolean isRemember() {
        return remember;
    }

    public void setRemember(boolean remember) {
        this.remember = remember;
    }












}
