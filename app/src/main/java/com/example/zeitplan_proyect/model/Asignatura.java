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
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class Asignatura extends EventoGeneral {

    ArrayList<String> inicios, finales;
    ArrayList<String> diasSemana;
    Fechas fechas;
    Context mContext;
    Firebase bd = new Firebase();

    public Asignatura(String fecha_inicio, String fecha_final, String nombre_as, String descripcion, ArrayList<String> diasSemana,ArrayList<String> inicios, ArrayList<String> finales, Context mContext){
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


    public void addFireBaseAsignatura(String fecha_inicio, String fecha_final, String nombre_as, String descripcion, ArrayList<String> diasSemana,ArrayList<String> inicios, ArrayList<String> finales){

        Map<String, Object> map = new HashMap<>();
        map.put("idUser", bd.getIdUser());
        map.put("Fecha inicio", fecha_inicio);
        map.put("Fecha final", fecha_final);
        map.put("Dias de la semana", diasSemana);
        map.put("Horas de inicio", inicios);
        map.put("Horas de final", finales);
        map.put("Name", nombre_as);
        map.put("Descripcion", descripcion);

        bd.mFirestore.collection("Asignaturas").document(nombre_as).set(map).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                Toast.makeText(mContext, "Asignatura guardada correctamente", Toast.LENGTH_SHORT).show();

            }
        }).addOnFailureListener(new OnFailureListener() { // en caso de que no entre correcto muestra un error
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(mContext, "Error al guardar", Toast.LENGTH_SHORT).show();

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



}
