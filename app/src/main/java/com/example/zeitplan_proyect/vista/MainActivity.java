package com.example.zeitplan_proyect.vista;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.DownloadManager;
import android.os.Bundle;

import com.example.zeitplan_proyect.DataBase.Firebase;
import com.example.zeitplan_proyect.R;
import com.google.firebase.firestore.Query;

public class MainActivity extends AppCompatActivity {
    RecyclerView reventos;
    EventAdapter eAdapter;
    Firebase db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        reventos=findViewById(R.id.reciclerVeventos);
        reventos.setLayoutManager(new LinearLayoutManager((this)));
        Query query =db.mFirestore.collection("evento");

    }
}