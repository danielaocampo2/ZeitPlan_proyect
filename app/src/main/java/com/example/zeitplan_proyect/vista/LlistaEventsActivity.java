package com.example.zeitplan_proyect.vista;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;

import com.example.zeitplan_proyect.MainActivity2;
import com.example.zeitplan_proyect.R;
import com.example.zeitplan_proyect.model.Event;
import com.example.zeitplan_proyect.presenter.PresenterCalendarUtils;

import java.util.ArrayList;

@RequiresApi(api = Build.VERSION_CODES.O)
public class LlistaEventsActivity extends Fragment {

    private ListView eventListView;
    private Button calendarAction, semanalAction, dailyAction;
    private Spinner spinner;

    PresenterCalendarUtils PresCal;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        final View view = inflater.inflate(R.layout.activity_llista_events, container, false);
        super.onCreate(savedInstanceState);
        ((MainActivity2) getActivity()).getSupportActionBar().setTitle("Calendario");

        spinner = (Spinner) view.findViewById(R.id.spinner_orden);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getContext(), R.array.TipoOrden, android.R.layout.simple_spinner_item);
        spinner.setAdapter(adapter);
        eventListView = view.findViewById(R.id.eventListView);
        calendarAction= view.findViewById(R.id.calendarButt);
        semanalAction = view.findViewById(R.id.semanalButt);
        dailyAction = view.findViewById(R.id.dailyButt);
        PresCal = PresenterCalendarUtils.getInstance();

        setEventAdapter();

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

    private void setEventAdapter() {
        String orden = spinner.getSelectedItem().toString();
        ArrayList<Event> dailyEvents = Event.getEventsList();
        LlistaAdapter llistaAdapter = new LlistaAdapter(getContext(), dailyEvents);
        eventListView.setAdapter(llistaAdapter);
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