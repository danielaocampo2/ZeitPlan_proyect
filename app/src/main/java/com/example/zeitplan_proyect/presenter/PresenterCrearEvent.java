package com.example.zeitplan_proyect.presenter;

import android.os.Build;

import androidx.annotation.RequiresApi;

import com.example.zeitplan_proyect.model.CalendarUtils;

import java.time.LocalDate;

@RequiresApi(api = Build.VERSION_CODES.O)
public class PresenterCrearEvent {

    CalendarUtils calendarUtils;

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

}
