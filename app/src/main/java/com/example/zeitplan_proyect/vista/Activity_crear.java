package com.example.zeitplan_proyect.vista;

import static android.content.ContentValues.TAG;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
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

import com.example.zeitplan_proyect.MainActivity2;
import com.example.zeitplan_proyect.model.CalendarUtils;
import com.example.zeitplan_proyect.model.Event;
import com.example.zeitplan_proyect.R;
import com.example.zeitplan_proyect.presenter.PresenterCrearEvent;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;

import java.time.LocalDate;
import java.time.LocalTime;

@RequiresApi(api = Build.VERSION_CODES.O)
public class Activity_crear extends Fragment {

    EditText eventNameET, eventDescrET;
    EditText eventDateTV, eventTimeTV;
    TimePickerDialog.OnTimeSetListener setListenerTimeEvent;
    DatePickerDialog.OnDateSetListener setListenerDateEvent;

    private LocalTime time;
    private LocalDate date;
    private int prioridad;
    private boolean remember;

    PresenterCrearEvent PreCreEvent;

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
        acpetar = view.findViewById(R.id.btn_guardar);

        eventNameET = (EditText) view.findViewById(R.id.editText_Titulo);
        eventDescrET = (EditText) view.findViewById(R.id.editText_descripcion);

        eventDateTV = view.findViewById(R.id.btn_seleFecha);
        eventTimeTV = view.findViewById(R.id.btn_seleHora);

        resultado_seekBar = view.findViewById(R.id.textView_resultadoPrioridad);
        seekBar = view.findViewById(R.id.seekBar_prioridad);
        seekBar.setProgress(0); //valor inicial
        seekBar.setMax(100); //valor final

        recuerdame_check = view.findViewById(R.id.checkBox_alarma);

        navigationView = ((MainActivity2) getActivity()).getNavigationView();

        ((MainActivity2) getActivity()).setupNavigationDrawerContent(navigationView);

        PreCreEvent = new PresenterCrearEvent();

        recuerdame_check.setOnCheckedChangeListener(
                new CheckBox.OnCheckedChangeListener(){
                    @Override
                    public void onCheckedChanged(CompoundButton compoundButton, boolean apretado) {
                        remember = apretado;
                    }
                }
        );

        seekBar.setOnSeekBarChangeListener(
                new SeekBar.OnSeekBarChangeListener() {
                    @Override
                    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                        resultado_seekBar.setText(String.valueOf(progress)+"%");
                        prioridad = progress;
                    }

                    @Override
                    public void onStartTrackingTouch(SeekBar seekBar) {

                    }

                    @Override
                    public void onStopTrackingTouch(SeekBar seekBar) {

                    }
                }
        );


        time = LocalTime.now();
        eventTimeTV.setText(CalendarUtils.formattedShortTime(time));
        date = PreCreEvent.getSelectedDate();
        eventDateTV.setText(date.getDayOfMonth() + "/" + date.getMonthValue() + "/" + date.getYear());

        eventDateTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerDialog datePicker = new DatePickerDialog(
                        getContext(), android.R.style.Theme_Holo_Light_Dialog_MinWidth, setListenerDateEvent, date.getYear(), date.getMonthValue() - 1, date.getDayOfMonth());
                datePicker.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                datePicker.show();

            }
        });
        setListenerDateEvent = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                String day_month_year = datePicker.getDayOfMonth() + "/" + (datePicker.getMonth()+1) + "/" + datePicker.getYear();
                eventDateTV.setText(day_month_year);
                date = LocalDate.of(datePicker.getYear(), datePicker.getMonth() + 1, datePicker.getDayOfMonth());
            }
        };

        eventTimeTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TimePickerDialog timePicker = new TimePickerDialog(
                        getContext(), android.R.style.Theme_Holo_Light_Dialog_MinWidth, setListenerTimeEvent, time.getHour(), time.getMinute(), true);
                timePicker.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                timePicker.show();
            }
        });
        setListenerTimeEvent = new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int i, int e) {
                String hour_time = timePicker.getHour() + ":" + timePicker.getMinute();
                eventTimeTV.setText(hour_time);
                time = LocalTime.of(timePicker.getHour(), timePicker.getMinute());
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
            String eventName = eventNameET.getText().toString();
            String eventDescription = eventDescrET.getText().toString();
            Event newEvent = new Event(eventName, eventDescription, date, time, prioridad, remember);

            Event.eventsList.add(newEvent);


            // llamar a metodo para guardar datos.
            Toast.makeText(getContext(), "Datos ingresados correctamente", Toast.LENGTH_SHORT).show();


            if(remember){
                Intent intent =new Intent(v.getContext(),Recordar.class);
                intent.putExtra("nombreEvento", eventName);
                intent.putExtra("descripcion", eventDescription);
                startActivity(intent);
            }

        }
    }

    public boolean validar(){
        boolean retorno = true;
        String c1 = eventNameET.getText().toString(), c2 = eventDateTV.getText().toString(), c3 = eventTimeTV.getText().toString();
        if (c1.isEmpty()){
            eventNameET.setError("Este campo no puede quedar vacio");
            retorno = false;
        } if (c2.isEmpty()){
            eventDateTV.setError("Este campo no puede quedar vacio");
            retorno = false;
        }
        if (c3.isEmpty()){
            eventTimeTV.setError("Este campo no puede quedar vacio");
            retorno = false;
        }
        return retorno;
    }


}