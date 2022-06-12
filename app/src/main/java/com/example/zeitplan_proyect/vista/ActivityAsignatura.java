package com.example.zeitplan_proyect.vista;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.example.zeitplan_proyect.DataBase.CallBackItemTouch;
import com.example.zeitplan_proyect.DataBase.Firebase;
import com.example.zeitplan_proyect.DataBase.ListaAsignaturaAdapter;
import com.example.zeitplan_proyect.DataBase.MyItemTouchHelperCallback;
import com.example.zeitplan_proyect.Fragments.FragmentHelper;
import com.example.zeitplan_proyect.MainActivity2;
import com.example.zeitplan_proyect.R;
import com.example.zeitplan_proyect.model.Asignatura;
import com.example.zeitplan_proyect.model.Event;
import com.example.zeitplan_proyect.model.Fechas;
import com.example.zeitplan_proyect.presenter.PresenterAsignatura;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class ActivityAsignatura extends Fragment implements CallBackItemTouch {

    FloatingActionButton boton_add;
    RecyclerView recyclerView;
    ScrollView layout;
    TextView nombre;
    ArrayList<Asignatura> asignaturas;
    ListaAsignaturaAdapter listaAsignaturaAdapter;
    FirebaseFirestore mFirestore;
    Firebase db = new Firebase();


    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        final View view = inflater.inflate(R.layout.activity_asignatura, container, false);
        ((MainActivity2) getActivity()).getSupportActionBar().setTitle("Asignatura");

        boton_add = view.findViewById(R.id.btn_addAsignatura);
        nombre = view.findViewById(R.id.textViewAsignaturaNombre);

        layout = view.findViewById(R.id.Fragment_asignatura);
        recyclerView = view.findViewById(R.id.RV);

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        mFirestore = FirebaseFirestore.getInstance();
        asignaturas = new ArrayList<>();
        listaAsignaturaAdapter = new ListaAsignaturaAdapter(getActivity().getApplicationContext(), asignaturas, getContext());
        recyclerView.setAdapter(listaAsignaturaAdapter);
        ItemTouchHelper.Callback callback = new MyItemTouchHelperCallback(this);
        ItemTouchHelper touchHelper = new ItemTouchHelper(callback);
        touchHelper.attachToRecyclerView(recyclerView);
        EventChangeListener();

        boton_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Activity_add_asignatura activity_add_asignatura = new Activity_add_asignatura();
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.fragment, activity_add_asignatura);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        });

        return view;
    }


    private void EventChangeListener() {
        mFirestore.collection("Asignaturas").whereEqualTo("idUser",db.getIdUser())
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                        if(error != null ){
                            Log.e("Firestore error", error.getMessage() );
                            return;
                        }
                        for(DocumentChange dc: value.getDocumentChanges()){
                            if(dc.getType() == DocumentChange.Type.ADDED){
                                Asignatura asignatura = new Asignatura((String) dc.getDocument().get("Fecha inicio"), (String)dc.getDocument().get("Fecha final"), (String)dc.getDocument().get("Name"), (String)dc.getDocument().get("Descripcion"), (ArrayList<String>)dc.getDocument().get("Dias semana"), (ArrayList<String>) dc.getDocument().get("Horas de inicio"), (ArrayList<String>) dc.getDocument().get("Horas de Final"));
                                asignaturas.add(asignatura);
                            }
                            listaAsignaturaAdapter.notifyDataSetChanged();
                        }
                    }
                });

    }


    @Override
    public void itemTouchOnMode(int oldPosition, int newPosition) {
        asignaturas.add(newPosition, asignaturas.remove(oldPosition));
        listaAsignaturaAdapter.notifyItemMoved(oldPosition, newPosition);
    }

    @Override
    public void onSwiped(RecyclerView.ViewHolder viewHolder, int position) {
        String nombre = asignaturas.get(viewHolder.getAdapterPosition()).getNombre();
        final Asignatura deleteItem = asignaturas.get(viewHolder.getAdapterPosition());
        final int deletedIndex = viewHolder.getAdapterPosition();
        listaAsignaturaAdapter.removeItem(viewHolder.getAdapterPosition());

        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setMessage("¿Esta seguro de que desea eliminar?");
        builder.setTitle("Eliminar Asignatura");
        builder.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                db.mFirestore.collection("Asignaturas").document(nombre)
                        .delete()
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Log.d("TAG", "DocumentSnapshot successfully deleted!");
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Log.w("TAG", "Error deleting document", e);
                            }
                        });
            }
        });
        builder.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                listaAsignaturaAdapter.restoreItem(deleteItem, deletedIndex);
            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();

    }
}