package com.example.zeitplan_proyect.vista;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;

import com.example.zeitplan_proyect.DataBase.MyAdapter;
import com.example.zeitplan_proyect.R;
import com.example.zeitplan_proyect.model.Event;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

@RequiresApi(api = Build.VERSION_CODES.O)
public class MainActivity extends AppCompatActivity {
    RecyclerView reventos;
    ArrayList<Event> eventoArrayList;
    MyAdapter eAdapter;
    FirebaseFirestore mFirestore;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        progressDialog = new ProgressDialog(this);
        progressDialog.setCancelable(false);
        progressDialog.setMessage("tomando datos..");
        progressDialog.show();

        reventos=findViewById(R.id.reciclerVeventos);
        reventos.setHasFixedSize(true);
        reventos.setLayoutManager(new LinearLayoutManager(this));


        mFirestore=FirebaseFirestore.getInstance();

        eventoArrayList =new ArrayList<Event>();
        eAdapter = new MyAdapter(MainActivity.this,eventoArrayList, getApplicationContext());

        reventos.setAdapter(eAdapter);

        EventChangeListener();


    }

    private void EventChangeListener() {
        mFirestore.collection("evento").orderBy("titulo",Query.Direction.ASCENDING)
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                        if(error != null ){
                            if(progressDialog.isShowing())
                                progressDialog.dismiss();
                            Log.e("Firestore error", error.getMessage() );
                            return;
                        }
                        for(DocumentChange dc: value.getDocumentChanges()){
                            if(dc.getType() == DocumentChange.Type.ADDED){
                                eventoArrayList.add(dc.getDocument().toObject(Event.class));
                            }
                            eAdapter.notifyDataSetChanged();
                            if(progressDialog.isShowing())
                                progressDialog.dismiss();
                        }
                    }
                });
    }


}