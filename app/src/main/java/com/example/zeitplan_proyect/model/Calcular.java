package com.example.zeitplan_proyect.model;

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
    public boolean validar(ArrayList<ArrayList> lista_notas_general, ArrayList<ArrayList> lista_porcentajes_general, ArrayList<EditText> lista_porcentajes_final){
        boolean retorno = true;
        float porcentaje_global=0;

        for(int i = 0; i<lista_notas_general.size(); i++){
            ArrayList<EditText> lista_n= lista_notas_general.get(i);
            ArrayList<EditText> lista_p= lista_porcentajes_general.get(i);
            float porcentaje_campo = 0;

            for(int j = 0; j<lista_n.size(); j++){

                if(lista_n.get(j).getText().toString().isEmpty()){
                    lista_n.get(j).setError("Este campo no puede quedar vacio");
                    retorno = false;
                }
                if(lista_p.get(j).getText().toString().isEmpty()){
                    lista_p.get(j).setError("Este campo no puede quedar vacio");
                    retorno = false;
                }else{
                    porcentaje_campo+=Float.parseFloat(lista_p.get(j).getText().toString());
                }
            }
            if(porcentaje_campo!=100){
                retorno = false;
            }
        }

        for(int i = 0; i<lista_porcentajes_final.size(); i++){

            if(lista_porcentajes_final.get(i).getText().toString().isEmpty()){
                lista_porcentajes_final.get(i).setError("Este campo no puede quedar vacio");
                retorno = false;
            }else{
                porcentaje_global+=Float.parseFloat(lista_porcentajes_final.get(i).getText().toString());
            }
        }


        if(porcentaje_global!=100){
            retorno = false;
            //Toast.makeText(getContext(), "Porcentajes incorrectos, el total es distinto de 100", Toast.LENGTH_SHORT).show();
        }

        return retorno;
    }
}
