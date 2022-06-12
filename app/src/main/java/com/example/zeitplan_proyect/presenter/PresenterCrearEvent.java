package com.example.zeitplan_proyect.presenter;

import android.content.Context;
import android.os.Build;

import androidx.annotation.RequiresApi;

import com.example.zeitplan_proyect.model.CalendarUtils;
import com.example.zeitplan_proyect.model.EventoBD;

import java.time.LocalDate;

@RequiresApi(api = Build.VERSION_CODES.O)
public class PresenterCrearEvent {
    Context mContext;
    CalendarUtils calendarUtils;
    EventoBD bdEvent =new EventoBD();


    public PresenterCrearEvent(Context mContext) {
        this.mContext = mContext;
    }

    public void guardarEvendoBD(String eventName, String eventDescription, String date,
                                String timeIni, String timeFin, int prioridad,  String tipoEven){

        bdEvent.creaEvento( eventName,  eventDescription,  date,
                 timeIni, timeFin, prioridad,   tipoEven, mContext);



    }
    public void actualizarEvendoBD(String eventName, String eventDescription, String date,
                                String timeIni, String timeFin, int prioridad,  String tipoEven, String idEvento){

        bdEvent.actualizaEvento( eventName,  eventDescription,  date,
                timeIni, timeFin, prioridad,   tipoEven, mContext, idEvento);



    }

}
