package com.example.zeitplan_proyect.vista;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.zeitplan_proyect.R;
import com.example.zeitplan_proyect.model.Event;
import com.example.zeitplan_proyect.presenter.PresenterCalendarUtils;

import java.time.LocalDate;
import java.time.LocalTime;

@RequiresApi(api = Build.VERSION_CODES.O)
public class EventViewer extends AppCompatActivity {

    TextView titleTV, descrTV, dateTV, timeTV, typeTV, priorityTV, rememberTV;
    Button Salir;
    PresenterCalendarUtils PresCal;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_viewer);

        Salir.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Salir(view);
            }
        });


        titleTV = findViewById(R.id.titleTV);
        descrTV = findViewById(R.id.descrTV);
        dateTV = findViewById(R.id.dateTV);
        timeTV= findViewById(R.id.timeTV);
        typeTV= findViewById(R.id.typeTV);
        priorityTV = findViewById(R.id.priorityTV);
        rememberTV= findViewById(R.id.rememberTV);

        PresCal = PresenterCalendarUtils.getInstance();

        Intent intent = getIntent();
        Bundle b = intent.getExtras();
        if(b!=null){
            titleTV.setText((String) b.get("name"));
            descrTV.setText((String) b.get("descr"));
            dateTV.setText((String) b.get("date"));
            timeTV.setText((String) b.get("time"));
            priorityTV.setText((String) b.get("prior"));
            rememberTV.setText((String) b.get("rememb"));
        }


    }

    public void Salir(View view) {
        finish();
    }
}