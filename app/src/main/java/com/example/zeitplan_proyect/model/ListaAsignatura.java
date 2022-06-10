package com.example.zeitplan_proyect.model;

import android.content.Context;

import androidx.annotation.Nullable;

import com.example.zeitplan_proyect.DataBase.Firebase;
import com.example.zeitplan_proyect.DataBase.ListaAsignaturaAdapter;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class ListaAsignatura {

    ArrayList<Asignatura> asignaturaArrayList;
    ListaAsignaturaAdapter listaAsignaturaAdapter;
    Firebase bd;

    public ListaAsignatura(Context context){
        bd = new Firebase();
        asignaturaArrayList = new ArrayList<Asignatura>();
        listaAsignaturaAdapter = new ListaAsignaturaAdapter(context,asignaturaArrayList);
    }

    public ListaAsignaturaAdapter getListaAsignaturaAdapter() {
        return listaAsignaturaAdapter;
    }

    public void EventChangedListener(){
        bd.mFirestore.collection("user").document(bd.getIdUser()).collection("Asignaturas").addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                if(error!=null){

                }
                for(DocumentChange dc : value.getDocumentChanges()){
                    if(dc.getType() == DocumentChange.Type.ADDED){
                        Asignatura asignatura = new Asignatura((String) dc.getDocument().get("Fecha inicio"), (String)dc.getDocument().get("Fecha final"), (String)dc.getDocument().get("Name"), (String)dc.getDocument().get("Descripcion"), (ArrayList<String>)dc.getDocument().get("Dias semana"), (ArrayList<String>) dc.getDocument().get("Horas de inicio"), (ArrayList<String>) dc.getDocument().get("Horas de Final"), null);
                        asignaturaArrayList.add(asignatura);
                    }
                }
                setAsignaturaArrayList(asignaturaArrayList);
                listaAsignaturaAdapter.notifyDataSetChanged();
            }
        });
    }

    public ArrayList<Asignatura> getAsignaturaArrayList() {
        return asignaturaArrayList;
    }

    public void setAsignaturaArrayList(ArrayList<Asignatura> asignaturaArrayList) {
        this.asignaturaArrayList = asignaturaArrayList;
    }



/*Firebase firebase = new Firebase();

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
    }*/

}
