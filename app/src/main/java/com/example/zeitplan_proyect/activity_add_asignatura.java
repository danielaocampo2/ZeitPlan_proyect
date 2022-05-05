package com.example.zeitplan_proyect;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.app.TimePickerDialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;

import java.util.Calendar;

public class activity_add_asignatura extends AppCompatActivity {

    EditText campo1;
    TimePickerDialog.OnTimeSetListener setListenerTimeLunes,setListenerTimeMartes,setListenerTimeMiercoles,setListenerTimeJueves,setListenerTimeViernes,setListenerTimeLunesFinal,setListenerTimeMartesFinal,setListenerTimeMiercolesFinal,setListenerTimeJuevesFinal,setListenerTimeViernesFinal ;
    TextView inicio_lunes, inicio_martes, inicio_miercoles, inicio_jueves, inicio_viernes,
            final_lunes, final_martes, final_miercoles, final_jueves, final_viernes;
    CheckBox checkBox_lunes, checkBox_martes, checkBox_miercoles, checkBox_jueves, checkBox_viernes;
    Spinner spinner;
    NavigationView navigationView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        final View view = inflater.inflate(R.layout.activity_add_asignatura, container, false);
        ((MainActivity2) getActivity()).getSupportActionBar().setTitle("Añadir Asignatura");
        FloatingActionButton shareBtn =  ((MainActivity2) getActivity()).findViewById(R.id.share);
        spinner = (Spinner) view.findViewById(R.id.spinner_descripcion);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getContext(), R.array.TipoDescripcion, android.R.layout.simple_spinner_item);
        spinner.setAdapter(adapter);

        campo1 = view.findViewById(R.id.editTextNombreAsignatura);

        inicio_lunes = view.findViewById(R.id.textView_horarioLunesInicio);
        inicio_martes = view.findViewById(R.id.textView_horarioMartesInicio);
        inicio_miercoles = view.findViewById(R.id.textView_horarioMiercolesInicio);
        inicio_jueves = view.findViewById(R.id.textView_horarioJuevesInicio);
        inicio_viernes = view.findViewById(R.id.textView_horarioViernesInicio);


        final_lunes = view.findViewById(R.id.textView_horarioLunesFinal);
        final_martes = view.findViewById(R.id.textView_horarioMartesFinal);
        final_miercoles = view.findViewById(R.id.textView_horarioMiercolesFinal);
        final_jueves = view.findViewById(R.id.textView_horarioJuevesFinal);
        final_viernes = view.findViewById(R.id.textView_horarioViernesFinal);

        checkBox_lunes = view.findViewById(R.id.checkBox_Lunes);
        checkBox_martes = view.findViewById(R.id.checkBox_Martes);
        checkBox_miercoles = view.findViewById(R.id.checkBox_Miercoles);
        checkBox_jueves = view.findViewById(R.id.checkBox_Jueves);
        checkBox_viernes = view.findViewById(R.id.checkBox_Viernes);


        Calendar calendar = Calendar.getInstance();
        final int hour = calendar.get(Calendar.HOUR_OF_DAY);
        final int minute = calendar.get(Calendar.MINUTE);

        navigationView = ((MainActivity2) getActivity()).getNavigationView();

        ((MainActivity2) getActivity()).setupNavigationDrawerContent(navigationView);
        //Hide share button
        shareBtn.setVisibility(View.GONE);

        //Lunes
        inicio_lunes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(checkBox_lunes.isChecked()){
                    TimePickerDialog timePicker = new TimePickerDialog(
                            getContext(), android.R.style.Theme_Holo_Light_Dialog_MinWidth, setListenerTimeLunes, hour, minute, true);
                    timePicker.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                    timePicker.show();
                }else{
                    inicio_lunes.setText("");
                }

            }
        });
        setListenerTimeLunes = new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int i, int e) {
                String hour_time = timePicker.getHour() + ":" + timePicker.getMinute();
                inicio_lunes.setText(hour_time);

            }
        };
        final_lunes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(checkBox_lunes.isChecked()){
                    TimePickerDialog timePicker = new TimePickerDialog(
                            getContext(), android.R.style.Theme_Holo_Light_Dialog_MinWidth, setListenerTimeLunesFinal, hour, minute, true);
                    timePicker.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                    timePicker.show();
                }else{
                    final_lunes.setText("");
                }

            }
        });
        setListenerTimeLunesFinal = new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int i, int e) {
                String hour_time = timePicker.getHour() + ":" + timePicker.getMinute();
                final_lunes.setText(hour_time);

            }
        };

        //Martes
        inicio_martes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(checkBox_martes.isChecked()){
                    TimePickerDialog timePicker = new TimePickerDialog(
                            getContext(), android.R.style.Theme_Holo_Light_Dialog_MinWidth, setListenerTimeMartes, hour, minute, true);
                    timePicker.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                    timePicker.show();
                }else{
                    inicio_martes.setText("");
                }

            }
        });
        setListenerTimeMartes = new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int i, int e) {
                String hour_time = timePicker.getHour() + ":" + timePicker.getMinute();
                inicio_martes.setText(hour_time);

            }
        };
        final_martes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(checkBox_martes.isChecked()){
                    TimePickerDialog timePicker = new TimePickerDialog(
                            getContext(), android.R.style.Theme_Holo_Light_Dialog_MinWidth, setListenerTimeMartesFinal, hour, minute, true);
                    timePicker.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                    timePicker.show();
                }else{
                    final_martes.setText("");
                }

            }
        });
        setListenerTimeMartesFinal = new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int i, int e) {
                String hour_time = timePicker.getHour() + ":" + timePicker.getMinute();
                final_martes.setText(hour_time);

            }
        };

        //Miercoles
        inicio_miercoles.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(checkBox_miercoles.isChecked()){
                    TimePickerDialog timePicker = new TimePickerDialog(
                            getContext(), android.R.style.Theme_Holo_Light_Dialog_MinWidth, setListenerTimeMiercoles, hour, minute, true);
                    timePicker.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                    timePicker.show();
                }else{
                    inicio_miercoles.setText("");
                }

            }
        });
        setListenerTimeMiercoles = new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int i, int e) {
                String hour_time = timePicker.getHour() + ":" + timePicker.getMinute();
                inicio_miercoles.setText(hour_time);

            }
        };
        final_miercoles.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(checkBox_miercoles.isChecked()){
                    TimePickerDialog timePicker = new TimePickerDialog(
                            getContext(), android.R.style.Theme_Holo_Light_Dialog_MinWidth, setListenerTimeMiercolesFinal, hour, minute, true);
                    timePicker.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                    timePicker.show();
                }else{
                    final_miercoles.setText("");
                }

            }
        });
        setListenerTimeMiercolesFinal = new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int i, int e) {
                String hour_time = timePicker.getHour() + ":" + timePicker.getMinute();
                final_miercoles.setText(hour_time);

            }
        };

        //Jueves
        inicio_jueves.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(checkBox_jueves.isChecked()){
                    TimePickerDialog timePicker = new TimePickerDialog(
                            getContext(), android.R.style.Theme_Holo_Light_Dialog_MinWidth, setListenerTimeJueves, hour, minute, true);
                    timePicker.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                    timePicker.show();
                }else{
                    inicio_jueves.setText("");
                }

            }
        });
        setListenerTimeJueves = new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int i, int e) {
                String hour_time = timePicker.getHour() + ":" + timePicker.getMinute();
                inicio_jueves.setText(hour_time);

            }
        };
        final_jueves.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(checkBox_jueves.isChecked()){
                    TimePickerDialog timePicker = new TimePickerDialog(
                            getContext(), android.R.style.Theme_Holo_Light_Dialog_MinWidth, setListenerTimeJuevesFinal, hour, minute, true);
                    timePicker.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                    timePicker.show();
                }else{
                    final_jueves.setText("");
                }

            }
        });
        setListenerTimeJuevesFinal = new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int i, int e) {
                String hour_time = timePicker.getHour() + ":" + timePicker.getMinute();
                final_jueves.setText(hour_time);

            }
        };

        //Viernes
        inicio_viernes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(checkBox_viernes.isChecked()){
                    TimePickerDialog timePicker = new TimePickerDialog(
                            getContext(), android.R.style.Theme_Holo_Light_Dialog_MinWidth, setListenerTimeViernes, hour, minute, true);
                    timePicker.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                    timePicker.show();
                }else{
                    inicio_viernes.setText("");
                }

            }
        });
        setListenerTimeViernes = new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int i, int e) {
                String hour_time = timePicker.getHour() + ":" + timePicker.getMinute();
                inicio_viernes.setText(hour_time);

            }
        };
        final_viernes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(checkBox_viernes.isChecked()){
                    TimePickerDialog timePicker = new TimePickerDialog(
                            getContext(), android.R.style.Theme_Holo_Light_Dialog_MinWidth, setListenerTimeViernesFinal, hour, minute, true);
                    timePicker.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                    timePicker.show();
                }else{
                    final_viernes.setText("");
                }

            }
        });
        setListenerTimeViernesFinal = new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int i, int e) {
                String hour_time = timePicker.getHour() + ":" + timePicker.getMinute();
                final_viernes.setText(hour_time);

            }
        };
     return view;
    }

    public void agregarAs(View v){
        if(validarAs()){
            Toast.makeText(getContext(), "Datos ingresados correctamente", Toast.LENGTH_SHORT).show();
        }
    }

    public boolean validarAs(){
        boolean retorno = true;
        String c1 = campo1.getText().toString();
        if (c1.isEmpty()){
            campo1.setError("Este campo no puede quedar vacio");
            retorno = false;
        }if(checkBox_lunes.isChecked()){
            if(inicio_lunes.getText().toString().isEmpty()||final_lunes.getText().toString().isEmpty()){
                Toast.makeText(getContext(), "Los campos de hora no pueden quedar vacios", Toast.LENGTH_SHORT).show();
                retorno = false;
            }else{
                retorno = validarCampo(inicio_lunes.getText().toString(), final_lunes.getText().toString());
            }
        }if(checkBox_martes.isChecked()){
            if(inicio_martes.getText().toString().isEmpty()||final_martes.getText().toString().isEmpty()){
                Toast.makeText(getContext(), "Los campos de hora no pueden quedar vacios", Toast.LENGTH_SHORT).show();
                retorno = false;
            }else{
                retorno = validarCampo(inicio_martes.getText().toString(), final_martes.getText().toString());
            }
        }if(checkBox_miercoles.isChecked()){
            if(inicio_miercoles.getText().toString().isEmpty()||final_miercoles.getText().toString().isEmpty()){
                Toast.makeText(getContext(), "Los campos de hora no pueden quedar vacios", Toast.LENGTH_SHORT).show();
                retorno = false;
            }else{
                retorno = validarCampo(inicio_miercoles.getText().toString(), final_miercoles.getText().toString());
            }
        }if(checkBox_jueves.isChecked()){
            if(inicio_jueves.getText().toString().isEmpty()||final_jueves.getText().toString().isEmpty()){
                Toast.makeText(getContext(), "Los campos de hora no pueden quedar vacios", Toast.LENGTH_SHORT).show();
                retorno = false;
            }else{
                retorno = validarCampo(inicio_jueves.getText().toString(), final_jueves.getText().toString());
            }
        }if(checkBox_viernes.isChecked()){
            if(inicio_viernes.getText().toString().isEmpty()||final_viernes.getText().toString().isEmpty()){
                Toast.makeText(getContext(), "Los campos de hora no pueden quedar vacios", Toast.LENGTH_SHORT).show();
                retorno = false;
            }else{
                retorno = validarCampo(inicio_viernes.getText().toString(), final_viernes.getText().toString());
            }
        }
        return retorno;
    }

    public boolean validarCampo(String inicio, String fin){
        boolean retorno = true;

        int hora_inicio = Integer.parseInt(inicio.substring(0,2));
        int hora_final = Integer.parseInt(fin.substring(0,2));

        int min_inicio = Integer.parseInt(inicio.substring(3,inicio.length()));
        int min_final = Integer.parseInt(fin.substring(3,fin.length()));

        if (hora_inicio > hora_final) {
            Toast.makeText(getContext(), "La hora de inicio debe ser menor a la de final", Toast.LENGTH_SHORT).show();
            retorno = false;
        } else if (hora_inicio == hora_final) {
                if (min_inicio > min_final) {
                    Toast.makeText(getContext(), "La hora de inicio debe ser menor a la de final", Toast.LENGTH_SHORT).show();
                    retorno = false;
                }
        }

        return retorno;
    }
}