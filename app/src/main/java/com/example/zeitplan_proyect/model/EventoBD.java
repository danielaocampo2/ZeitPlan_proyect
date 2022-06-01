package com.example.zeitplan_proyect.model;

import static android.content.ContentValues.TAG;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.zeitplan_proyect.DataBase.Firebase;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class EventoBD {

    Context mContext;
    Firebase bd = new Firebase();


    public void creaEvento(String eventName, String eventDescription, String date,
                           String time,int prioridad,  String tipoEven, Context mContext){

        String uniqueID = UUID.randomUUID().toString();

        Map<String, Object> map = new HashMap<>();
        map.put("idUser", bd.getIdUser());
        map.put("titulo", eventName);
        map.put("descripcion", eventDescription);
        map.put("fecha", date);
        map.put("hora", time);
        map.put("prioridad", prioridad);
        map.put("tipo", tipoEven);



         //Toast.makeText(mContext, "holii555", Toast.LENGTH_SHORT).show();


        bd.mFirestore.collection("evento").document(uniqueID).set(map).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                Toast.makeText(mContext, "EventoNuevo guardado correctamente", Toast.LENGTH_SHORT).show();
                //retornar a la pantalla anterior
                Log.i(TAG, "creaEvento: creooo");
            }
        });
        /*.addOnFailureListener(new OnFailureListener() { // en caso de que no entre correcto muestra un error

            @Override
            public void onFailure(@NonNull Exception e) {
              //  Toast.makeText(mContext, "Error al guardar", Toast.LENGTH_SHORT).show();

            }
        });*/
    }
}
