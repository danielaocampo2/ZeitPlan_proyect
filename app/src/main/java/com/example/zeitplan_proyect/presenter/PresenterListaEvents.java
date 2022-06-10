package com.example.zeitplan_proyect.presenter;

import android.util.Log;

import com.example.zeitplan_proyect.DataBase.MyAdapter;
import com.example.zeitplan_proyect.model.Event;
import com.example.zeitplan_proyect.model.EventoBD;

import java.util.ArrayList;

public class PresenterListaEvents {

    EventoBD consulta = new EventoBD();

    /*public PresenterListaEvents() {

    }*/

    public void filtrarEvento(String filtro, ArrayList<Event> eventos, MyAdapter eAdapter){
        Log.i("filtro presenter", filtro);

            if(filtro.equals("Prioridad")) {

                consulta.filtrarPrioridad(eventos, eAdapter);
            }
            else if(filtro.equals("Nombre")){
                consulta.filtrarNombre(eventos,eAdapter);
            }
            else if(filtro.equals("Evento") || filtro.equals("Trabajo") ||filtro.equals("Entrega") ||filtro.equals("Examen") ){
                consulta.filtrarTipo(eventos,eAdapter, filtro);
            }
    }


}
