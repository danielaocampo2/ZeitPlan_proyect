package com.example.zeitplan_proyect.model;

import android.util.Log;

import androidx.annotation.NonNull;

import com.example.zeitplan_proyect.DataBase.Firebase;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class ListaAsignatura {

    Firebase firebase = new Firebase();

    public vmInterface listener;
    public ListaAsignatura listaAsignatura;

    public interface vmInterface{
        void setCollection(ArrayList<Asignatura> ac);
        void setToast(String s);
    }

    public ListaAsignatura(vmInterface listener) {
        this.listener = listener;
        listaAsignatura = this;
    }

    public void getUserAsignaturas(){
        firebase.mFirestore.collection("user").document(firebase.getIdUser()).collection("Asignaturas").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    ArrayList<Asignatura> as = new ArrayList<>() ;
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        Asignatura asignatura = new Asignatura((String) document.get("Fecha inicio"), (String)document.get("Fecha final"), (String)document.get("Name"), (String)document.get("Descripcion"), (ArrayList<String>) document.get("Dias semana"), (ArrayList<String>) document.get("Horas de inicio"), (ArrayList<String>) document.get("Horas de Final"), null);
                        as.add(asignatura);
                    }
                    Log.i("TAG", "onComplete: "+ as);
                    listener.setCollection(as);
                } else {
                    Log.e("TAG", "Error getting documents: ", task.getException());
                }
            }

        });
    }



}
