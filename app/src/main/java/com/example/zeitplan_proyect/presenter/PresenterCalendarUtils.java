package com.example.zeitplan_proyect.presenter;

import android.os.Build;

import androidx.annotation.RequiresApi;

import com.example.zeitplan_proyect.model.CalendarUtils;
import com.example.zeitplan_proyect.model.Event;
import com.example.zeitplan_proyect.model.HourEvent;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.Locale;

@RequiresApi(api = Build.VERSION_CODES.O)
public class PresenterCalendarUtils {


    private static PresenterCalendarUtils instance;
    CalendarUtils calendarUtils;

    public PresenterCalendarUtils() {
        this.calendarUtils = new CalendarUtils();
    }

    public static PresenterCalendarUtils getInstance() {
        if(instance == null) instance = new PresenterCalendarUtils();
        return instance;
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
    public ArrayList<LocalDate> daysInMonthArray() { return calendarUtils.daysInMonthArray();}

    public String formattedShortTime(LocalTime time){ return calendarUtils.formattedShortTime(time);}
    public String formattedTime(LocalTime time) { return calendarUtils.formattedTime(time);}

    public String formattedDate(LocalDate date){return calendarUtils.formattedDate(date);}
    public String formattedDateNum(LocalDate date){return calendarUtils.formattedDateNum(date);}

    public HourEvent newHourEvent(LocalTime time) {
        ArrayList<Event> events = Event.eventsForDateAndTime(calendarUtils.getSelectedDate(), time);
        HourEvent hourEvent = new HourEvent(time, events);
        return hourEvent;
    }
}
