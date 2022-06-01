package com.example.zeitplan_proyect.model;

import java.time.LocalDate;
import java.util.Date;

public abstract class EventoGeneral {

    public String fecha_inicio, fecha_final;
    public String nombre, descripcion;

    public EventoGeneral(String fecha_inicio, String fecha_final, String nombre, String descripcion){
        this.nombre = nombre;
        this.fecha_inicio = fecha_inicio;
        this.fecha_final = fecha_final;
        this.descripcion = descripcion;
    }

    public EventoGeneral() {}

    public abstract String getNombre();
    public abstract void setNombre(String nombre);

    public abstract String getDescripcion();
    public abstract void setDescripcion(String descripcion);

    public abstract String getFecha_inicio();
    public abstract void setFecha_inicio(String fecha_inicio);

    public abstract String getFecha_final();
    public abstract void setFecha_final(String fecha_final);

}
