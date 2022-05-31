package com.example.zeitplan_proyect.vista;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
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
import com.example.zeitplan_proyect.presenter.PresenterCalendarUtils;

import java.time.LocalDate;
import java.util.List;

@RequiresApi(api = Build.VERSION_CODES.O)
public class EventAdapter extends ArrayAdapter<Event>
{

    PresenterCalendarUtils PresCal;
    Context context;

    public EventAdapter(@NonNull Context context, List<Event> events) {
        super(context, 0, events);
        this.PresCal = PresenterCalendarUtils.getInstance();
        this.context = context;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        Event event = getItem(position);
        if(convertView == null)
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.event_cell,parent,false);
        TextView eventCellTV = convertView.findViewById(R.id.eventCellTV);
        String eventTitle = PresCal.formattedShortTime(event.getTimeIn()) + " - " + PresCal.formattedShortTime(event.getTimeFi())+ " " + event.getNombre();
        eventCellTV.setText(eventTitle);
        eventCellTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, EventViewer.class);
                intent.putExtra("name", event.getNombre());
                intent.putExtra("descr", event.getDescripcion());
                intent.putExtra("date", PresCal.formattedDate(event.getDate()));
                intent.putExtra("time", PresCal.formattedShortTime(event.getTimeIn()) + " - " + PresCal.formattedShortTime(event.getTimeFi()));
                intent.putExtra("prior", event.getPriority() + "%");
                intent.putExtra("prior", event.getType());
                if(event.isRemember()) intent.putExtra("rememb", "✓");
                else                   intent.putExtra("rememb", "✕");
                context.startActivity(intent);
            }
        });

        return convertView;
    }


}
