package com.example.zeitplan_proyect.vista;

import androidx.core.util.Pair;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.app.TimePickerDialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.zeitplan_proyect.MainActivity2;
import com.example.zeitplan_proyect.R;
import com.example.zeitplan_proyect.model.Asignatura;
import com.example.zeitplan_proyect.model.EventoGeneral;
import com.example.zeitplan_proyect.model.ValidacionAsignatura;
import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.datepicker.MaterialPickerOnPositiveButtonClickListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;
import java.util.Calendar;

public class Activity_add_asignatura extends Fragment {


    EditText campo1, date_inicio_fin;
    TimePickerDialog.OnTimeSetListener setListenerTimeLunes,setListenerTimeMartes,setListenerTimeMiercoles,setListenerTimeJueves,setListenerTimeViernes,setListenerTimeLunesFinal,setListenerTimeMartesFinal,setListenerTimeMiercolesFinal,setListenerTimeJuevesFinal,setListenerTimeViernesFinal ;
    TextView inicio_lunes, inicio_martes, inicio_miercoles, inicio_jueves, inicio_viernes,
            final_lunes, final_martes, final_miercoles, final_jueves, final_viernes;
    CheckBox checkBox_lunes, checkBox_martes, checkBox_miercoles, checkBox_jueves, checkBox_viernes;
    Spinner spinner;
    NavigationView navigationView;
    Button guardar, cancelar;

    ArrayList<TextView> lista_inicios, lista_finales;
    ArrayList<CheckBox> lista_checkbox;

    ValidacionAsignatura validacionAsignatura;




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        final View view = inflater.inflate(R.layout.activity_add_asignatura, container, false);
        ((MainActivity2) getActivity()).getSupportActionBar().setTitle("Añadir Asignatura");
        FloatingActionButton shareBtn =  ((MainActivity2) getActivity()).findViewById(R.id.share);

        MaterialDatePicker.Builder<Pair<Long, Long>> builder = MaterialDatePicker.Builder.dateRangePicker();
        builder.setTitleText("SELECT A DATE");
        MaterialDatePicker materialDatePicker = builder.build();

        date_inicio_fin = view.findViewById(R.id.EditText_datepicker_inicio);

        spinner = (Spinner) view.findViewById(R.id.spinner_descripcion);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getContext(), R.array.TipoDescripcion, android.R.layout.simple_spinner_item);
        spinner.setAdapter(adapter);

        guardar = view.findViewById(R.id.Guardar);
        cancelar = view.findViewById(R.id.Cancelar);
        campo1 = view.findViewById(R.id.editTextNombreAsignatura);

        lista_inicios = new ArrayList<>();
        lista_finales = new ArrayList<>();
        lista_checkbox = new ArrayList<>();

        inicio_lunes = view.findViewById(R.id.textView_horarioLunesInicio);
        lista_inicios.add(inicio_lunes);
        inicio_martes = view.findViewById(R.id.textView_horarioMartesInicio);
        lista_inicios.add(inicio_martes);
        inicio_miercoles = view.findViewById(R.id.textView_horarioMiercolesInicio);
        lista_inicios.add(inicio_miercoles);
        inicio_jueves = view.findViewById(R.id.textView_horarioJuevesInicio);
        lista_inicios.add(inicio_jueves);
        inicio_viernes = view.findViewById(R.id.textView_horarioViernesInicio);
        lista_inicios.add(inicio_viernes);


        final_lunes = view.findViewById(R.id.textView_horarioLunesFinal);
        lista_finales.add(final_lunes);
        final_martes = view.findViewById(R.id.textView_horarioMartesFinal);
        lista_finales.add(final_martes);
        final_miercoles = view.findViewById(R.id.textView_horarioMiercolesFinal);
        lista_finales.add(final_miercoles);
        final_jueves = view.findViewById(R.id.textView_horarioJuevesFinal);
        lista_finales.add(final_jueves);
        final_viernes = view.findViewById(R.id.textView_horarioViernesFinal);
        lista_finales.add(final_viernes);

        checkBox_lunes = view.findViewById(R.id.checkBox_Lunes);
        lista_checkbox.add(checkBox_lunes);
        checkBox_martes = view.findViewById(R.id.checkBox_Martes);
        lista_checkbox.add(checkBox_martes);
        checkBox_miercoles = view.findViewById(R.id.checkBox_Miercoles);
        lista_checkbox.add(checkBox_miercoles);
        checkBox_jueves = view.findViewById(R.id.checkBox_Jueves);
        lista_checkbox.add(checkBox_jueves);
        checkBox_viernes = view.findViewById(R.id.checkBox_Viernes);
        lista_checkbox.add(checkBox_viernes);



        Calendar calendar = Calendar.getInstance();
        final int hour = calendar.get(Calendar.HOUR_OF_DAY);
        final int minute = calendar.get(Calendar.MINUTE);

        navigationView = ((MainActivity2) getActivity()).getNavigationView();

        ((MainActivity2) getActivity()).setupNavigationDrawerContent(navigationView);
        //Hide share button
        shareBtn.setVisibility(View.GONE);

        date_inicio_fin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                materialDatePicker.show(getActivity().getSupportFragmentManager(), "DATE PICKER");

            }
        });

        materialDatePicker.addOnPositiveButtonClickListener(new MaterialPickerOnPositiveButtonClickListener() {
            @Override
            public void onPositiveButtonClick(Object selection) {


                date_inicio_fin.setText(materialDatePicker.getHeaderText().toString());
            }
        });

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

        guardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                validacionAsignatura = new ValidacionAsignatura();
                int valor = validacionAsignatura.validarAs(lista_inicios, lista_finales, lista_checkbox, campo1, date_inicio_fin);
                if(valor==2){
                    Toast.makeText(getActivity().getApplicationContext(), "Los campos de hora no pueden quedar vacios", Toast.LENGTH_SHORT).show();
                }else if (valor == 3){
                    Toast.makeText(getActivity().getApplicationContext(), "La hora de inicio debe ser menor a la de final", Toast.LENGTH_SHORT).show();
                }else if (valor == 0){
                    //CREAR OBJETO ASIGNATURA Y AÑADIRLO A LA LISTA DE ASIGNATURAS DEL USUARIO

                    //REGRSAR A LA PANTALLA ANTERIOR
                    Toast.makeText(getActivity().getApplicationContext(), "Datos ingresados correctamente", Toast.LENGTH_SHORT).show();
                }


            }
        });

        cancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ActivityAsignatura activity_asignatura = new ActivityAsignatura();
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.fragment, activity_asignatura);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
                //Tiene que volver a la view anterior
            }
        });


        return view;
    }




}
