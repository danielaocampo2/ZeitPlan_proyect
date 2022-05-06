package com.example.zeitplan_proyect;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;

import android.content.Context;
import android.content.DialogInterface;
import android.nfc.Tag;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;

public class CalculadoraActivity extends Fragment {

    Button boton_add1, boton_add2, boton_add3, boton_add4, boton_add5, boton_calculo, boton_campoNuevo;
    LinearLayout container1, container2, container3, container4, container5;
    EditText nota1, porcentaje1, nota_final, porcentaje_teoria;
    ConstraintLayout campo1, campo2, campo3, campo4;
    ImageButton remove_campo1, remove_campo2, remove_campo3, remove_campo4;


    EditText nota2, porcentaje2, porcentaje_campo1;
    EditText nota3, porcentaje3, porcentaje_campo2;
    EditText nota4, porcentaje4, porcentaje_campo3;
    EditText nota5, porcentaje5, porcentaje_campo4;


    ArrayList<EditText> lista_notas1, lista_porcentajes1, lista_notas2, lista_porcentajes2,
            lista_notas3, lista_porcentajes3, lista_notas4, lista_porcentajes4, lista_notas5, lista_porcentajes5,
            lista_final_porcentajes;

    ArrayList<ArrayList> lista_notas_general, lista_porcentajes_general;

    Calcular calcular;

    NavigationView navigationView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        final  View view = inflater.inflate(R.layout.activity_calculadora, container, false);
        ((MainActivity2)getActivity()).getSupportActionBar().setTitle("Calculadora");
        FloatingActionButton shareBtn = ((MainActivity2)getActivity()).findViewById(R.id.share);

        //LISTAS DE CALCULO
        lista_notas1= new ArrayList<>();
        lista_porcentajes1 = new ArrayList<>();
        lista_final_porcentajes = new ArrayList<>();


        //APARTADO TEORIA
        nota1 = (EditText) view.findViewById(R.id.textField_AñadeNota);
        porcentaje1 = (EditText) view.findViewById(R.id.textField_AñadeTantoPorCiento);
        porcentaje_teoria = (EditText) view.findViewById(R.id.textField_AñadeTantoPorCiento00);

        boton_add1 = view.findViewById(R.id.Boton_add);
        container1 = view.findViewById(R.id.container);

        //APARTADOS NUEVOS
        boton_campoNuevo = view.findViewById(R.id.Boton_añadir_campo);

        campo1 = view.findViewById(R.id.campo_1);
        container2 = view.findViewById(R.id.container2);
        boton_add2 = view.findViewById(R.id.Boton_add2);
        remove_campo1 = view.findViewById(R.id.remove_campo1);

        campo2 = view.findViewById(R.id.campo_2);
        container3 = view.findViewById(R.id.container3);
        boton_add3 = view.findViewById(R.id.Boton_add3);
        remove_campo2 = view.findViewById(R.id.remove_campo2);

        campo3 = view.findViewById(R.id.campo_3);
        container4 = view.findViewById(R.id.container4);
        boton_add4 = view.findViewById(R.id.Boton_add4);
        remove_campo3 = view.findViewById(R.id.remove_campo3);

        campo4 = view.findViewById(R.id.campo_4);
        container5 = view.findViewById(R.id.container5);
        boton_add5 = view.findViewById(R.id.Boton_add5);
        remove_campo4 = view.findViewById(R.id.remove_campo4);

        //NOTA FINAL
        nota_final = view.findViewById(R.id.textField_NotaFinal);
        boton_calculo = view.findViewById(R.id.Boton_calcular);
        calcular = new Calcular();

        lista_notas1.add(nota1);
        lista_porcentajes1.add(porcentaje1);
        lista_final_porcentajes.add(porcentaje_teoria);

        navigationView = ((MainActivity2) getActivity()).getNavigationView();

        ((MainActivity2) getActivity()).setupNavigationDrawerContent(navigationView);
        //Hide share button
        shareBtn.setVisibility(View.GONE);

        boton_add1.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                LayoutInflater layoutInflater = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                final View addView = layoutInflater.inflate(R.layout.fila_calculadora, null);
                ImageButton buttonRemove = addView.findViewById(R.id.remove_new);

                EditText nota_row = addView.findViewById(R.id.textField_AñadeNota_row);
                EditText porcentaje_row = addView.findViewById(R.id.textField_AñadeTantoPorCiento_row);

                lista_notas1.add(nota_row);
                lista_porcentajes1.add(porcentaje_row);

