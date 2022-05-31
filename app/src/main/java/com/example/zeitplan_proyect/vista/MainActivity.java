package com.example.zeitplan_proyect.vista;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.example.zeitplan_proyect.R;
import com.example.zeitplan_proyect.model.EventoNuevo;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;


public class MainActivity extends AppCompatActivity {
    RecyclerView reventos;
    EventAdapter eAdapter;
    FirebaseFirestore mFirestore;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        reventos=findViewById(R.id.reciclerVeventos);
        mFirestore=FirebaseFirestore.getInstance();
        //PERMITE HACER LA CONEXION
        reventos.setLayoutManager(new LinearLayoutManager((this)));
        Query query =mFirestore.collection("evento");
        FirestoreRecyclerOptions<EventoNuevo> firestoreRecyclerOptions = new FirestoreRecyclerOptions.Builder<EventoNuevo>().setQuery(query, EventoNuevo.class).build();
        eAdapter = new EventAdapter(firestoreRecyclerOptions);
        eAdapter.notifyDataSetChanged();
        reventos.setAdapter(eAdapter);

    }

    @Override
    protected void onStart() {
        super.onStart();
        eAdapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        eAdapter.stopListening();
    }
}