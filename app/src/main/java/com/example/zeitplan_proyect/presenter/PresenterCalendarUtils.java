package com.example.zeitplan_proyect.presenter;

import android.os.Build;

import androidx.annotation.RequiresApi;

import com.example.zeitplan_proyect.DataBase.ListaAsignaturaAdapter;
import com.example.zeitplan_proyect.model.Asignatura;
import com.example.zeitplan_proyect.model.CalendarUtils;
import com.example.zeitplan_proyect.model.Event;
import com.example.zeitplan_proyect.model.HourEvent;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.Month;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
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

    public int numEvents(LocalDate date, ArrayList<Event> events) {
        int numEvents = 0;
        for(Event event : events)
        {
            if(event.getFechaIniLD().equals(date))
                numEvents++;
            if(numEvents==5) return 5;
        }
        return  numEvents;}


    public int numEventsAssig(LocalDate date, ArrayList<Asignatura> asignaturas) {
        int numEventsAssig = 0;

        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        DateTimeFormatter formatterDate = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        try {
            for(Asignatura asignatura : asignaturas)
            {
                LocalDate Fini = LocalDate.parse(formatter.format(formatter.parse(asignatura.getFecha_inicio())), formatterDate).minusDays(1);
                LocalDate FFin = LocalDate.parse(formatter.format(formatter.parse(asignatura.getFecha_final())), formatterDate).plusDays(1);
                if(date.isAfter(Fini) && date.isBefore(FFin)) {

                    String dateDayofWeek = calendarUtils.DayOfWeek(date);
                    ArrayList<String> DiasSemanaAss = asignatura.getDiasSemana();
                    if(DiasSemanaAss!=null){
                        for(String dayOfWeek : DiasSemanaAss){
                            if(dayOfWeek.equals(dateDayofWeek)){
                                numEventsAssig++;
                                break;
                            }
                        }
                    }
                }
            }
        }
            catch (ParseException e) {
            e.printStackTrace();
        }
        return numEventsAssig;
    }
}