                final View.OnClickListener thisListener = new View.OnClickListener(){
                    @Override
                    public void onClick(View v) {
                        ((LinearLayout)addView.getParent()).removeView(addView);
                        lista_notas1.remove(nota_row);
                        lista_porcentajes1.remove(porcentaje_row);
                    }
                };
                buttonRemove.setOnClickListener(thisListener);
                container1.addView(addView);
            }
        });


        boton_add2.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                final  View view = inflater.inflate(R.layout.activity_calculadora, container, false);
                LayoutInflater layoutInflater = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                final View addView = layoutInflater.inflate(R.layout.fila_calculadora, null);
                ImageButton buttonRemove = addView.findViewById(R.id.remove_new);

                EditText nota_row = addView.findViewById(R.id.textField_AñadeNota_row);
                EditText porcentaje_row = addView.findViewById(R.id.textField_AñadeTantoPorCiento_row);

                lista_notas2.add(nota_row);
                lista_porcentajes2.add(porcentaje_row);


                final View.OnClickListener thisListener = new View.OnClickListener(){
                    @Override
                    public void onClick(View v) {
                        ((LinearLayout)addView.getParent()).removeView(addView);
                        lista_notas2.remove(nota_row);
                        lista_porcentajes2.remove(porcentaje_row);

                    }
                };

                buttonRemove.setOnClickListener(thisListener);
                container2.addView(addView);
            }
        });


        remove_campo1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                campo1.setVisibility(View.GONE);
                lista_final_porcentajes.remove(porcentaje_campo1);
            }
        });

        boton_add3.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                LayoutInflater layoutInflater = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                final View addView = layoutInflater.inflate(R.layout.fila_calculadora, null);
                ImageButton buttonRemove = addView.findViewById(R.id.remove_new);

                EditText nota_row = addView.findViewById(R.id.textField_AñadeNota_row);
                EditText porcentaje_row = addView.findViewById(R.id.textField_AñadeTantoPorCiento_row);

                lista_notas3.add(nota_row);
                lista_porcentajes3.add(porcentaje_row);


                final View.OnClickListener thisListener = new View.OnClickListener(){
                    @Override
                    public void onClick(View v) {
                        ((LinearLayout)addView.getParent()).removeView(addView);
                        lista_notas3.remove(nota_row);
                        lista_porcentajes3.remove(porcentaje_row);

                    }
                };

                buttonRemove.setOnClickListener(thisListener);
                container3.addView(addView);
            }
        });

        remove_campo2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                campo2.setVisibility(View.GONE);
                lista_final_porcentajes.remove(porcentaje_campo2);
            }
        });

        boton_add4.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                LayoutInflater layoutInflater = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                final View addView = layoutInflater.inflate(R.layout.fila_calculadora, null);
                ImageButton buttonRemove = addView.findViewById(R.id.remove_new);

                EditText nota_row = addView.findViewById(R.id.textField_AñadeNota_row);
                EditText porcentaje_row = addView.findViewById(R.id.textField_AñadeTantoPorCiento_row);

                lista_notas4.add(nota_row);
                lista_porcentajes4.add(porcentaje_row);


                final View.OnClickListener thisListener = new View.OnClickListener(){
                    @Override
                    public void onClick(View v) {
                        ((LinearLayout)addView.getParent()).removeView(addView);
                        lista_notas4.remove(nota_row);
                        lista_porcentajes4.remove(porcentaje_row);

                    }
                };

                buttonRemove.setOnClickListener(thisListener);
                container4.addView(addView);
            }
        });

        remove_campo3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                campo3.setVisibility(View.GONE);
                lista_final_porcentajes.remove(porcentaje_campo3);
            }
        });

        boton_add5.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                LayoutInflater layoutInflater = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                final View addView = layoutInflater.inflate(R.layout.fila_calculadora, null);
                ImageButton buttonRemove = addView.findViewById(R.id.remove_new);

                EditText nota_row = addView.findViewById(R.id.textField_AñadeNota_row);
                EditText porcentaje_row = addView.findViewById(R.id.textField_AñadeTantoPorCiento_row);

                lista_notas5.add(nota_row);
                lista_porcentajes5.add(porcentaje_row);


                final View.OnClickListener thisListener = new View.OnClickListener(){
                    @Override
                    public void onClick(View v) {
                        ((LinearLayout)addView.getParent()).removeView(addView);
                        lista_notas5.remove(nota_row);
                        lista_porcentajes5.remove(porcentaje_row);

                    }
                };

                buttonRemove.setOnClickListener(thisListener);
                container5.addView(addView);
            }
        });

        remove_campo4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                campo4.setVisibility(View.GONE);
                lista_final_porcentajes.remove(porcentaje_campo4);
            }
        });

        boton_calculo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                lista_notas_general = new ArrayList<>();
                lista_porcentajes_general = new ArrayList<>();
                lista_notas_general.add(lista_notas1);
                lista_porcentajes_general.add(lista_porcentajes1);

                //condicional para visibilidad de campos
                if(campo1.getVisibility()!=View.GONE){
                    lista_notas_general.add(lista_notas2);
                    lista_porcentajes_general.add(lista_porcentajes2);
                } if(campo2.getVisibility()!=View.GONE){
                    lista_notas_general.add(lista_notas3);
                    lista_porcentajes_general.add(lista_porcentajes3);
                } if(campo3.getVisibility()!=View.GONE){
                    lista_notas_general.add(lista_notas4);
                    lista_porcentajes_general.add(lista_porcentajes4);
                } if(campo4.getVisibility()!=View.GONE){
                    lista_notas_general.add(lista_notas5);
                    lista_porcentajes_general.add(lista_porcentajes5);
                }

                if(validar(lista_notas_general,lista_porcentajes_general,lista_final_porcentajes)){
                    nota_final.setText(calcular.calcular_notaFinal(lista_notas_general,lista_porcentajes_general,lista_final_porcentajes).toString());
                }else{
                    nota_final.setText("");
                }
            }
        });

        boton_campoNuevo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                LayoutInflater inflater = getLayoutInflater();
                View vista = inflater.inflate(R.layout.dialogo_camponuevo, (ViewGroup)view.findViewById(R.id.dialog_campoNuevo));

                EditText nombre_nuevo = (EditText) vista.findViewById(R.id.editText_nuevo);

                AlertDialog.Builder dialog = new AlertDialog.Builder(getContext());
                dialog.setView(vista).setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        String nombre = nombre_nuevo.getText().toString();

                        if(campo1.getVisibility()==View.GONE){
                            campo1.setVisibility(View.VISIBLE);
                            TextView titulo = view.findViewById(R.id.textView_Teoria2);
                            titulo.setText(nombre);
                            nota2 = (EditText) view.findViewById(R.id.textField_AñadeNota2);
                            porcentaje2 = (EditText) view.findViewById(R.id.textField_AñadeTantoPorCiento2);
                            porcentaje_campo1 = (EditText) view.findViewById(R.id.textField_AñadeTantoPorCiento002);
                            lista_notas2= new ArrayList<>();
                            lista_porcentajes2 = new ArrayList<>();
                            lista_notas2.add(nota2);
                            lista_porcentajes2.add(porcentaje2);
                            lista_final_porcentajes.add(porcentaje_campo1);


                        }else if (campo2.getVisibility()==View.GONE){
                            TextView titulo = view.findViewById(R.id.textView_Teoria3);
                            titulo.setText(nombre);
                            campo2.setVisibility(View.VISIBLE);
                            nota3 = (EditText) view.findViewById(R.id.textField_AñadeNota3);
                            porcentaje3 = (EditText) view.findViewById(R.id.textField_AñadeTantoPorCiento3);
                            porcentaje_campo2 = (EditText) view.findViewById(R.id.textField_AñadeTantoPorCiento003);
                            lista_notas3= new ArrayList<>();
                            lista_porcentajes3 = new ArrayList<>();
                            lista_notas3.add(nota3);
                            lista_porcentajes3.add(porcentaje3);
                            lista_final_porcentajes.add(porcentaje_campo2);
                        }
                        else if(campo3.getVisibility()==View.GONE){
                            TextView titulo = view.findViewById(R.id.textView_Teoria4);
                            titulo.setText(nombre);
                            campo3.setVisibility(View.VISIBLE);
                            nota4 = (EditText) view.findViewById(R.id.textField_AñadeNota4);
                            porcentaje4 = (EditText) view.findViewById(R.id.textField_AñadeTantoPorCiento4);
                            porcentaje_campo3 = (EditText) view.findViewById(R.id.textField_AñadeTantoPorCiento004);
                            lista_notas4= new ArrayList<>();
                            lista_porcentajes4 = new ArrayList<>();
                            lista_notas4.add(nota4);
                            lista_porcentajes4.add(porcentaje4);
                            lista_final_porcentajes.add(porcentaje_campo3);

                        }
                        else if(campo4.getVisibility()==View.GONE){
                            TextView titulo = view.findViewById(R.id.textView_Teoria5);
                            titulo.setText(nombre);
                            campo4.setVisibility(View.VISIBLE);
                            nota5 = (EditText) view.findViewById(R.id.textField_AñadeNota5);
                            porcentaje5 = (EditText) view.findViewById(R.id.textField_AñadeTantoPorCiento5);
                            porcentaje_campo4 = (EditText) view.findViewById(R.id.textField_AñadeTantoPorCiento005);
                            lista_notas5= new ArrayList<>();
                            lista_porcentajes5 = new ArrayList<>();
                            lista_notas5.add(nota5);
                            lista_porcentajes5.add(porcentaje5);
                            lista_final_porcentajes.add(porcentaje_campo4);

                        }else{
                            maxCampos();
                        }

                    }
                }).setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                });

                dialog.show();


            }
        });
        return view;
    }

    public void maxCampos(){
        Toast.makeText(getContext(), "Se ha alcanzado el máximo de campos permitidos", Toast.LENGTH_SHORT).show();
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
                Toast.makeText(getContext(), "Porcentajes incorrectos, el total es distinto de 100 en el campo "+i, Toast.LENGTH_SHORT).show();
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
            Toast.makeText(getContext(), "Porcentajes incorrectos, el total es distinto de 100", Toast.LENGTH_SHORT).show();
        }

        return retorno;
    }
}