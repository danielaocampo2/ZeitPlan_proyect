package com.example.zeitplan_proyect.vista;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.time.LocalDate;
import java.util.ArrayList;

import com.example.zeitplan_proyect.MainActivity2;
import com.example.zeitplan_proyect.R;
import com.example.zeitplan_proyect.presenter.PresenterCalendarUtils;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;

@RequiresApi(api = Build.VERSION_CODES.O)
public class CalendarActivity extends Fragment implements CalendarAdapter.OnItemListener {
    private TextView monthYearText;
    private RecyclerView calendarRV;
    private Button prevMonth, nextMonth, semanalAction, dailyAction, llistaAction;
    private NavigationView navigationView;

    PresenterCalendarUtils PresCal;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        // Inflate the layout for this fragment
        final View view = inflater.inflate(R.layout.activity_calendar, container, false);
        ((MainActivity2) getActivity()).getSupportActionBar().setTitle("Calendario");
        calendarRV = (RecyclerView) view.findViewById(R.id.calendarRV);
        monthYearText = (TextView) view.findViewById(R.id.monthYearTV);
        prevMonth = view.findViewById(R.id.prevMonth);
        nextMonth = view.findViewById(R.id.nextMonth);
        semanalAction = view.findViewById(R.id.semanalButt);
        dailyAction = view.findViewById(R.id.dailyButt);
        llistaAction = view.findViewById(R.id.ListButt);
        navigationView = ((MainActivity2) getActivity()).getNavigationView();
        PresCal = PresenterCalendarUtils.getInstance();
        FloatingActionButton shareBtn =  ((MainActivity2) getActivity()).findViewById(R.id.share);
        shareBtn.setVisibility(View.GONE);

        nextMonth.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                nextMonthAction(view);
            }
        });
        prevMonth.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                prevMonthAction(view);
            }
        });
        semanalAction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                weeklyAction(view);
            }
        });
        dailyAction.setOnClickListener(new View.OnClickListener() {
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

        PresCal.setSelectedDate(LocalDate.now());
        setMonthView();
        ((MainActivity2) getActivity()).setupNavigationDrawerContent(navigationView);
        return view;
    }


    private void setMonthView()
    {
        monthYearText.setText(PresCal.monthYearFromSelDay());
        ArrayList<LocalDate> daysMonth = PresCal.daysInMonthArray();
        CalendarAdapter calendarAdapter = new CalendarAdapter(daysMonth, this);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(requireContext(), 7);
        calendarRV.setLayoutManager(layoutManager);
        calendarRV.setAdapter(calendarAdapter);
    }



    public void prevMonthAction(View view) {
        PresCal.SelDateMoveMonth(-1);
        setMonthView();
    }

    public void nextMonthAction(View view) {
        PresCal.SelDateMoveMonth(1);
        setMonthView();
    }

    @Override
    public void onItemClick(int position, LocalDate date){
        if(date!=null){
            if(PresCal.getSelectedDate().equals(date)){
                dailyAction(this.getView());
            }else{
                PresCal.setSelectedDate(date);
                setMonthView();
            }
        }
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