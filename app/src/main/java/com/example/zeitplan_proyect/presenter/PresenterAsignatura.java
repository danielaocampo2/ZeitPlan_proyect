package com.example.zeitplan_proyect.presenter;

import android.content.Context;
import android.util.Log;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.example.zeitplan_proyect.DataBase.Firebase;
import com.example.zeitplan_proyect.model.Asignatura;
import com.example.zeitplan_proyect.model.Fechas;
import com.example.zeitplan_proyect.model.ValidacionAsignatura;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;

import java.util.ArrayList;
import java.util.Date;

public class PresenterAsignatura {

    Fechas fechas;
    ValidacionAsignatura validacionAsignatura;
    ArrayList<Asignatura> listaAsignaturas;
    Firebase bd = new Firebase();


    public PresenterAsignatura() {
        fechas = new Fechas();
        validacionAsignatura = new ValidacionAsignatura();
        listaAsignaturas = new ArrayList<>();
    }

    public int validarAs(ArrayList<TextView> inicios, ArrayList<TextView> finales, ArrayList<CheckBox> cajas, EditText campo1, TextView campo2, TextView campo3) {
        return validacionAsignatura.validarAs(inicios, finales, cajas, campo1, campo2, campo3);
    }

    public boolean validarNombre(String nombre){
        return validacionAsignatura.equalsAsignatura(nombre, this.getListaAsignaturas());
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
        this.addAsignatura(asignatura);
        asignatura.addFireBaseAsignatura(fecha_inicio,fecha_final,nombre_as,descripcion,diasSemana,horasInicio,horasFinal);
    }

    public void fechasAsignaturas(String fechaIn, String fechaFin){
        fechas.FechasDiaSemana(fechaIn,fechaFin);
    }

    public ArrayList<Asignatura> getListaAsignaturas() {
        return listaAsignaturas;
    }

    public void setListaAsignaturas(ArrayList<Asignatura> listaAsignaturas) {
        this.listaAsignaturas = listaAsignaturas;
    }

    public void addAsignatura(Asignatura asignatura){
        this.getListaAsignaturas().add(asignatura);
    }

    public void getUserAsignaturas(){

        bd.mFirestore.collection("users").document(bd.getIdUser().toString()).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if (task.isSuccessful()) {
                            Log.e("TAG", "hola");
                            DocumentSnapshot document = task.getResult();
                            if (document.exists()) {
                                Log.e("TAG", document.getId() + " => " + document.get("userCourses"));
                                ArrayList<String> acc = (ArrayList<String>) document.get("userCourses");
                                //getUserObjectCourses(acc);
                            }
                        }

                    }

                });
    }






}
