package com.example.zeitplan_proyect;

import static com.example.zeitplan_proyect.CalendarUtils.daysInMonthArray;
import static com.example.zeitplan_proyect.CalendarUtils.daysInWeekArray;
import static com.example.zeitplan_proyect.CalendarUtils.monthYearFromDate;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
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
import android.widget.Toast;

import java.time.LocalDate;
import java.util.ArrayList;

@RequiresApi(api = Build.VERSION_CODES.O)
public class WeekViewActivity extends Fragment implements CalendarAdapter.OnItemListener{
    private TextView monthYearText;
    private RecyclerView calendarRV;
    private ListView eventListView;
    private Button prevWeekAction,nextWeekAction,dailyAction,nuevoEvento;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        final View view = inflater.inflate(R.layout.activity_week_view, container, false);
        ((MainActivity2) getActivity()).getSupportActionBar().setTitle("Calendario");

        calendarRV = view.findViewById(R.id.calendarRV);
        monthYearText = view.findViewById(R.id.monthYearTV);
        eventListView = view.findViewById(R.id.eventListView);
        prevWeekAction = view.findViewById(R.id.prevWeekAction);
        nextWeekAction = view.findViewById(R.id.nextWeekAction);
        dailyAction = view.findViewById(R.id.dailyAction);

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
        dailyAction.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                dailyAction(view);
            }
        });

    return view;
    }

    private void setWeekView()
    {
        monthYearText.setText(monthYearFromDate(CalendarUtils.selectedDate));
        ArrayList<LocalDate> days = daysInWeekArray(CalendarUtils.selectedDate);
        CalendarAdapter calendarAdapter = new CalendarAdapter(days, this);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getContext(), 7);
        calendarRV.setLayoutManager(layoutManager);
        calendarRV.setAdapter(calendarAdapter);
        setEventAdapter();

    }



    public void prevWeekAction(View view)
    {
        CalendarUtils.selectedDate = CalendarUtils.selectedDate.minusWeeks(1);
        setWeekView();
    }

    public void nextWeekAction(View view)
    {
        CalendarUtils.selectedDate = CalendarUtils.selectedDate.plusWeeks(1);
        setWeekView();
    }

    @Override
    public void onItemClick(int position, LocalDate date){
        CalendarUtils.selectedDate = date;
        setWeekView();
    }

    public void onResume()
    {
        super.onResume();
        setEventAdapter();
    }

    private void setEventAdapter()
    {
        ArrayList<Event> dailyEvents = Event.eventsForDate(CalendarUtils.selectedDate);
        EventAdapter eventAdapter = new EventAdapter(getContext(), dailyEvents);
        eventListView.setAdapter(eventAdapter);
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