package com.example.zeitplan_proyect.model;

import android.os.Build;

import androidx.annotation.RequiresApi;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.Month;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

@RequiresApi(api = Build.VERSION_CODES.O)
public class CalendarUtils {

    private static CalendarUtils instance;
    private static LocalDate selectedDate;

    public CalendarUtils() {
    }

    public static CalendarUtils getInstance() {
        if (instance == null) {
            instance = new CalendarUtils();
        }
        return instance;
    }

    public static LocalDate getSelectedDate() {
        return selectedDate;
    }

    public static void setSelectedDate(LocalDate selectedDate) {
        CalendarUtils.selectedDate = selectedDate;
    }

    public static String formattedDate(LocalDate date)
    {
        return date.getDayOfMonth() + " " + MonthToString(date.getMonth())+ " " + date.getYear();
    }

    public static String formattedTime(LocalTime time)
    {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("hh:mm:ss");
        return time.format(formatter);
    }

    public static String formattedShortTime(LocalTime time)
    {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
        return time.format(formatter);
    }

    public static ArrayList<LocalDate> daysInMonthArray()
    {
        ArrayList<LocalDate> daysMonth = new ArrayList<>();

        YearMonth yearMonth = YearMonth.from(selectedDate);
        int daysInMonth = yearMonth.lengthOfMonth();

        LocalDate prevMonth = selectedDate.minusMonths(1);
        LocalDate nextMonth = selectedDate.plusMonths(1);

        YearMonth prevYearMonth = YearMonth.from(prevMonth);
        int prevDaysInMonth = prevYearMonth.lengthOfMonth();

        LocalDate firstOfMonth = CalendarUtils.selectedDate.withDayOfMonth(1);
        int dayOfWeek = (firstOfMonth.getDayOfWeek().getValue()+6)%7;

        for(int i = 1; i<=42; i++){
            if(i<= dayOfWeek){
                daysMonth.add(LocalDate.of(prevMonth.getYear(),prevMonth.getMonth(),prevDaysInMonth + i - dayOfWeek));
            }
            else if(i > daysInMonth + dayOfWeek)
                daysMonth.add(LocalDate.of(nextMonth.getYear(),nextMonth.getMonth(),i-dayOfWeek-daysInMonth));
            else{
                daysMonth.add(LocalDate.of(selectedDate.getYear(),selectedDate.getMonth(),i-dayOfWeek));
            }
        }
        return daysMonth;
    }

    public static String monthYearFromDate(LocalDate date)
    {
        return MonthToString(date.getMonth()) + " " + date.getYear();
    }

    public static String monthDayFromDate(LocalDate date)
    {
        return date.getDayOfMonth() + " " + MonthToString(date.getMonth());
    }

    public static String MonthToString(Month month){
        switch(month){
            case JANUARY: return "Enero";
            case FEBRUARY: return "Febrero";
            case MARCH: return "Marzo";
            case APRIL: return "Abril";
            case MAY: return "Mayo";
            case JUNE: return "Junio";
            case JULY: return "Julio";
            case AUGUST: return "Agosto";
            case SEPTEMBER: return "Septiembre";
            case OCTOBER: return "Octubre";
            case NOVEMBER: return "Noviembre";
            case DECEMBER: return "Diciembre";
        }
        return " ";
    }


    public static ArrayList<LocalDate> daysInWeekArray() {
        ArrayList<LocalDate> days = new ArrayList<>();
        LocalDate current = mondayForDate(selectedDate);
        LocalDate endDate = current.plusWeeks(1);
        while (current.isBefore(endDate)){
            days.add(current);
            current = current.plusDays(1);
        }
        return days;
    }

    private static LocalDate mondayForDate(LocalDate current) {
        LocalDate oneWeekAgo = current.minusWeeks(1);

        while (current.isAfter(oneWeekAgo))
        {
            if(current.getDayOfWeek() == DayOfWeek.MONDAY)
                return current;

            current = current.minusDays(1);
        }
        return null;
    }


    public void SelDateMoveMonth(int i) {
        if(i>0){
            selectedDate = selectedDate.plusMonths(i);
        }else if(i<0) {
            selectedDate = selectedDate.minusMonths(-i);
        }
    }
    public void SelDateMoveWeek(int i) {
        if(i>0){
            selectedDate = selectedDate.plusWeeks(i);
        }else if(i<0) {
            selectedDate = selectedDate.minusWeeks(-i);
        }
    }

    public void SelDateMoveDay(int i) {
        if(i>0){
            selectedDate = selectedDate.plusDays(i);
        }else if(i<0) {
            selectedDate = selectedDate.minusDays(-i);
        }
    }

    public String monthYearFromSelDay(){
        return monthYearFromDate(selectedDate);
    }

    public String monthDayFromSelDay(){
        return monthDayFromDate(selectedDate);
    }


    public Month SelDateMonth() {
        return selectedDate.getMonth();
    }

    public String SelDateDayOfWeek() {
        switch(selectedDate.getDayOfWeek()){
            case MONDAY: return "Lunes";
            case TUESDAY: return "Martes";
            case WEDNESDAY: return "Miercoles";
            case THURSDAY: return "Jueves";
            case FRIDAY: return "Viernes";
            case SATURDAY: return "Sabado";
            case SUNDAY: return "Domingo";
        }
        return " ";
    }

}
