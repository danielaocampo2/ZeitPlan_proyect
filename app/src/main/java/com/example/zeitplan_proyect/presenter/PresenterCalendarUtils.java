package com.example.zeitplan_proyect.presenter;

import android.os.Build;

import androidx.annotation.RequiresApi;

import com.example.zeitplan_proyect.model.CalendarUtils;

import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.Locale;

@RequiresApi(api = Build.VERSION_CODES.O)
public class PresenterCalendarUtils {

    CalendarUtils calendarUtils;


    public PresenterCalendarUtils() {
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

    public void SelDateMoveMonth(int i)
    {
        calendarUtils.SelDateMoveMonth(i);
    }

    public void SelDateMoveWeek(int i) { calendarUtils.SelDateMoveWeek(i); }

    public void SelDateMoveDay(int i)
    {
        calendarUtils.SelDateMoveDay(i);
    }

    public Month SelDateMonth()
    {
        return calendarUtils.SelDateMonth();
    }
    public String SelDateDayOfWeek() { return calendarUtils.SelDateDayOfWeek(); }

    public String monthYearFromSelDay() { return calendarUtils.monthYearFromSelDay(); }
    public String monthDayFromSelDay(){ return calendarUtils.monthDayFromSelDay();}

    public ArrayList<LocalDate> daysInWeekArray() { return calendarUtils.daysInWeekArray();}
}
