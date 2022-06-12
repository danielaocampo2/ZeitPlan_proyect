package com.example.zeitplan_proyect.vista;

import static android.content.ContentValues.TAG;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

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
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.zeitplan_proyect.DataBase.Firebase;
import com.example.zeitplan_proyect.MainActivity2;
import com.example.zeitplan_proyect.R;
import com.example.zeitplan_proyect.presenter.PresenterCalendarUtils;
import com.example.zeitplan_proyect.presenter.PresenterCrearEvent;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

@RequiresApi(api = Build.VERSION_CODES.O)
public class Activity_crear extends Fragment {

    EditText eventNameET, eventDescrET;
    TextView eventDateTV, eventTimeTVIni, eventTimeTVFin;
    TimePickerDialog.OnTimeSetListener setListenerTimeEventIni, setListenerTimeEventFin;
    DatePickerDialog.OnDateSetListener setListenerDateEvent;
    String funcion = "guardar";
    private LocalTime timeIni, timeFin;
    private LocalDate date;
    private int prioridad;
    private boolean remember;

    private PresenterCalendarUtils PreCal;
    private PresenterCrearEvent PreCreEvent;

    AutoCompleteTextView spinner;
    SeekBar seekBar;
    TextView resultado_seekBar;
    CheckBox recuerdame_check;
    NavigationView navigationView;
    Button aceptar;
    String id;

    Firebase bd = new Firebase();

    DateTimeFormatter formatterDate = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    DateTimeFormatter formatterTime = DateTimeFormatter.ofPattern("HH:mm");


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {


        final View view = inflater.inflate(R.layout.activity_crear, container, false);
        FloatingActionButton shareBtn =  ((MainActivity2) getActivity()).findViewById(R.id.share);

        spinner = (AutoCompleteTextView) view.findViewById(R.id.spinner_tipos);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getContext(), R.array.TipoEventos, android.R.layout.simple_spinner_item);
        spinner.setAdapter(adapter);
        aceptar = view.findViewById(R.id.btn_guardar);

        eventNameET = (EditText) view.findViewById(R.id.editText_Titulo);
        eventDescrET = (EditText) view.findViewById(R.id.editText_descripcion);

        eventDateTV = view.findViewById(R.id.FechaTV);
        eventTimeTVIni = view.findViewById(R.id.HoraTVIni);
        eventTimeTVFin = view.findViewById(R.id.HoraTVFin);

        resultado_seekBar = view.findViewById(R.id.textView_resultadoPrioridad);
        seekBar = view.findViewById(R.id.seekBar_prioridad);
        seekBar.setProgress(0); //valor inicial
        seekBar.setMax(100); //valor final

        recuerdame_check = view.findViewById(R.id.checkBox_alarma);

        navigationView = ((MainActivity2) getActivity()).getNavigationView();

