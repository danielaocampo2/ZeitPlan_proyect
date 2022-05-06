package com.example.zeitplan_proyect;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Calendar;

@RequiresApi(api = Build.VERSION_CODES.O)
public class EventEditActivity extends AppCompatActivity {

    private EditText eventNameET;
    private TextView eventDateTV, eventTimeTV;
    TimePickerDialog.OnTimeSetListener setListenerTimeEvent;
    DatePickerDialog.OnDateSetListener setListenerDateEvent;

    private LocalTime time;
    private LocalDate date;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_edit);
        initWidgets();
        time = LocalTime.now();
        eventTimeTV.setText(CalendarUtils.formattedShortTime(time));
        date = CalendarUtils.selectedDate;
        eventDateTV.setText(date.getDayOfMonth() + "/" + date.getMonthValue() + "/" + date.getYear());

        eventDateTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerDialog datePicker = new DatePickerDialog(
                        EventEditActivity.this, android.R.style.Theme_Holo_Light_Dialog_MinWidth, setListenerDateEvent, date.getYear(), date.getMonthValue() - 1, date.getDayOfMonth());
                datePicker.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                datePicker.show();

            }
        });
        setListenerDateEvent = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                String day_month_year = datePicker.getDayOfMonth() + "/" + (datePicker.getMonth()+1) + "/" + datePicker.getYear();
                eventDateTV.setText(day_month_year);
                date = LocalDate.of(datePicker.getYear(), datePicker.getMonth() + 1, datePicker.getDayOfMonth());
            }
        };

        eventTimeTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TimePickerDialog timePicker = new TimePickerDialog(
                            EventEditActivity.this, android.R.style.Theme_Holo_Light_Dialog_MinWidth, setListenerTimeEvent, time.getHour(), time.getMinute(), true);
                timePicker.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                timePicker.show();
            }
        });
        setListenerTimeEvent = new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int i, int e) {
                String hour_time = timePicker.getHour() + ":" + timePicker.getMinute();
                eventTimeTV.setText(hour_time);
                time = LocalTime.of(timePicker.getHour(), timePicker.getMinute());
            }
        };
    }



    private void initWidgets()
    {
        eventNameET = findViewById(R.id.eventNameET);
        eventDateTV = findViewById(R.id.eventDateTV);
        eventTimeTV = findViewById(R.id.eventTimeTV);
    }

    public void saveEventAction(View view)
    {
        String eventName = eventNameET.getText().toString();
        if(eventName.equals("")) eventName = "Evento " + (Event.eventsList.size()+1);
        Event newEvent = new Event(eventName, date, time);
        Event.eventsList.add(newEvent);
        finish();
    }
}