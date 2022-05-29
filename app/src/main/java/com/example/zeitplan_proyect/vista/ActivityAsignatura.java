package com.example.zeitplan_proyect.vista;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.example.zeitplan_proyect.MainActivity2;
import com.example.zeitplan_proyect.R;
import com.example.zeitplan_proyect.model.Asignatura;
import com.example.zeitplan_proyect.model.Fechas;
import com.example.zeitplan_proyect.presenter.PresenterAsignatura;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class ActivityAsignatura extends Fragment {

    FloatingActionButton boton_add;
    Button aceptar;
    LinearLayout container;

    private static PresenterAsignatura presenterAsignatura = new PresenterAsignatura();


    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        final View view = inflater.inflate(R.layout.activity_asignatura, container, false);
        ((MainActivity2) getActivity()).getSupportActionBar().setTitle("Asignatura");
        FloatingActionButton shareBtn =  ((MainActivity2) getActivity()).findViewById(R.id.share);

        container = view.findViewById(R.id.containerFilaAsignatura);
        boton_add = view.findViewById(R.id.btn_addAsignatura);
        aceptar = view.findViewById(R.id.btn_guardar);

        aceptar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        boton_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Activity_add_asignatura activity_add_asignatura = new Activity_add_asignatura();
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.fragment, activity_add_asignatura);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        });


        for(int i=0; i<presenterAsignatura.getListaAsignaturas().size(); i++){
            LayoutInflater layoutInflater = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            final View addView = layoutInflater.inflate(R.layout.fila_asignatura, null);
            EditText nom = addView.findViewById(R.id.textField_Asignatura_row);
            Log.e("Tag",presenterAsignatura.getListaAsignaturas().get(i).getNombre());
            nom.setText(presenterAsignatura.getListaAsignaturas().get(i).getNombre());
            container.addView(addView);
        }

        return view;
    }


}