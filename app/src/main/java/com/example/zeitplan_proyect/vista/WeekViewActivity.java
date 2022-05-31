package com.example.zeitplan_proyect.vista;


import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.example.zeitplan_proyect.MainActivity2;
import com.example.zeitplan_proyect.model.Event;
import com.example.zeitplan_proyect.R;
import com.example.zeitplan_proyect.presenter.PresenterCalendarUtils;

import java.time.LocalDate;
import java.util.ArrayList;

@RequiresApi(api = Build.VERSION_CODES.O)
public class WeekViewActivity extends Fragment implements CalendarAdapter.OnItemListener {
    private TextView monthYearText;
    private RecyclerView calendarRV;
    private ListView eventListView;
    private Button prevWeekAction,nextWeekAction,dailyAction, calendarAction, llistaAction;

    PresenterCalendarUtils PresCal;

    public WeekViewActivity() {
    }


    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        final View view = inflater.inflate(R.layout.activity_week_view, container, false);
        ((MainActivity2) getActivity()).getSupportActionBar().setTitle("Calendario");

        calendarRV = view.findViewById(R.id.calendarRV);
        monthYearText = view.findViewById(R.id.monthYearTV);
        eventListView = view.findViewById(R.id.eventListView);
        prevWeekAction = view.findViewById(R.id.prevWeekAction);
        nextWeekAction = view.findViewById(R.id.nextWeekAction);
        calendarAction=view.findViewById(R.id.calendarButt);
        dailyAction = view.findViewById(R.id.dailyButt);
        llistaAction = view.findViewById(R.id.ListButt);

        PresCal = PresenterCalendarUtils.getInstance();

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
        monthYearText.setText(PresCal.monthYearFromSelDay());
        ArrayList<LocalDate> days = PresCal.daysInWeekArray();
        CalendarAdapter calendarAdapter = new CalendarAdapter(days, this);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getContext(), 7);
        calendarRV.setLayoutManager(layoutManager);
        calendarRV.setAdapter(calendarAdapter);
        setEventAdapter();

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
        setEventAdapter();
    }

    private void setEventAdapter()
    {
        ArrayList<Event> dailyEvents = Event.eventsForDate(PresCal.getSelectedDate());
        EventAdapter eventAdapter = new EventAdapter(getContext(), dailyEvents);
        eventListView.setAdapter(eventAdapter);
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