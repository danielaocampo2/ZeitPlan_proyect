package com.example.zeitplan_proyect.model;

import android.content.Context;
import android.os.Build;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;

import com.example.zeitplan_proyect.DataBase.Firebase;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@RequiresApi(api = Build.VERSION_CODES.O)
public class Event extends EventoGeneral{
    Firebase bd = new Firebase();

    public static ArrayList<Event> eventsList = new ArrayList<>();

    public static ArrayList<Event> getEventsList(){
        return eventsList;
    }

    /*public static ArrayList<Event> getEventsListSorted(String orden) {
        ArrayList<Event> events = new ArrayList<>();
        switch (orden){
            case "Fecha":
                //events = Event.getEventsListByDate();
            case "Nombre":
                //events = Event.getEventsListByName();
            case "Prioridad":
                //events = Event.getEventsListByPriority();
            default:
               return eventsList;
        }
        return events;
    }*/

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
            int eventHourIn = event.getTimeIn().getHour();
            int eventHourFi = event.getTimeFi().minusMinutes(1).getHour();
            int cellHour = time.getHour();
            for(int eventHour = eventHourIn; eventHour <= eventHourFi; eventHour++){
                if(event.getDate().equals(date) && eventHour == cellHour)
                    events.add(event);
            }

        }
        return  events;
    }


    private LocalDate date;
    private LocalTime timeIn, timeFi;
    private String type;
    private int priority;
    private boolean remember;
    private String id;

    public Event(){
        super(" "," ", " ", " ");
        this.date = LocalDate.now();
        this.timeIn = LocalTime.now();
        this.timeFi = LocalTime.now().plusHours(1);
        this.type = " ";
        this.priority = 0;
        this.remember = false;
        this.id = " ";
    }
    public Event(String name, String description, LocalDate date, LocalTime time, String type, int priority, boolean remember, String id) {
        super(date.toString(), date.toString(), name, description);
        this.date = date;
        this.timeIn = time;
        this.timeFi = time.plusHours(1);
        this.type = type;
        this.priority = priority;
        this.remember = remember;
        this.id = id;
    }
    public Event(String name, String description, LocalDate date, LocalTime timeIn, LocalTime timeFi, String type, int priority, boolean remember) {
        super(date.toString(), date.toString(), name, description);
        this.date = date;
        this.timeIn = timeIn;
        this.timeFi = timeFi;
        this.type = type;
        this.priority = priority;
        this.remember = remember;
    }

    @Override
    public String getNombre() {
        return nombre;
    }

    @Override
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    @Override
    public String getDescripcion() {
        return descripcion;
    }

    @Override
    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    @Override
    public String getFecha_inicio() {
        return fecha_inicio;
    }

    @Override
    public void setFecha_inicio(String fecha_inicio) {
        this.fecha_inicio = fecha_inicio;
    }

    @Override
    public String getFecha_final() {
        return fecha_final;
    }

    @Override
    public void setFecha_final(String fecha_final) {
        this.fecha_final = fecha_final;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public LocalTime getTimeIn() {
        return timeIn;
    }

    public void setTimeIn(LocalTime timeIn) {
        this.timeIn = timeIn;
    }

    public LocalTime getTimeFi() {
        return timeFi;
    }

    public void setTimeFi(LocalTime timeFi) { this.timeIn = timeFi; }


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

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
