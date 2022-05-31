package com.example.zeitplan_proyect.model;

public class EventoNuevo {
    String titulo, descripcion,prioridad;

    public EventoNuevo(){}

    public EventoNuevo(String titulo, String descripcion, String prioridad) {
        this.titulo = titulo;
        this.descripcion = descripcion;
        this.prioridad = prioridad;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getPrioridad() {
        return prioridad;
    }

    public void setPrioridad(String prioridad) {
        this.prioridad = prioridad;
    }
}
