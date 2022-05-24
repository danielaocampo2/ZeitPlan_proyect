package com.example.zeitplan_proyect.presenter;

import android.content.Context;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import com.example.zeitplan_proyect.model.Asignatura;
import com.example.zeitplan_proyect.model.Fechas;
import com.example.zeitplan_proyect.model.ValidacionAsignatura;

import java.util.ArrayList;
import java.util.Date;

public class PresenterAsignatura {

    Fechas fechas;
    ValidacionAsignatura validacionAsignatura;


    public PresenterAsignatura() {
        fechas = new Fechas();
        validacionAsignatura = new ValidacionAsignatura();
    }

    public int validarAs(ArrayList<TextView> inicios, ArrayList<TextView> finales, ArrayList<CheckBox> cajas, EditText campo1, EditText campo2){
        return validacionAsignatura.validarAs(inicios,finales,cajas,campo1,campo2);
    }

    public void crearAsignatura(Date fecha_inicio, Date fecha_final, String nombre_as, String descripcion,
                                ArrayList<CheckBox> cajas, ArrayList<TextView> inicios, ArrayList<TextView> finales, Context mContext){

        ArrayList<String> diasSemana = new ArrayList<>();
        ArrayList<String> horasInicio = new ArrayList<>();
        ArrayList<String> horasFinal = new ArrayList<>();

        for (int i=0; i<cajas.size();i++){
            if(cajas.get(i).isChecked()){
                diasSemana.add(cajas.get(i).getText().toString());
                horasInicio.add(inicios.get(i).getText().toString());
                horasFinal.add(finales.get(i).getText().toString());

            }
        }

        Asignatura asignatura = new Asignatura(fecha_inicio,fecha_final,nombre_as,descripcion,diasSemana,inicios,finales,mContext);
        asignatura.addAsignatura(asignatura);
        asignatura.addFireBaseAsignatura(fecha_inicio,fecha_final,nombre_as,descripcion,diasSemana,inicios,finales);
    }

    public void fechasAsignaturas(String fechaIn, String fechaFin){
        fechas.FechasDiaSemana(fechaIn,fechaFin);
    }






}
