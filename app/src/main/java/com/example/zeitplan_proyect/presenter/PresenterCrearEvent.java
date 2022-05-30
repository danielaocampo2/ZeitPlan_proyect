package com.example.zeitplan_proyect.presenter;

import static android.content.ContentValues.TAG;

import android.content.Context;
import android.os.Build;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.RequiresApi;

import com.example.zeitplan_proyect.model.CalendarUtils;
import com.example.zeitplan_proyect.model.Event;
import com.example.zeitplan_proyect.model.EventoBD;

import java.time.LocalDate;
import java.time.LocalTime;

@RequiresApi(api = Build.VERSION_CODES.O)
public class PresenterCrearEvent {
    Context mContext;
    CalendarUtils calendarUtils;
    EventoBD bdEvent =new EventoBD();


    public PresenterCrearEvent(Context mContext) {
        this.mContext = mContext;
    }

    public PresenterCrearEvent() {
        this.calendarUtils = CalendarUtils.getInstance();
    }

    public LocalDate getSelectedDate()
    {
        return calendarUtils.getSelectedDate();
    }

    public void setSelectedDate(LocalDate date)
    {
        calendarUtils.setSelectedDate(date);
    }

    public void guardarEvendoBD(String eventName, String eventDescription, String date,
                                String time,int prioridad,  String tipoEven){

        bdEvent.creaEvento( eventName,  eventDescription,  date,
                 time, prioridad,   tipoEven, mContext);



    }

}
