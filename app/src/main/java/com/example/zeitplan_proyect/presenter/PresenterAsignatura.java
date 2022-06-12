package com.example.zeitplan_proyect.presenter;

import android.content.Context;
import android.util.Log;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.zeitplan_proyect.DataBase.Firebase;
import com.example.zeitplan_proyect.model.Asignatura;
import com.example.zeitplan_proyect.model.Fechas;
import com.example.zeitplan_proyect.model.ValidacionAsignatura;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class PresenterAsignatura{

    Fechas fechas;
    ValidacionAsignatura validacionAsignatura;
    Context context;
    Firebase bd = new Firebase();

    public PresenterAsignatura(Context context) {
        this.fechas = new Fechas();
        this.validacionAsignatura = new ValidacionAsignatura();
        this.context = context;
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
        //Asignatura asignatura = new Asignatura(fecha_inicio,fecha_final,nombre_as,descripcion,diasSemana,horasInicio,horasFinal,mContext);
        //Log.e("TAG2", asignatura.getNombre());
        addFireBaseAsignatura(fecha_inicio,fecha_final,nombre_as,descripcion,diasSemana,horasInicio,horasFinal);

    }

    public void fechasAsignaturas(String fechaIn, String fechaFin){
        fechas.FechasDiaSemana(fechaIn,fechaFin);
    }

    public void addFireBaseAsignatura(String fecha_inicio, String fecha_final, String nombre_as, String descripcion, ArrayList<String> diasSemana,ArrayList<String> inicios, ArrayList<String> finales){

        Map<String, Object> map = new HashMap<>();
        map.put("idUser", bd.getIdUser());
        map.put("Fecha inicio", fecha_inicio);
        map.put("Fecha final", fecha_final);
        map.put("Dias de la semana", diasSemana);
        map.put("Horas de inicio", inicios);
        map.put("Horas de final", finales);
        map.put("Name", nombre_as);
        map.put("Descripcion", descripcion);

        bd.mFirestore.collection("Asignaturas").document(nombre_as).set(map).addOnSuccessListener(
                new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Toast.makeText(context, "Asignatura guardada correctamente", Toast.LENGTH_SHORT).show();

                    }
                }).addOnFailureListener(new OnFailureListener() { // en caso de que no entre correcto muestra un error
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(context, "Error al guardar", Toast.LENGTH_SHORT).show();

            }
        });
    }

}


