package com.example.zeitplan_proyect.presenter;

import android.content.Context;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import com.example.zeitplan_proyect.model.Asignatura;
import com.example.zeitplan_proyect.model.Fechas;
import com.example.zeitplan_proyect.model.ListaAsignatura;
import com.example.zeitplan_proyect.DataBase.ListaAsignaturaAdapter;
import com.example.zeitplan_proyect.model.ValidacionAsignatura;

import java.util.ArrayList;

public class PresenterAsignatura{

    Fechas fechas;
    ValidacionAsignatura validacionAsignatura;
    Context context;
    ListaAsignatura listaAsignatura;

    public PresenterAsignatura(Context context) {
        this.fechas = new Fechas();
        this.validacionAsignatura = new ValidacionAsignatura();
        this.context = context;
        this.listaAsignatura = new ListaAsignatura(context);
    }

    public void EventChangedListener(){
        listaAsignatura.EventChangedListener();
    }

    public ListaAsignaturaAdapter getListaAsignaturaAdapter() {
        return listaAsignatura.getListaAsignaturaAdapter();
    }

    public ArrayList<Asignatura> getAsignaturaArrayList() {
        return listaAsignatura.getAsignaturaArrayList();
    }

    public int validarAs(ArrayList<TextView> inicios, ArrayList<TextView> finales, ArrayList<CheckBox> cajas, EditText campo1, TextView campo2, TextView campo3) {
        return validacionAsignatura.validarAs(inicios, finales, cajas, campo1, campo2, campo3);
    }

    public boolean validarNombre(String nombre){
        //return validacionAsignatura.equalsAsignatura(nombre, this.getListaAsignaturas());
        return false;
    }

    public void crearAsignatura(String fecha_inicio, String fecha_final, String nombre_as, String descripcion,
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

        Asignatura asignatura = new Asignatura(fecha_inicio,fecha_final,nombre_as,descripcion,diasSemana,horasInicio,horasFinal,mContext);
        asignatura.addFireBaseAsignatura(fecha_inicio,fecha_final,nombre_as,descripcion,diasSemana,horasInicio,horasFinal);

    }

    public void eliminarAsignatura(Asignatura asignatura){
        //asignatura.deleteFireBaseAsignatura(asignatura);
    }

    public void fechasAsignaturas(String fechaIn, String fechaFin){
        fechas.FechasDiaSemana(fechaIn,fechaFin);
    }

}


