package com.example.zeitplan_proyect.model;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Filter;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.zeitplan_proyect.DataBase.Firebase;
import com.example.zeitplan_proyect.MainActivity2;
import com.example.zeitplan_proyect.presenter.PresenterAsignatura;
import com.example.zeitplan_proyect.vista.RegistroActivity;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class Asignatura extends EventoGeneral {

    ArrayList<TextView> inicios, finales;
    ArrayList<String> diasSemana;
    ArrayList<Asignatura> listaAsignaturas = new ArrayList<>();
    Fechas fechas;
    Context mContext;

    Firebase bd = new Firebase();

    public Asignatura(Date fecha_inicio, Date fecha_final, String nombre_as, String descripcion, ArrayList<String> diasSemana,ArrayList<TextView> inicios, ArrayList<TextView> finales, Context mContext){
        super(fecha_inicio, fecha_final, nombre_as, descripcion);
        this.diasSemana = diasSemana;
        this.mContext = mContext;
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

    public void addFireBaseAsignatura(Date fecha_inicio, Date fecha_final, String nombre_as, String descripcion, ArrayList<String> diasSemana,ArrayList<TextView> inicios, ArrayList<TextView> finales){

        Map<String, Object> map = new HashMap<>();
        map.put("id", bd.getIdUser());
        map.put("name", nombre_as);
        map.put("descripcion", descripcion);

        bd.mFirestore.collection("user").document(bd.getIdUser()).collection("Asignaturas").document("autoincrement").set(map).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                Log.e("HOLAAAAAAA","EEEEEE");
                Toast.makeText(mContext, "Usuario registrado con exito", Toast.LENGTH_SHORT).show();
                //Intent dashboardActivity = new Intent(mContext, MainActivity2.class);
                //mContext.startActivity(dashboardActivity);
                //((RegistroActivity)mContext).finish();

            }
        }).addOnFailureListener(new OnFailureListener() { // en caso de que no entre correcto uestra un error
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(mContext, "Error al guardar", Toast.LENGTH_SHORT).show();
                Log.e("HOLAAAAAAA","MAL");

            }
        });
    }

    /*
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


    }*/

    public ArrayList<Asignatura> getListaAsignaturas() {
        return listaAsignaturas;
    }

    public void setListaAsignaturas(ArrayList<Asignatura> listaAsignaturas) {
        this.listaAsignaturas = listaAsignaturas;
    }

    public void addAsignatura(Asignatura asignatura){
        this.getListaAsignaturas().add(asignatura);
    }

}
