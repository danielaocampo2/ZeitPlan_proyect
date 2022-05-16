package com.example.zeitplan_proyect.Fragments;

import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;


import com.example.zeitplan_proyect.Activity_add_asignatura;
import com.example.zeitplan_proyect.CalculadoraActivity;
import com.example.zeitplan_proyect.vista.NoteActivity;
import com.example.zeitplan_proyect.R;
import com.example.zeitplan_proyect.vista.Activity_crear;
import com.example.zeitplan_proyect.vista.CalendarActivity;

/**
 * Clase para cambiar de fragmentos con el menu desplegable.
 */

public class FragmentHelper extends FragmentActivity {
    private static final int CALENDARIO = 0;
    private static final int AÑADIR_ACTIVIDAD = 1;
    private static final int AÑADIR_ASIGNATURA = 2;
    private static final int NOTAS = 3;
    private static final int SING_OUT = 4;
    private static final int CALCULADORA = 5;
    private final FragmentManager fragmentManager;
    private final FragmentActivity fragmentActivity;

    public FragmentHelper(FragmentActivity fragmentActivity){
        this.fragmentActivity = fragmentActivity;
        fragmentManager = fragmentActivity.getSupportFragmentManager();

    }

    public void setFragment(int position) {
        FragmentTransaction fragmentTransaction;
        switch (position) {
            case CALENDARIO:
                fragmentTransaction = fragmentManager.beginTransaction();
                CalendarActivity calendar = new CalendarActivity();
                fragmentTransaction.replace(R.id.fragment, calendar);
                fragmentTransaction.commit();
                break;
            case AÑADIR_ACTIVIDAD:

                fragmentTransaction = fragmentManager.beginTransaction();
                Activity_add_asignatura activity_add_asignatura = new Activity_add_asignatura();
                fragmentTransaction.replace(R.id.fragment, activity_add_asignatura);
                fragmentTransaction.commit();
                break;
            case AÑADIR_ASIGNATURA:

                fragmentTransaction = fragmentManager.beginTransaction();
                Activity_crear crear_activity = new Activity_crear();
                fragmentTransaction.replace(R.id.fragment, crear_activity);
                fragmentTransaction.commit();
                break;
            case NOTAS:

                fragmentTransaction = fragmentManager.beginTransaction();
                NoteActivity noteActivity = new NoteActivity();
                fragmentTransaction.replace(R.id.fragment, noteActivity);
                fragmentTransaction.commit();
                break;
            case CALCULADORA:

                fragmentTransaction = fragmentManager.beginTransaction();
                CalculadoraActivity calculadoraActivity = new CalculadoraActivity();
                fragmentTransaction.replace(R.id.fragment, calculadoraActivity);
                fragmentTransaction.commit();
                break;

        }
    }
}
