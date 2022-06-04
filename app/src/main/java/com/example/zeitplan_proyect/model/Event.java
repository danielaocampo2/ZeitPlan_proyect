package com.example.zeitplan_proyect.model;

import android.os.Build;

import androidx.annotation.RequiresApi;

import com.example.zeitplan_proyect.DataBase.Firebase;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

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
            if(event.getFechaIniLD().equals(date))
                events.add(event);
        }
        return  events;
    }

    public static int numEvents(LocalDate date) {
        int numEvents = 0;
        for(Event event : eventsList)
        {
            if(event.getFechaIniLD().equals(date))
                numEvents++;
                if(numEvents==5) return 5;
        }
        return  numEvents;
    }

    public static ArrayList<Event> eventsForDateAndTime(LocalDate date, LocalTime time){

        ArrayList<Event> events= new ArrayList<>();
        for(Event event : eventsList)
        {
            int eventHourIn = event.getTiempoIniLT().getHour();
            int eventHourFi = event.getTiempoFiLT().minusMinutes(1).getHour();
            int cellHour = time.getHour();
            for(int eventHour = eventHourIn; eventHour <= eventHourFi; eventHour++){
                if(event.getFechaIniLD().equals(date) && eventHour == cellHour)
                    events.add(event);
            }

        }
        return  events;
    }


    private String tiempoIni, tiempoFi;
    private String tipo;
    private int prioridad;
    private String idUser;
    private String id;

    DateTimeFormatter formatterTime = DateTimeFormatter.ofPattern("HH:mm");
    DateTimeFormatter formatterDate = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    public Event(){
        super();
    }

    public Event(String descripcion, String fecha_inicio, String idUser, String nombre, int prioridad, String tiempoIni, String tipo) {
        super(fecha_inicio, fecha_inicio, nombre, descripcion);
        this.tiempoIni = tiempoIni;
        this.tiempoFi = getTiempoIniLT().plusHours(1).toString();
        this.tipo = tipo;
        this.prioridad = prioridad;
        this.idUser = idUser;
    }
/*
    public Event(String nombre, String descripcion, String fecha_inicio, String tiempoIni, String tipo, int prioridad, String idUser) {
        super(fecha_inicio, fecha_inicio, nombre, descripcion);
        this.tiempoIni = tiempoIni;
        this.tiempoFi = LocalTime.parse(tiempoIni, formatterTime).plusHours(1).toString();
        this.tipo = tipo;
        this.prioridad = prioridad;
        this.idUser = idUser;
    }
    public Event(String name, String description, LocalDate fecha, LocalTime tiempoIni, LocalTime tiempoFi, String tipo, int prioridad) {
        super(fecha.toString(), fecha.toString(), name, description);
        this.fecha = fecha;
        this.tiempoIni = tiempoIni;
        this.tiempoFi = tiempoFi;
        this.tipo = tipo;
        this.prioridad = prioridad;
    }*/

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

    public LocalDate getFechaIniLD() {
        return LocalDate.parse(fecha_inicio, formatterDate);
    }

    public LocalDate getFechaFiLD() {
        return LocalDate.parse(fecha_final, formatterDate);
    }

    public LocalTime getTiempoIniLT() {
        return LocalTime.parse(tiempoIni, formatterTime);
    }

    public String getTiempoIni() {
        return tiempoIni;
    }

    public void setTiempoIni(String tiempoIni) {
        this.tiempoIni = tiempoIni;
    }

    public LocalTime getTiempoFiLT() {
        return LocalTime.parse(tiempoFi, formatterTime);
    }

    public String getTiempoFi() {
        return tiempoFi;
    }


    public void setTiempoFi(String tiempoFi) { this.tiempoIni = tiempoFi; }


    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public int getPrioridad() {
        return prioridad;
    }

    public void setPrioridad(int prioridad) {
        this.prioridad = prioridad;
    }

    public String getIdUser() {
        return idUser;
    }

    public void setIdUser(String idUser) {
        this.idUser = idUser;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
