package com.example.zeitplan_proyect;

import static com.example.zeitplan_proyect.CalendarUtils.selectedDate;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
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

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.time.format.TextStyle;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

@RequiresApi(api = Build.VERSION_CODES.O)
public class DailyCalendarActivity extends Fragment {

    private TextView monthDayText;
    private TextView dayOfWeekTV;
    private ListView hourListView;
    private Button prevDayAction,nextDayAction,nuevoEvento;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        final View view = inflater.inflate(R.layout.activity_daily_calendar, container, false);
        ((MainActivity2) getActivity()).getSupportActionBar().setTitle("Calendario");
        monthDayText = view.findViewById(R.id.monthDayTV);
        dayOfWeekTV = view.findViewById(R.id.dayOfWeekTV);
        hourListView = view.findViewById(R.id.hourListView);
        prevDayAction = view.findViewById(R.id.prevDayAction);
        nextDayAction = view.findViewById(R.id.nextDayAction);
        nuevoEvento = view.findViewById(R.id.nuevo_evento);

        prevDayAction.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                prevDayAction(view);
            }
        });
        nextDayAction.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                nextDayAction(view);
            }
        });
        nuevoEvento.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                newEventAction(view);
            }
        });
        return view;
    }
    @Override
    public void onResume()
    {
        super.onResume();
        setDayView();
    }

    private void setDayView()
    {
        monthDayText.setText(CalendarUtils.monthDayFromDate(selectedDate));
        dayOfWeekTV.setText(StringDayOfWeek(selectedDate.getDayOfWeek()));
        setHourAdapter();
    }

    public String StringDayOfWeek(DayOfWeek dayOfWeek) {
        switch(dayOfWeek){
            case MONDAY: return "Lunes";
            case TUESDAY: return "Martes";
            case WEDNESDAY: return "Miercoles";
            case THURSDAY: return "Jueves";
            case FRIDAY: return "Viernes";
            case SATURDAY: return "Sabado";
            case SUNDAY: return "Domingo";
        }
        return " ";
    }


    private void setHourAdapter() {
        HourAdapter hourAdapter = new HourAdapter(getContext(), hourEventList());
        hourListView.setAdapter(hourAdapter);

    }

    private ArrayList<HourEvent> hourEventList() {

        ArrayList<HourEvent> list = new ArrayList<>();

        for(int hour = 0; hour<24; hour++){
            LocalTime time = LocalTime.of(hour, 0);
            ArrayList<Event> events = Event.eventsForDateAndTime(selectedDate, time);
            HourEvent hourEvent = new HourEvent(time, events);
            list.add(hourEvent);
        }
        return list;

    }

    public void prevDayAction(View view)
    {
        CalendarUtils.selectedDate = CalendarUtils.selectedDate.minusDays(1);
        setDayView();
    }

    public void nextDayAction(View view)
    {
        CalendarUtils.selectedDate = CalendarUtils.selectedDate.plusDays(1);
        setDayView();
    }

    public void newEventAction(View view)
    {
        startActivity(new Intent(getActivity(), EventEditActivity.class));
    }
}