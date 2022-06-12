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

    private String tiempoIni, tiempoFi;
    private String tipo;
    private int prioridad;
    private String idUser;
    private String id;
    private String idEvento;
    DateTimeFormatter formatterTime = DateTimeFormatter.ofPattern("HH:mm");
    DateTimeFormatter formatterDate = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    public Event(){
        super();
    }

    public Event(String descripcion, String fecha_inicio, String idUser, String nombre, int prioridad, String tiempoFin, String tiempoIni, String tipo ,String idEvento) {
        super(fecha_inicio, fecha_inicio, nombre, descripcion);
        this.tiempoIni = tiempoIni;
        this.tiempoFi = tiempoFin;
        this.tipo = tipo;
        this.prioridad = prioridad;
        this.idUser = idUser;
        this.idEvento=idEvento;
    }

    public String getIdEvento() {
        return idEvento;
    }

    public void setIdEvento(String idEvento) {
        this.idEvento = idEvento;
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

    public int getTiempoFiLTHour() {
        return LocalTime.parse(tiempoFi, formatterTime).getHour();
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
