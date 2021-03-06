package com.example.zeitplan_proyect.vista;

import androidx.core.util.Pair;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
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
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.zeitplan_proyect.MainActivity2;
import com.example.zeitplan_proyect.R;
import com.example.zeitplan_proyect.model.Asignatura;
import com.example.zeitplan_proyect.model.EventoGeneral;
import com.example.zeitplan_proyect.model.ValidacionAsignatura;
import com.example.zeitplan_proyect.presenter.PresenterAsignatura;
import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.datepicker.MaterialPickerOnPositiveButtonClickListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class Activity_add_asignatura extends Fragment {


    EditText campo1, descripcion;
    TimePickerDialog.OnTimeSetListener setListenerTimeLunes,setListenerTimeMartes,setListenerTimeMiercoles,setListenerTimeJueves,setListenerTimeViernes,setListenerTimeLunesFinal,setListenerTimeMartesFinal,setListenerTimeMiercolesFinal,setListenerTimeJuevesFinal,setListenerTimeViernesFinal ;
    TextView fehca_in, fecha_fin, inicio_lunes, inicio_martes, inicio_miercoles, inicio_jueves, inicio_viernes,
            final_lunes, final_martes, final_miercoles, final_jueves, final_viernes;
    CheckBox checkBox_lunes, checkBox_martes, checkBox_miercoles, checkBox_jueves, checkBox_viernes;
    Spinner spinner;
    NavigationView navigationView;
    Button guardar, cancelar;
    ImageButton fechaIn, fechaFin;

    DatePickerDialog.OnDateSetListener setListenerDateEventIn,setListenerDateEventFin;

    ArrayList<TextView> lista_inicios, lista_finales;
    ArrayList<CheckBox> lista_checkbox;

    PresenterAsignatura presenterAsignatura;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        final View view = inflater.inflate(R.layout.activity_add_asignatura, container, false);
        ((MainActivity2) getActivity()).getSupportActionBar().setTitle("A??adir Asignatura");
        FloatingActionButton shareBtn =  ((MainActivity2) getActivity()).findViewById(R.id.share);

        presenterAsignatura = new PresenterAsignatura(getContext());
        fehca_in = view.findViewById(R.id.datepicker_inicio);
        fecha_fin = view.findViewById(R.id.datepicker_final);

        fechaIn = view.findViewById(R.id.acceder_datepickerinicio);
        fechaFin = view.findViewById(R.id.acceder_datepickerfinal);

        spinner = (Spinner) view.findViewById(R.id.spinner_descripcion);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getContext(), R.array.TipoDescripcion, android.R.layout.simple_spinner_item);
        spinner.setAdapter(adapter);

        descripcion = view.findViewById(R.id.editTextDescripcion);
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

        final int day = calendar.get(Calendar.DAY_OF_MONTH);
        final int month = calendar.get(Calendar.MONTH);
        final int year = calendar.get(Calendar.YEAR);


        navigationView = ((MainActivity2) getActivity()).getNavigationView();

        ((MainActivity2) getActivity()).setupNavigationDrawerContent(navigationView);
        shareBtn.setVisibility(View.GONE);

        fechaIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerDialog datePicker = new DatePickerDialog(
                        getContext(), android.R.style.Theme_Holo_Light_Dialog_MinWidth, setListenerDateEventIn, year, month, day);
                datePicker.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                datePicker.show();
            }
        });

       setListenerDateEventIn = new DatePickerDialog.OnDateSetListener() {
           @Override
           public void onDateSet(DatePicker datePicker, int year, int month, int dayOfMonth) {
               String date_inicio = datePicker.getDayOfMonth() + "/" + datePicker.getMonth() + "/" + datePicker.getYear();
               fehca_in.setText(date_inicio);
           }
       };

        fechaFin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerDialog datePicker = new DatePickerDialog(
                        getContext(), android.R.style.Theme_Holo_Light_Dialog_MinWidth, setListenerDateEventFin, year, month, day);
                datePicker.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                datePicker.show();
            }
        });

        setListenerDateEventFin = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int dayOfMonth) {
                String date_inicio = datePicker.getDayOfMonth() + "/" + datePicker.getMonth() + "/" + datePicker.getYear();
                fecha_fin.setText(date_inicio);
            }
        };

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
                int valor = presenterAsignatura.validarAs(lista_inicios, lista_finales, lista_checkbox, campo1, fehca_in, fecha_fin);
                if(presenterAsignatura.validarNombre(campo1.getText().toString())){
                    Toast.makeText(getActivity().getApplicationContext(), "Ya existe una asignatura con ese nombre", Toast.LENGTH_SHORT).show();
                }
                else if(valor==2){
                    Toast.makeText(getActivity().getApplicationContext(), "Los campos de hora no pueden quedar vacios", Toast.LENGTH_SHORT).show();
                }else if (valor == 3){
                    Toast.makeText(getActivity().getApplicationContext(), "La hora de inicio debe ser menor a la de final", Toast.LENGTH_SHORT).show();
                }else if (valor == 4){
                    Toast.makeText(getActivity().getApplicationContext(), "La fecha de inicio debe ser menor a la final", Toast.LENGTH_SHORT).show();
                }else if (valor == 0){
                    presenterAsignatura.crearAsignatura(fehca_in.getText().toString(),fecha_fin.getText().toString(),campo1.getText().toString(),descripcion.getText().toString(),lista_checkbox,lista_inicios,lista_finales,view.getContext());
                    Intent intent = new Intent(view.getContext(),MainActivity2.class);
                    startActivity(intent);
                }
            }
        });

        cancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFragmentManager().popBackStack();
            }
        });

        return view;
    }



}