        Bundle datosRecuperados = getArguments();
        if (datosRecuperados != null) {
            // No hay datos, manejar excepción
            funcion = datosRecuperados.getString("funcion");
            id = datosRecuperados.getString("id");
            Log.i(TAG, "id: "+id);
            //eventNameET.setText(titulo);
            bd.mFirestore.collection("evento").whereEqualTo("idUser",bd.getIdUser()).whereEqualTo("idEvento",id)
                    .addSnapshotListener(new EventListener<QuerySnapshot>() {
                        @Override
                        public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                            if(error!=null){
                                Log.e("Firestore error", error.getMessage() );
                                return;
                            }
                           // List datos= value.iterator();

                            for(DocumentChange dc: value.getDocumentChanges()) {


                                eventNameET.setText((String) dc.getDocument().get("nombre"));
                                eventDescrET.setText((String) dc.getDocument().get("descripcion"));
                                Number priori=  (Number) dc.getDocument().get("prioridad");
                               // Log.i(TAG, " valores " + priori.toString());
                                resultado_seekBar.setText(priori.toString()+"%");
                                seekBar.setProgress(priori.intValue());

                                spinner.setText((String) dc.getDocument().get("tipo"));
                                ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getContext(), R.array.TipoEventos, android.R.layout.simple_spinner_item);
                                spinner.setAdapter(adapter);

                                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                                String date2 = (String) dc.getDocument().get("fecha_inicio");
                                LocalDate dateBD = LocalDate.parse(date2, formatter);
                                eventDateTV.setText("  Fecha:\n  " + PreCal.formattedDate(dateBD));
                                LocalTime horaI = LocalTime.parse((String) dc.getDocument().get("tiempoIni"));
                                eventTimeTVIni.setText("  Hora Inicial:\n  " + horaI.format(formatterTime));
                                LocalTime horaF = LocalTime.parse((String) dc.getDocument().get("tiempoFin"));
                                eventTimeTVFin.setText("  Hora Final:\n  " + horaF.format(formatterTime));
                                ((MainActivity2) getActivity()).getSupportActionBar().setTitle("Editar Actividad");

                            }
                        }
                    });

            if(funcion=="editar"){
                aceptar.setText("Editar");
            }
        }else{
            ((MainActivity2) getActivity()).getSupportActionBar().setTitle("Añadir Actividad");

        }
        ((MainActivity2) getActivity()).setupNavigationDrawerContent(navigationView);

        this.PreCal = PresenterCalendarUtils.getInstance();
        PreCreEvent = new PresenterCrearEvent(getActivity().getApplicationContext());

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

        aceptar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                agregar(view);
            }
        });

        timeIni = LocalTime.now();
        timeFin = timeIni.plusHours(1);
        date = PreCal.getSelectedDate();

        eventDateTV.setText("  Fecha:\n  " + PreCal.formattedDate(date));
        eventTimeTVIni.setText("  Hora Inicial:\n  " + timeIni.format(formatterTime));
        eventTimeTVFin.setText("  Hora Final:\n  " + timeFin.format(formatterTime));

        eventDateTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerDialog datePicker = new DatePickerDialog(
                        getContext(), android.R.style.Theme_Holo_Light_Dialog_MinWidth, setListenerDateEvent, date.getYear(), date.getMonthValue(), date.getDayOfMonth());
                datePicker.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                datePicker.show();
            }
        });
        setListenerDateEvent = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                date = LocalDate.of(datePicker.getYear(), datePicker.getMonth() + 1, datePicker.getDayOfMonth());
                eventDateTV.setText("  Fecha:\n  " + PreCal.formattedDate(date));
            }
        };

        eventTimeTVIni.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TimePickerDialog timePicker = new TimePickerDialog(
                        getContext(), android.R.style.Theme_Holo_Light_Dialog_MinWidth, setListenerTimeEventIni, timeIni.getHour(), timeIni.getMinute(), true);
                timePicker.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                timePicker.show();
            }
        });
        setListenerTimeEventIni = new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int i, int e) {
                timeIni = LocalTime.of(timePicker.getHour(), timePicker.getMinute());
                eventTimeTVIni.setText("  Hora Inicial:\n  " + timeIni.format(formatterTime));
            }
        };

        eventTimeTVFin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TimePickerDialog timePicker = new TimePickerDialog(
                        getContext(), android.R.style.Theme_Holo_Light_Dialog_MinWidth, setListenerTimeEventFin, timeFin.getHour(), timeFin.getMinute(), true);
                timePicker.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                timePicker.show();
            }
        });
        setListenerTimeEventFin = new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int i, int e) {
                timeFin = LocalTime.of(timePicker.getHour(), timePicker.getMinute());
                eventTimeTVFin.setText("  Hora Final:\n  " + timeFin.format(formatterTime));
            }
        };



        //Hide share button
        shareBtn.setVisibility(View.GONE);
    return view;
    }

    //Falta que vuelva el boton a la ultima vista visitada

    public void agregar(View v){
        if(validar()){
            String eventName = eventNameET.getText().toString();
            String eventDescription = eventDescrET.getText().toString();
            String fecha = date.format(formatterDate);
            String horaIni = timeIni.format(formatterTime);
            String horaFin = timeFin.format(formatterTime);
            Log.i(TAG, "agregar: " +fecha);

            String tipoEven = spinner.getText().toString();
            if (funcion == "guardar") {
                PreCreEvent.guardarEvendoBD(eventName, eventDescription, fecha, horaIni, horaFin, prioridad, tipoEven);
            }else{
                PreCreEvent.actualizarEvendoBD(eventName, eventDescription, fecha, horaIni, horaFin, prioridad, tipoEven, id);
            }

            // llamar a metodo para guardar datos.
            Toast.makeText(getContext(), "Datos ingresados correctamente", Toast.LENGTH_SHORT).show();


            if(remember){
                Intent intent =new Intent(v.getContext(),Recordar.class);
                intent.putExtra("nombreEvento", eventName);
                intent.putExtra("descripcion", eventDescription);
                startActivity(intent);
            }else{
                /*Intent intent =new Intent(v.getContext(),MainActivity2.class);
                startActivity(intent);*/

                LlistaEventsActivity llistaEventsActivity = new LlistaEventsActivity();

                //activity_crear.setArguments(datosAEnviar);
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();

                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                //getParentFragmentManager().setFragmentResult("requestKey", result);
                fragmentTransaction.replace(R.id.fragment, llistaEventsActivity);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }

        }
    }

    public boolean validar(){
        String c1 = eventNameET.getText().toString();
        if (c1.isEmpty()){
            eventNameET.setError("Este campo no puede quedar vacio");
            Log.i(TAG, "validar:  hola"+ getContext());
            Log.i(TAG, "validar:  hola"+ this.getActivity().getApplicationContext());

            Toast.makeText(getActivity(), "Click!", Toast.LENGTH_SHORT).show();;

            return false;
        } if (timeIni.isAfter(timeFin)){
            Toast.makeText(getContext(), "Hora Inicio debe ser anterior a Hora Final", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }


}
