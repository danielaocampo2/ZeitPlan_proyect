package com.example.zeitplan_proyect.DataBase;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.example.zeitplan_proyect.R;
import com.example.zeitplan_proyect.model.Asignatura;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;

import java.util.ArrayList;

public class ListaAsignaturaAdapter extends RecyclerView.Adapter<ListaAsignaturaAdapter.MyViewHolder> {

    Context context;
    ArrayList<Asignatura> asignaturaArrayList;
    Firebase firebase = new Firebase();


    public ListaAsignaturaAdapter(Context context, ArrayList<Asignatura> asignaturaArrayList) {
        this.context = context;
        this.asignaturaArrayList = asignaturaArrayList;
    }

    @NonNull
    @Override
    public ListaAsignaturaAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.fila_asignatura,parent,false);
        ImageButton buttonRemove = v.findViewById(R.id.configuracion);
        ImageButton buttonCalculadora = v.findViewById(R.id.acceder_calculadora);

        final View.OnClickListener thisListener = new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                TextView nombre = v.findViewById(R.id.textViewAsignaturaNombre);
                firebase.mFirestore.collection("Asignaturas").document(nombre.getText().toString())
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
                ((RecyclerView)v).removeView(v);
            }
        };
        buttonRemove.setOnClickListener(thisListener);

        final View.OnClickListener thisListener2 = new View.OnClickListener(){
            @Override
            public void onClick(View view) {

            }
        };
        buttonCalculadora.setOnClickListener(thisListener2);

        return new ListaAsignaturaAdapter.MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ListaAsignaturaAdapter.MyViewHolder holder, int position) {
        Asignatura asignatura = asignaturaArrayList.get(position);
        holder.titulo.setText(asignatura.getNombre());
    }

    @Override
    public int getItemCount() {
        return asignaturaArrayList.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{

        TextView titulo;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            titulo = itemView.findViewById(R.id.textViewAsignaturaNombre);
        }

    }

}
