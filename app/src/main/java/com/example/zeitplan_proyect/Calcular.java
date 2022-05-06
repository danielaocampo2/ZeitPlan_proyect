package com.example.zeitplan_proyect;

import android.widget.EditText;

import java.util.ArrayList;

public class Calcular {

    public Float calcular_notaFinal(ArrayList<ArrayList> lista_notas_general, ArrayList<ArrayList> lista_porcentajes_general, ArrayList<EditText> lista_porcentajes_final){
        float nota = 0, provisional, porcentaje_general;
        for(int i = 0; i<lista_notas_general.size(); i++){
            provisional = calcular_nota(lista_notas_general.get(i), lista_porcentajes_general.get(i));
            porcentaje_general = Float.parseFloat(lista_porcentajes_final.get(i).getText().toString()) / 100;
            nota+=(provisional*porcentaje_general);
        }
        return nota;

    }
    public Float calcular_nota (ArrayList<EditText> lista_notas, ArrayList<EditText> lista_porcentajes){
        float nota_final = 0;
        for(int i = 0; i<lista_notas.size(); i++){
            float tanto_x_ciento = Float.parseFloat(lista_porcentajes.get(i).getText().toString()) / 100;
            float nota = Float.parseFloat(lista_notas.get(i).getText().toString());
            nota_final+=nota*tanto_x_ciento;
        }
        return nota_final;
    }
}
