package com.example.zeitplan_proyect.vista;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.zeitplan_proyect.MainActivity2;
import com.example.zeitplan_proyect.R;
import com.example.zeitplan_proyect.model.Fechas;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class ActivityAsignatura extends Fragment {

    FloatingActionButton boton_add;


    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        final View view = inflater.inflate(R.layout.activity_asignatura, container, false);
        ((MainActivity2) getActivity()).getSupportActionBar().setTitle("Asignatura");
        FloatingActionButton shareBtn =  ((MainActivity2) getActivity()).findViewById(R.id.share);

        boton_add = view.findViewById(R.id.btn_addAsignatura);


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



        return view;
    }

}