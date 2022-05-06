package com.example.zeitplan_proyect;

import static com.example.zeitplan_proyect.CalendarUtils.daysInMonthArray;
import static com.example.zeitplan_proyect.CalendarUtils.monthYearFromDate;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.time.LocalDate;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import com.example.zeitplan_proyect.MainActivity2;
import com.example.zeitplan_proyect.R;
import com.google.android.material.navigation.NavigationView;

@RequiresApi(api = Build.VERSION_CODES.O)
public class calendarActivity extends Fragment implements CalendarAdapter.OnItemListener {
    private TextView monthYearText;
    private RecyclerView calendarRV;
    private Button prevMonth, nextMonth, weeklyAction;
    private NavigationView navigationView;

    

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
        weeklyAction = view.findViewById(R.id.weeklyAction);
        navigationView = ((MainActivity2) getActivity()).getNavigationView();

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
        weeklyAction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                weeklyAction(view);
            }
        });

        CalendarUtils.selectedDate = LocalDate.now();
        setMonthView();
        ((MainActivity2) getActivity()).setupNavigationDrawerContent(navigationView);
        return view;
    }


    private void setMonthView()
    {
        monthYearText.setText(monthYearFromDate(CalendarUtils.selectedDate));
        ArrayList<LocalDate> daysMonth = daysInMonthArray();
        CalendarAdapter calendarAdapter = new CalendarAdapter(daysMonth, this);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(requireContext(), 7);
        calendarRV.setLayoutManager(layoutManager);
        calendarRV.setAdapter(calendarAdapter);
    }



    public void prevMonthAction(View view) {
        CalendarUtils.selectedDate = CalendarUtils.selectedDate.minusMonths(1);
        setMonthView();
    }

    public void nextMonthAction(View view) {
        CalendarUtils.selectedDate = CalendarUtils.selectedDate.plusMonths(1);
        setMonthView();
    }

    @Override
    public void onItemClick(int position, LocalDate date){
        if(date!=null){
            CalendarUtils.selectedDate = date;
            setMonthView();
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


}