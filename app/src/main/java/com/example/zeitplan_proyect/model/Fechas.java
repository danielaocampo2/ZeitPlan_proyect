package com.example.zeitplan_proyect.model;
import android.util.Log;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class Fechas {

    SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
    List<String> dates = new ArrayList<String>();

    public void FechasDiaSemana(String fecha_in, String fecha_fin){

        Calendar cini = Calendar.getInstance();

        try {
            cini.setTime(formatter.parse(fecha_in));

            Calendar cfin = Calendar.getInstance();
            cfin.setTime(formatter.parse(fecha_fin));
            while (cfin.after(cini) || cfin.equals(cini)) {
                if (cini.get(Calendar.DAY_OF_WEEK) == Calendar.MONDAY) {
                    Log.e("TAG","---------->" + cini.getTime().toString() + " es Lunes ");
                    dates.add(formatter.format(cini.getTime()));
                }
                if (cini.get(Calendar.DAY_OF_WEEK) == Calendar.TUESDAY) {
                    Log.e("TAG","---------->" + cini.getTime().toString() + " es Martes ");
                    dates.add(formatter.format(cini.getTime()));
                }
                if (cini.get(Calendar.DAY_OF_WEEK) == Calendar.WEDNESDAY) {
                    Log.e("TAG","---------->" + cini.getTime().toString() + " es Miercoles ");
                    dates.add(formatter.format(cini.getTime()));
                }
                if (cini.get(Calendar.DAY_OF_WEEK) == Calendar.THURSDAY) {
                    Log.e("TAG","---------->" + cini.getTime().toString() + " es Jueves ");
                    dates.add(formatter.format(cini.getTime()));
                }
                if (cini.get(Calendar.DAY_OF_WEEK) == Calendar.FRIDAY) {
                    Log.e("TAG","---------->" + cini.getTime().toString() + " es Viernes ");
                    dates.add(formatter.format(cini.getTime()));
                }
                cini.add(Calendar.DATE, 1);
            }
        }
        catch (ParseException e) {
            e.printStackTrace();
        }
    }

}
