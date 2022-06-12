package com.example.zeitplan_proyect.vista;


import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.example.zeitplan_proyect.DataBase.Firebase;
import com.example.zeitplan_proyect.DataBase.MyAdapter;
import com.example.zeitplan_proyect.MainActivity2;
import com.example.zeitplan_proyect.model.Event;
import com.example.zeitplan_proyect.R;
import com.example.zeitplan_proyect.presenter.PresenterCalendarUtils;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

@RequiresApi(api = Build.VERSION_CODES.O)
public class WeekViewActivity extends Fragment implements CalendarAdapter.OnItemListener {

    private TextView monthYearText;
    private RecyclerView calendarRV;
    private RecyclerView eventRV;
    private Button prevWeekAction,nextWeekAction,dailyAction, calendarAction, llistaAction;

    ArrayList<Event> eventosV;
    ArrayList<Event> eventosL;
    MyAdapter eAdapter;
    CalendarAdapter calendarAdapter;
    FirebaseFirestore mFirestore;
    Firebase db = new Firebase();
    PresenterCalendarUtils PresCal;


    public WeekViewActivity() {
    }


    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        final View view = inflater.inflate(R.layout.activity_week_view, container, false);
        ((MainActivity2) getActivity()).getSupportActionBar().setTitle("Calendario");

        calendarRV = view.findViewById(R.id.calendarRV);
        monthYearText = view.findViewById(R.id.monthYearTV);
        eventRV = view.findViewById(R.id.eventRV);
        prevWeekAction = view.findViewById(R.id.prevWeekAction);
        nextWeekAction = view.findViewById(R.id.nextWeekAction);
        calendarAction=view.findViewById(R.id.calendarButt);
        dailyAction = view.findViewById(R.id.dailyButt);
        llistaAction = view.findViewById(R.id.ListButt);
        FloatingActionButton shareBtn =  ((MainActivity2) getActivity()).findViewById(R.id.share);
        shareBtn.setVisibility(View.GONE);

        PresCal = PresenterCalendarUtils.getInstance();

        mFirestore = FirebaseFirestore.getInstance();
        eventRV = view.findViewById(R.id.eventRV);
        eventRV.setHasFixedSize(true);
        eventRV.setLayoutManager(new LinearLayoutManager(getActivity().getApplicationContext()));

        setWeekView();


        prevWeekAction.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                prevWeekAction(view);
            }
        });
        nextWeekAction.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                nextWeekAction(view);
            }
        });
        calendarAction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                calendarAction(view);
            }
        });
        dailyAction.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                dailyAction(view);
            }
        });
        llistaAction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                llistaAction(view);
            }
        });

    return view;
    }

    private void setWeekView()
    {
        // Vista semanal
        eventosV = new ArrayList<Event>();
        monthYearText.setText(PresCal.monthYearFromSelDay());
        ArrayList<LocalDate> days = PresCal.daysInWeekArray();
        calendarAdapter = new CalendarAdapter(days, this, eventosV);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(requireContext(), 7);
        calendarRV.setLayoutManager(layoutManager);
        calendarRV.setAdapter(calendarAdapter);
        EventVChangeListener();

        // Sublista
        eventosL = new ArrayList<Event>();
        eAdapter = new MyAdapter(getActivity().getApplicationContext(), eventosL, getContext());
        eventRV.setAdapter(eAdapter);
        EventLChangeListener();
    }


    public void prevWeekAction(View view)
    {
        PresCal.SelDateMoveWeek(-1);
        setWeekView();

    }

    public void nextWeekAction(View view)
    {
        PresCal.SelDateMoveWeek(1);
        setWeekView();
    }

    @Override
    public void onItemClick(int position, LocalDate date){
        if(PresCal.getSelectedDate().equals(date)){
            dailyAction(this.getView());
        }else{
            PresCal.setSelectedDate(date);
            setWeekView();
        }
    }

    public void onResume()
    {
        super.onResume();
    }

    private void EventVChangeListener() {
        mFirestore.collection("evento").whereEqualTo("idUser",db.getIdUser())
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                        if(error != null ){
                            Log.e("Firestore error", error.getMessage() );
                            return;
                        }
                        for(DocumentChange dc: value.getDocumentChanges()){
                            if(dc.getType() == DocumentChange.Type.ADDED){
                                eventosV.add(dc.getDocument().toObject(Event.class));
                            }
                            calendarAdapter.notifyDataSetChanged();
                        }
                    }
                });
    }

    private void EventLChangeListener() {
        // String orden = spinner.getSelectedItem().toString();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        String SelDate = PresCal.getSelectedDate().format(formatter);
        mFirestore.collection("evento").whereEqualTo("idUser",db.getIdUser()).whereEqualTo("fecha_inicio",SelDate)
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                        if(error != null ){
                            Log.e("Firestore error", error.getMessage() );
                            return;
                        }
                        for(DocumentChange dc: value.getDocumentChanges()){
                            if(dc.getType() == DocumentChange.Type.ADDED){
                                eventosL.add(dc.getDocument().toObject(Event.class));
                            }
                            if(dc.getType() == DocumentChange.Type.REMOVED){
                                eventosL.remove(dc.getOldIndex());
                            }
                            eAdapter.notifyDataSetChanged();
                        }
                    }
                });

    }


    public void calendarAction(View view) {
        CalendarActivity CalendarActivity = new CalendarActivity();
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragment, CalendarActivity);
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
    public void llistaAction(View view)
    {
        LlistaEventsActivity llistaEventsActivity = new LlistaEventsActivity();
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragment, llistaEventsActivity);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }
}