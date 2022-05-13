package com.example.zeitplan_proyect.vista;

import android.content.Context;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;

import com.example.zeitplan_proyect.model.CalendarUtils;
import com.example.zeitplan_proyect.model.Event;
import com.example.zeitplan_proyect.R;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@RequiresApi(api = Build.VERSION_CODES.O)
public class HourAdapter extends ArrayAdapter<HourEvent>
{

    public HourAdapter(@NonNull Context context, List<HourEvent> hourEvents) {
        super(context, 0, hourEvents);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        HourEvent event = getItem(position);

        if(convertView == null)
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.hour_cell,parent,false);

        setHour(convertView, event.time);
        setEvents(convertView, event.events);

        return convertView;
    }

    private void setHour(View convertView, LocalTime time) {
        TextView timeTV = convertView.findViewById(R.id.timeTV);
        timeTV.setText(CalendarUtils.formattedShortTime(time));
    }

    private void setEvents(View convertView, ArrayList<Event> events) {
        TextView event1= convertView.findViewById(R.id.event1);
        TextView event2= convertView.findViewById(R.id.event2);
        TextView event3= convertView.findViewById(R.id.event3);

        switch (events.size()){
            case 0:
                hideEvent(event1);
                hideEvent(event2);
                hideEvent(event3);
                break;
            case 1:
                setEvent(event1, events.get(0));
                hideEvent(event2);
                hideEvent(event3);
                break;
            case 2:
                setEvent(event1, events.get(0));
                setEvent(event2, events.get(1));
                hideEvent(event3);
                break;
            case 3:
                setEvent(event1, events.get(0));
                setEvent(event2, events.get(1));
                setEvent(event3, events.get(2));
                break;
            default:
                setEvent(event1, events.get(0));
                setEvent(event2, events.get(1));
                event3.setVisibility(View.VISIBLE);
                event3.setText("+ events");
                break;
        }
    }

    private void setEvent(TextView tv, Event event) {
        tv.setText(event.getName());
        tv.setVisibility(View.VISIBLE);

    }

    private void hideEvent(TextView event) {
        event.setVisibility(View.INVISIBLE);
    }
}
