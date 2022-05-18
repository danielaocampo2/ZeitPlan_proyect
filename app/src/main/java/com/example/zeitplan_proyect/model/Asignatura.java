package com.example.zeitplan_proyect.model;

import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class Asignatura extends EventoGeneral {

    ArrayList<TextView> inicios, finales;
    ArrayList<CheckBox> cajas;
    ArrayList<Asignatura> listaAsignaturas = new ArrayList<>();
    Fechas fechas;

    public Asignatura(Date fecha_inicio, Date fecha_final, String nombre_as, String descripcion, ArrayList<CheckBox> cajas,ArrayList<TextView> inicios, ArrayList<TextView> finales){
        super(fecha_inicio, fecha_final, nombre_as, descripcion);
        this.cajas = cajas;
        this.inicios = inicios;
        this.finales = finales;
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
    public Date getFecha_inicio() {
        return fecha_inicio;
    }

    @Override
    public void setFecha_inicio(Date fecha_inicio) {
        this.fecha_inicio = fecha_inicio;
    }

    @Override
    public Date getFecha_final() {
        return fecha_final;
    }

    @Override
    public void setFecha_final(Date fecha_final) {
        this.fecha_final = fecha_final;
    }

    public ArrayList<TextView> getInicios() {
        return inicios;
    }

    public void setInicios(ArrayList<TextView> inicios) {
        this.inicios = inicios;
    }

    public ArrayList<TextView> getFinales() {
        return finales;
    }

    public void setFinales(ArrayList<TextView> finales) {
        this.finales = finales;

    }

    public ArrayList<CheckBox> getCajas() {
        return cajas;
    }

    public void setCajas(ArrayList<CheckBox> cajas) {
        this.cajas = cajas;

        fechas = new Fechas();
        fechas.FechasDiaSemana(this.getFecha_inicio().toString(), this.getFecha_final().toString());

        ArrayList<String> dias_semana = new ArrayList<>();
        for (int i = 0; i<cajas.size(); i++){
            dias_semana.add(cajas.get(i).getText().toString());
        }


    }

    public ArrayList<Asignatura> getListaAsignaturas() {
        return listaAsignaturas;
    }

    public void setListaAsignaturas(ArrayList<Asignatura> listaAsignaturas) {
        this.listaAsignaturas = listaAsignaturas;
    }

}