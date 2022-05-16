package com.example.zeitplan_proyect.vista;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

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

import java.time.LocalTime;
import java.util.ArrayList;

@RequiresApi(api = Build.VERSION_CODES.O)
public class DailyCalendarActivity extends Fragment {

    private TextView monthDayText;
    private TextView dayOfWeekTV;
    private ListView hourListView;
    private Button prevDayAction,nextDayAction;

    PresenterCalendarUtils PresCal;

    public DailyCalendarActivity(PresenterCalendarUtils PresCal) {
        this.PresCal = PresCal;
    }

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
        monthDayText.setText(PresCal.monthDayFromSelDay());
        dayOfWeekTV.setText(PresCal.SelDateDayOfWeek());
        setHourAdapter();
    }




    private void setHourAdapter() {
        HourAdapter hourAdapter = new HourAdapter(getContext(), hourEventList());
        hourListView.setAdapter(hourAdapter);

    }

    private ArrayList<HourEvent> hourEventList() {

        ArrayList<HourEvent> list = new ArrayList<>();

        for(int hour = 0; hour<24; hour++){
            LocalTime time = LocalTime.of(hour, 0);
            ArrayList<Event> events = Event.eventsForDateAndTime(PresCal.getSelectedDate(), time);
            HourEvent hourEvent = new HourEvent(time, events);
            list.add(hourEvent);
        }
        return list;

    }

    public void prevDayAction(View view)
    {
        PresCal.SelDateMoveDay(-1);
        setDayView();
    }

    public void nextDayAction(View view)
    {
        PresCal.SelDateMoveDay(1);
        setDayView();
    }

}