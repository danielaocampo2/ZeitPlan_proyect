package com.example.zeitplan_proyect;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.Calendar;

public class CrearEventoActivity extends AppCompatActivity {


    EditText campo1, campo2, campoFecha, campoHora;
    DatePickerDialog.OnDateSetListener setListener;
    TimePickerDialog.OnTimeSetListener setListenerTime;
    Spinner spinner;
    SeekBar seekBar;
    TextView resultado_seekBar;
    CheckBox recuerdame_check;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        spinner = (Spinner) findViewById(R.id.spinner_tipos);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.TipoEventos, android.R.layout.simple_spinner_item);
        spinner.setAdapter(adapter);

        campo1 = (EditText)findViewById(R.id.editText_Titulo);
        campo2 = (EditText) findViewById(R.id.editText_descripcion);

        campoFecha = findViewById(R.id.editTextDate);
        campoHora = findViewById(R.id.editTextHour);

        resultado_seekBar = findViewById(R.id.textView_resultadoPrioridad);
        seekBar = findViewById(R.id.seekBar_prioridad);
        seekBar.setProgress(0); //valor inicial
        seekBar.setMax(100); //valor final

        recuerdame_check = findViewById(R.id.checkBox_alarma);

        recuerdame_check.setOnCheckedChangeListener(
                new CheckBox.OnCheckedChangeListener(){

                    @Override
                    public void onCheckedChanged(CompoundButton compoundButton, boolean apretado) {
                        if (apretado==true){
                            //crear alarma

                        }
                    }
                }
        );

        seekBar.setOnSeekBarChangeListener(
                new SeekBar.OnSeekBarChangeListener() {
                    @Override
                    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                        resultado_seekBar.setText(String.valueOf(progress)+"%");

                    }

                    @Override
                    public void onStartTrackingTouch(SeekBar seekBar) {

                    }

                    @Override
                    public void onStopTrackingTouch(SeekBar seekBar) {

                    }
                }
        );


        Calendar calendar = Calendar.getInstance();
        final int year = calendar.get(Calendar.YEAR);
        final int month = calendar.get(Calendar.MONTH);
        final int day = calendar.get(Calendar.DAY_OF_MONTH);

        final int hour = calendar.get(Calendar.HOUR_OF_DAY);
        final int minute = calendar.get(Calendar.MINUTE);

        campoHora.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TimePickerDialog timePicker = new TimePickerDialog(
                        CrearEventoActivity.this, android.R.style.Theme_Holo_Light_Dialog_MinWidth,setListenerTime,hour,minute,true);
                timePicker.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                timePicker.show();
            }
        });
        setListenerTime = new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int i, int i1) {
                String hour_time = hour+":"+minute;
                campoHora.setText(hour_time);

            }
        };

        campoFecha.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(
                        CrearEventoActivity.this, android.R.style.Theme_Holo_Light_Dialog_MinWidth
                        ,setListener,year,month,day);
                datePickerDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                datePickerDialog.show();


            }
        });
        setListener = new DatePickerDialog.OnDateSetListener(){


            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                month = month+1;
                String date = day+"/"+month+"/"+year;
                campoFecha.setText(date);

            }
        };


    }

    //Falta que vuelva el boton a la ultima vista visitada
    public void agregar(View v){

        if(validar()){
            Toast.makeText(this, "Datos ingresados correctamente", Toast.LENGTH_SHORT).show();
        }

    }

    public boolean validar(){
        boolean retorno = true;
        String c1 = campo1.getText().toString(), c2 = campoFecha.getText().toString(), c3 = campoHora.getText().toString();
        if (c1.isEmpty()){
            campo1.setError("Este campo no puede quedar vacio");
            retorno = false;
        } if (c2.isEmpty()){
            campoFecha.setError("Este campo no puede quedar vacio");
            retorno = false;
        } else{
            campoFecha.setText("");
        }
        if (c3.isEmpty()){
            campoHora.setError("Este campo no puede quedar vacio");
            retorno = false;
        }else{
            campoFecha.setText("");
        }
        return retorno;
    }



}