package com.example.zeitplan_proyect.vista;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.zeitplan_proyect.DataBase.Firebase;
import com.example.zeitplan_proyect.DataBase.MyAdapter;
import com.example.zeitplan_proyect.MainActivity2;
import com.example.zeitplan_proyect.R;
import com.example.zeitplan_proyect.model.Event;
import com.example.zeitplan_proyect.model.EventoBD;
import com.example.zeitplan_proyect.presenter.PresenterCalendarUtils;
import com.example.zeitplan_proyect.presenter.PresenterListaEvents;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

@RequiresApi(api = Build.VERSION_CODES.O)
public class LlistaEventsActivity extends Fragment {
    private boolean bandera = false;
    private RecyclerView eventRV;
    private Button calendarAction, semanalAction, dailyAction;
    private Spinner spinner;

    ArrayList<Event> eventos;
    MyAdapter eAdapter;
    FirebaseFirestore mFirestore;
    Firebase db = new Firebase();
    PresenterCalendarUtils PresCal;
    PresenterListaEvents consulta =new PresenterListaEvents();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        final View view = inflater.inflate(R.layout.activity_llista_events, container, false);
        super.onCreate(savedInstanceState);
        ((MainActivity2) getActivity()).getSupportActionBar().setTitle("Calendario");

        spinner = (Spinner) view.findViewById(R.id.spinner_orden);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getContext(), R.array.TipoOrden, android.R.layout.simple_spinner_item);
        spinner.setAdapter(adapter);

        eventRV = view.findViewById(R.id.reciclerllista);
        eventRV.setHasFixedSize(true);
        eventRV.setLayoutManager(new LinearLayoutManager(getActivity().getApplicationContext()));

        calendarAction= view.findViewById(R.id.calendarButt);
        semanalAction = view.findViewById(R.id.semanalButt);
        dailyAction = view.findViewById(R.id.dailyButt);

        mFirestore=FirebaseFirestore.getInstance();

        PresCal = PresenterCalendarUtils.getInstance();

        FloatingActionButton shareBtn =  ((MainActivity2) getActivity()).findViewById(R.id.share);
        shareBtn.setVisibility(View.GONE);

        eventos = new ArrayList<Event>();
        eAdapter = new MyAdapter(getActivity().getApplicationContext(), eventos, getContext());
        eventRV.setAdapter(eAdapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view,
                                       int position, long id) {
                eventos = new ArrayList<Event>();
                eAdapter = new MyAdapter(getActivity().getApplicationContext(), eventos,getContext());
                eventRV.setAdapter(eAdapter);
                mostarFiltro();

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                // TODO Auto-generated method stub

            }
        });

        calendarAction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                calendarAction(view);
            }
        });


        semanalAction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                weeklyAction(view);
            }
        });
        dailyAction.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                dailyAction(view);
            }
        });



        return view;
    }



    private void mostarFiltro() {
        String orden = spinner.getSelectedItem().toString();
        Log.i("filtro valor enviado", orden);
        consulta.filtrarEvento(orden,eventos,eAdapter);

    }
    public void calendarAction(View view) {
        CalendarActivity CalendarActivity = new CalendarActivity();
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragment, CalendarActivity);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }
    public void weeklyAction(View view)
    {
        WeekViewActivity weekViewActivity = new WeekViewActivity();
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragment, weekViewActivity);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }
    public void dailyAction(View view) {
        DailyCalendarActivity dailyCalendarActivity = new DailyCalendarActivity();
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragment, dailyCalendarActivity);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();

    }

}
