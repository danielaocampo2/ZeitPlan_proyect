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
                                String time,int prioridad,  String tipoEven){

        bdEvent.creaEvento( eventName,  eventDescription,  date,
                 time, prioridad,   tipoEven, mContext);



    }

}
