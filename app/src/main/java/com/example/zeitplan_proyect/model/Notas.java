package com.example.zeitplan_proyect.model;

public class Notas {
    String idUser,texto, nombre;
    static int idNota;
    public User user = User.getInstance();

    public Notas(){}

    public Notas(String idUser, String texto, String nombre) {
        this.idUser = idUser;
        this.texto = texto;
        this.nombre = nombre;
        idNota++;
    }

    public String getIdUser() {
        return idUser;
    }

    public void setIdUser(String idUser) {
        this.idUser = idUser;
    }

    public String getTexto() {
        return texto;
    }

    public void setTexto(String texto) {
        this.texto = texto;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getIdNota(){
        return idNota++;
    }

    public String getUsuarioId(){
         return user.getId();
    }
}
