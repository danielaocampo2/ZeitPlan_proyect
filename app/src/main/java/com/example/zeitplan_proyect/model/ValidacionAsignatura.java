package com.example.zeitplan_proyect.model;

import android.util.Log;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Date;

public class ValidacionAsignatura {

    public int validarAs(ArrayList<TextView> inicios, ArrayList<TextView> finales, ArrayList<CheckBox> cajas, EditText campo1, TextView campo2, TextView campo3){
        int retorno = 0;

        String c1 = campo1.getText().toString();
        String c2 = campo2.getText().toString();
        String c3 = campo3.getText().toString();

        if (c1.isEmpty()){
            campo1.setError("Este campo no puede quedar vacio");
            retorno = 1;
        }if (c2.isEmpty()){
            campo2.setError("Este campo no puede quedar vacio");
            retorno = 1;
        }else{
            campo2.setError(null);
        }if(c3.isEmpty()){
            campo3.setError("Este campo no puede quedar vacio");
            retorno = 1;
        }else{
            campo3.setError(null);
        }
        if(retorno!=1){
            retorno = validarFecha(c2, c3);
        }

        for (int i=0; i<cajas.size(); i++){
            if(cajas.get(i).isChecked()){
                if(inicios.get(i).getText().toString().isEmpty()||finales.get(i).getText().toString().isEmpty()){
                    retorno = 2;
                }else{
                    retorno = validarCampo(inicios.get(i).getText().toString(), finales.get(i).getText().toString());
                }
            }
        }
        return retorno;
    }

    public boolean equalsAsignatura(String nombreAsignatura, ArrayList<Asignatura> listaAsignaturas){
        for(int i = 0; i<listaAsignaturas.size(); i++){
            if(listaAsignaturas.get(i).getNombre().equals(nombreAsignatura)){
                return true;
            }
        }
        return false;
    }

    public int validarFecha(String inicio, String fin){
        int retorno = 0;
        if(Date.parse(fin) < Date.parse(inicio)) {
            retorno = 4;
        }
        return retorno;
    }
    public int validarCampo(String inicio, String fin){
        int retorno = 0;

        int hora_inicio = Integer.parseInt(inicio.substring(0,inicio.indexOf(':')));
        int hora_final = Integer.parseInt(fin.substring(0,fin.indexOf(':')));

        int min_inicio = Integer.parseInt(inicio.substring(inicio.indexOf(':')+1,inicio.length()));
        int min_final = Integer.parseInt(fin.substring(fin.indexOf(':')+1,fin.length()));

        if (hora_inicio > hora_final) {
            retorno = 3;
        } else if (hora_inicio == hora_final) {
            if (min_inicio > min_final) {
                retorno = 3;
            }
        }

        return retorno;
    }


}
