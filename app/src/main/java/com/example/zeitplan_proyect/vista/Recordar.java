package com.example.zeitplan_proyect.vista;

import androidx.appcompat.app.AppCompatActivity;
import androidx.work.Data;
import androidx.work.WorkManager;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.zeitplan_proyect.R;
import com.example.zeitplan_proyect.model.WorkManagerNoti;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Calendar;
import java.util.UUID;

public class Recordar extends AppCompatActivity {

    Button seleFecha, seleHora, guardar;
    TextView tv_fecha, tv_hora;


    Calendar actual = Calendar.getInstance(); // Para declarar la fecha actual
    Calendar calendar =Calendar.getInstance();




    private int minutos,hora,dia,mes,anio;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recordar);

        seleFecha=findViewById(R.id.btn_seleFecha);
        seleHora=findViewById(R.id.btn_seleHora);
        tv_fecha=findViewById(R.id.tv_fecha);
        tv_hora=findViewById(R.id.tv_hora);
        guardar=findViewById(R.id.btn_guardar);

        seleFecha.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                anio=actual.get(Calendar.YEAR);
                mes=actual.get(Calendar.MONTH);
                dia=actual.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog datePickerDialog =new DatePickerDialog(view.getContext(), R.style.DatePickerStyle,
                        new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int y, int m, int d) {
                        calendar.set(Calendar.DAY_OF_MONTH,d);
                        calendar.set(Calendar.MONTH,m);
                        calendar.set(Calendar.YEAR,y);

                        //para formatear el texto
                        SimpleDateFormat format =new SimpleDateFormat("dd/MM/yyyy");
                        String strDate = format.format(calendar.getTime());
                        tv_fecha.setText(strDate);

                    }
                },anio,mes,dia);
                datePickerDialog.show();

            }
        });

        seleHora.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                hora=actual.get(calendar.HOUR_OF_DAY);
                minutos=actual.get(Calendar.MINUTE);
                TimePickerDialog timePickerDialog=new TimePickerDialog(view.getContext(), R.style.DatePickerStyle, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int h, int m) {
                        calendar.set(calendar.HOUR_OF_DAY, h);
                        calendar.set(calendar.MINUTE,m);

                        // ara ponerlo en el textview
                        tv_hora.setText(String.format("%02d:%02d",h,m));

                    }
                },hora,minutos,true);
                timePickerDialog.show();

            }
        });
        guardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String tag =generateKey();
                //Pasa el valor de la fecha y hora a milisegundos, el sistem es para que workmanager trabajae de manera correcta
                Long alerttime=calendar.getTimeInMillis() - System.currentTimeMillis();
                int random =(int) (Math.random()*50+1);

                Data data=guardarData("soy un titulo", "soy un detalle", random);
                WorkManagerNoti.GuardarNoti(alerttime,data,tag);


                Toast.makeText(Recordar.this, "Alarma guardada",Toast.LENGTH_SHORT).show();

            }
        });




    }

    private void eliminarNoti(String tag){
        WorkManager.getInstance(this).cancelAllWorkByTag(tag);
        Toast.makeText(Recordar.this, "Alarma eliminada",Toast.LENGTH_SHORT).show();

    }

    // genera numero aleatorio
    private String generateKey(){
        return UUID.randomUUID().toString();

    }

    private Data guardarData(String titulo, String detalle, int id_noti){
        return new Data.Builder()
                .putString("titulo",titulo)
                .putString("detalle",detalle)
                .putInt("id_noti",id_noti).build();
    }






}