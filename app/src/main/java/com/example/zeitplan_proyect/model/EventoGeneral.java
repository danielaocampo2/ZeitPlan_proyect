package com.example.zeitplan_proyect.model;

import java.time.LocalDate;
import java.util.Date;

public abstract class EventoGeneral {

    public Date fecha_inicio, fecha_final;
    public String nombre, descripcion;

    public EventoGeneral(Date fecha_inicio, Date fecha_final, String nombre, String descripcion){
        this.nombre = nombre;
        this.fecha_inicio = fecha_inicio;
        this.fecha_final = fecha_final;
        this.descripcion = descripcion;
    }
    public abstract String getNombre();
    public abstract void setNombre(String nombre);

    public abstract String getDescripcion();
    public abstract void setDescripcion(String descripcion);

    public abstract Date getFecha_inicio();
    public abstract void setFecha_inicio(Date fecha_inicio);

    public abstract Date getFecha_final();
    public abstract void setFecha_final(Date fecha_final);

}
