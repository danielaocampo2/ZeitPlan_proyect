package com.example.zeitplan_proyect;

import androidx.appcompat.app.AppCompatActivity;

import android.app.TimePickerDialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.Calendar;

public class activity_add_asignatura extends AppCompatActivity {

    EditText campo1;
    TimePickerDialog.OnTimeSetListener setListenerTimeLunes,setListenerTimeMartes,setListenerTimeMiercoles,setListenerTimeJueves,setListenerTimeViernes,setListenerTimeLunesFinal,setListenerTimeMartesFinal,setListenerTimeMiercolesFinal,setListenerTimeJuevesFinal,setListenerTimeViernesFinal ;
    TextView inicio_lunes, inicio_martes, inicio_miercoles, inicio_jueves, inicio_viernes,
            final_lunes, final_martes, final_miercoles, final_jueves, final_viernes;
    CheckBox checkBox_lunes, checkBox_martes, checkBox_miercoles, checkBox_jueves, checkBox_viernes;
    Spinner spinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_asignatura);
        spinner = (Spinner) findViewById(R.id.spinner_descripcion);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.TipoDescripcion, android.R.layout.simple_spinner_item);
        spinner.setAdapter(adapter);

        campo1 = findViewById(R.id.editTextNombreAsignatura);

        inicio_lunes = findViewById(R.id.textView_horarioLunesInicio);
        inicio_martes = findViewById(R.id.textView_horarioMartesInicio);
        inicio_miercoles = findViewById(R.id.textView_horarioMiercolesInicio);
        inicio_jueves = findViewById(R.id.textView_horarioJuevesInicio);
        inicio_viernes = findViewById(R.id.textView_horarioViernesInicio);


        final_lunes = findViewById(R.id.textView_horarioLunesFinal);
        final_martes = findViewById(R.id.textView_horarioMartesFinal);
        final_miercoles = findViewById(R.id.textView_horarioMiercolesFinal);
        final_jueves = findViewById(R.id.textView_horarioJuevesFinal);
        final_viernes = findViewById(R.id.textView_horarioViernesFinal);

        checkBox_lunes = findViewById(R.id.checkBox_Lunes);
        checkBox_martes = findViewById(R.id.checkBox_Martes);
        checkBox_miercoles = findViewById(R.id.checkBox_Miercoles);
        checkBox_jueves = findViewById(R.id.checkBox_Jueves);
        checkBox_viernes = findViewById(R.id.checkBox_Viernes);


        Calendar calendar = Calendar.getInstance();
        final int hour = calendar.get(Calendar.HOUR_OF_DAY);
        final int minute = calendar.get(Calendar.MINUTE);

        //Lunes
        inicio_lunes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(checkBox_lunes.isChecked()){
                    TimePickerDialog timePicker = new TimePickerDialog(
                            activity_add_asignatura.this, android.R.style.Theme_Holo_Light_Dialog_MinWidth, setListenerTimeLunes, hour, minute, true);
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
                            activity_add_asignatura.this, android.R.style.Theme_Holo_Light_Dialog_MinWidth, setListenerTimeLunesFinal, hour, minute, true);
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
                            activity_add_asignatura.this, android.R.style.Theme_Holo_Light_Dialog_MinWidth, setListenerTimeMartes, hour, minute, true);
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
                            activity_add_asignatura.this, android.R.style.Theme_Holo_Light_Dialog_MinWidth, setListenerTimeMartesFinal, hour, minute, true);
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
                            activity_add_asignatura.this, android.R.style.Theme_Holo_Light_Dialog_MinWidth, setListenerTimeMiercoles, hour, minute, true);
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
                            activity_add_asignatura.this, android.R.style.Theme_Holo_Light_Dialog_MinWidth, setListenerTimeMiercolesFinal, hour, minute, true);
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
                            activity_add_asignatura.this, android.R.style.Theme_Holo_Light_Dialog_MinWidth, setListenerTimeJueves, hour, minute, true);
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
                            activity_add_asignatura.this, android.R.style.Theme_Holo_Light_Dialog_MinWidth, setListenerTimeJuevesFinal, hour, minute, true);
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
                            activity_add_asignatura.this, android.R.style.Theme_Holo_Light_Dialog_MinWidth, setListenerTimeViernes, hour, minute, true);
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
                            activity_add_asignatura.this, android.R.style.Theme_Holo_Light_Dialog_MinWidth, setListenerTimeViernesFinal, hour, minute, true);
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

    }

    public void agregarAs(View v){
        if(validarAs()){
            Toast.makeText(this, "Datos ingresados correctamente", Toast.LENGTH_SHORT).show();
        }
    }
    public boolean validarAs(){
        boolean retorno = true;
        String c1 = campo1.getText().toString();
        if (c1.isEmpty()){
            campo1.setError("Este campo no puede quedar vacio");
            retorno = false;
        }
        return retorno;
    }
}