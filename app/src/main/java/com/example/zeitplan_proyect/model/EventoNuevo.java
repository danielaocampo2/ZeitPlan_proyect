package com.example.zeitplan_proyect.model;

public class EventoNuevo {
    String titulo, descripcion,idUser;

    public EventoNuevo(){}

    public EventoNuevo(String titulo, String descripcion, String idUser) {
        this.titulo = titulo;
        this.descripcion = descripcion;
        this.idUser = idUser;
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

    public String getIdUser() {
        return idUser;
    }

    public void setIdUser(String idUser) {
        this.idUser = idUser;
    }
}
