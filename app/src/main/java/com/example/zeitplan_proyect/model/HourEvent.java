package com.example.zeitplan_proyect.model;

import android.os.Build;

import androidx.annotation.RequiresApi;

import com.example.zeitplan_proyect.model.Event;

import java.time.LocalTime;
import java.util.ArrayList;
@RequiresApi(api = Build.VERSION_CODES.O)
public class HourEvent {
    LocalTime time;
    ArrayList<Event> events;

    public HourEvent(LocalTime time, ArrayList<Event> events) {
        this.time = time;
        this.events = eventsOfTime(events);
    }


    private ArrayList<Event> eventsOfTime(ArrayList<Event> events) {
        int hour = time.getHour();
        for(Event event : events)
        {
            int eventHourIn = event.getTiempoIniLT().getHour();
            int eventHourFi = event.getTiempoFiLT().minusMinutes(1).getHour();
            /*if (!(eventHourIn <= hour && hour <= eventHourFi))*/ events.remove(event);
        }
        return events;
    }


    public LocalTime getTime() {
        return time;
    }

    public void setTime(LocalTime time) {
        this.time = time;
    }

    public ArrayList<Event> getEvents() {
        return events;
    }

    public void setEvents(ArrayList<Event> events) {
        this.events = events;
    }
}
