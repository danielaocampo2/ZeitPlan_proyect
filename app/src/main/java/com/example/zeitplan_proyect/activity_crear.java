package com.example.zeitplan_proyect;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;

import java.util.Calendar;

public class activity_crear extends Fragment {
    EditText campo1, campo2;
    EditText campoFecha, campoHora;
    DatePickerDialog.OnDateSetListener setListener;
    TimePickerDialog.OnTimeSetListener setListenerTime;
    Spinner spinner;
    SeekBar seekBar;
    TextView resultado_seekBar;
    CheckBox recuerdame_check;
    NavigationView navigationView;
    Button acpetar;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        final View view = inflater.inflate(R.layout.activity_crear, container, false);
        ((MainActivity2) getActivity()).getSupportActionBar().setTitle("Añadir Actividad");
        FloatingActionButton shareBtn =  ((MainActivity2) getActivity()).findViewById(R.id.share);
        spinner = (Spinner) view.findViewById(R.id.spinner_tipos);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getContext(), R.array.TipoEventos, android.R.layout.simple_spinner_item);
        spinner.setAdapter(adapter);
        acpetar = view.findViewById(R.id.button_aceptar);

        campo1 = (EditText) view.findViewById(R.id.editText_Titulo);
        campo2 = (EditText) view.findViewById(R.id.editText_descripcion);

        campoFecha = view.findViewById(R.id.editTextDate);
        campoHora = view.findViewById(R.id.editTextHour);

        resultado_seekBar = view.findViewById(R.id.textView_resultadoPrioridad);
        seekBar = view.findViewById(R.id.seekBar_prioridad);
        seekBar.setProgress(0); //valor inicial
        seekBar.setMax(100); //valor final

        recuerdame_check = view.findViewById(R.id.checkBox_alarma);

        navigationView = ((MainActivity2) getActivity()).getNavigationView();

        ((MainActivity2) getActivity()).setupNavigationDrawerContent(navigationView);

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
                        getContext(), android.R.style.Theme_Holo_Light_Dialog_MinWidth,setListenerTime,hour,minute,true);
                timePicker.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                timePicker.show();
                campoHora.setText("");
            }
        });
        setListenerTime = new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int i, int i1) {
                String hour_time = timePicker.getHour()+":"+timePicker.getMinute();
                campoHora.setText(hour_time);
            }
        };

        campoFecha.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(
                        getContext(), android.R.style.Theme_Holo_Light_Dialog_MinWidth
                        ,setListener,year,month,day);
                datePickerDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                datePickerDialog.show();
                datePickerDialog.getDatePicker();


            }
        });
        setListener = new DatePickerDialog.OnDateSetListener(){
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                String date = view.getMonth()+"/"+view.getDayOfMonth()+"/"+view.getYear();
                campoFecha.setText(date);
            }
        };

        acpetar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                agregar(view);
            }
        });

        //Hide share button
        shareBtn.setVisibility(View.GONE);
    return view;
    }

    //Falta que vuelva el boton a la ultima vista visitada

    public void agregar(View v){
        if(validar()){
            Toast.makeText(getContext(), "Datos ingresados correctamente", Toast.LENGTH_SHORT).show();
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
        }
        if (c3.isEmpty()){
            campoHora.setError("Este campo no puede quedar vacio");
            retorno = false;
        }
        return retorno;
    }


}