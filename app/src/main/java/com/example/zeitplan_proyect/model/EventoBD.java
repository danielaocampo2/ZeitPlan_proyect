package com.example.zeitplan_proyect.model;

import static android.content.ContentValues.TAG;

import android.content.Context;
import android.os.Build;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;

import com.example.zeitplan_proyect.DataBase.Firebase;
import com.example.zeitplan_proyect.DataBase.MyAdapter;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class EventoBD {

    Context mContext;
    Firebase bd = new Firebase();


    public void creaEvento(String eventName, String eventDescription, String date,
                           String timeIni, String timeFin,int prioridad,  String tipoEven, Context mContext){

        String uniqueID = UUID.randomUUID().toString();

        Map<String, Object> map = new HashMap<>();

        map.put("nombre", eventName);
        map.put("descripcion", eventDescription);
        map.put("fecha_inicio", date);
        map.put("tiempoIni", timeIni);
        map.put("tiempoFin", timeFin);
        map.put("tipo", tipoEven);
        map.put("prioridad", prioridad);
        map.put("idUser", bd.getIdUser());
        map.put("idEvento", uniqueID);


        bd.mFirestore.collection("evento").document(uniqueID).set(map).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                Toast.makeText(mContext, "EventoNuevo guardado correctamente", Toast.LENGTH_SHORT).show();
                //retornar a la pantalla anterior
               // Log.i(TAG, "creaEvento: creooo");
            }
        })
        .addOnFailureListener(new OnFailureListener() { // en caso de que no entre correcto muestra un error

            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(mContext, "Error al guardar", Toast.LENGTH_SHORT).show();

            }
        });

    }

    public void filtrarNombre(ArrayList<Event> eventos, MyAdapter eAdapter) {

        bd.mFirestore.collection("evento").whereEqualTo("idUser",bd.getIdUser()).orderBy("nombre", Query.Direction.ASCENDING)
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @RequiresApi(api = Build.VERSION_CODES.O)
                    @Override
                    public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                        if(error != null ){
                            Log.e("Firestore error", error.getMessage() );
                            return;
                        }
                        for(DocumentChange dc: value.getDocumentChanges()){
                            if(dc.getType() == DocumentChange.Type.ADDED || dc.getType() == DocumentChange.Type.REMOVED){
                                eventos.add(dc.getDocument().toObject(Event.class));
                            }
                            eAdapter.notifyDataSetChanged();
                        }
                    }

                });

    }

    public void filtrarTipo(ArrayList<Event> eventos, MyAdapter eAdapter,String tipo) {

        bd.mFirestore.collection("evento").whereEqualTo("idUser",bd.getIdUser()).whereEqualTo("tipo",tipo)
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @RequiresApi(api = Build.VERSION_CODES.O)
                    @Override
                    public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                        if(error != null ){
                            Log.e("Firestore error", error.getMessage() );
                            return;
                        }
                        for(DocumentChange dc: value.getDocumentChanges()){
                            if(dc.getType() == DocumentChange.Type.ADDED ||dc.getType()==DocumentChange.Type.REMOVED){
                                eventos.add(dc.getDocument().toObject(Event.class));
                            }
                            eAdapter.notifyDataSetChanged();
                        }
                    }

                });

    }

    public void filtrarPrioridad(ArrayList<Event> eventos, MyAdapter eAdapter) {
        bd.mFirestore.collection("evento").orderBy("prioridad",Query.Direction.DESCENDING).whereEqualTo("idUser",bd.getIdUser())
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @RequiresApi(api = Build.VERSION_CODES.O)
                    @Override
                    public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {

                        if(error != null ){
                            Log.e("Firestore error", error.getMessage() );
                            return;
                        }
                        for(DocumentChange dc: value.getDocumentChanges()){

                            if(dc.getType() == DocumentChange.Type.ADDED ){
                                eventos.add(dc.getDocument().toObject(Event.class));
                            }
                            if(dc.getType()== DocumentChange.Type.REMOVED){
                                eventos.remove(dc.getOldIndex());
                            }
                            eAdapter.notifyDataSetChanged();
                        }
                    }

          });
    }


}


