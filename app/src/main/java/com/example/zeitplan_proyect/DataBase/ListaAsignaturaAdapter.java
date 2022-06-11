package com.example.zeitplan_proyect.DataBase;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.example.zeitplan_proyect.Fragments.FragmentHelper;
import com.example.zeitplan_proyect.MainActivity2;
import com.example.zeitplan_proyect.R;
import com.example.zeitplan_proyect.model.Asignatura;
import com.example.zeitplan_proyect.vista.ActivityAsignatura;
import com.example.zeitplan_proyect.vista.Activity_add_asignatura;
import com.example.zeitplan_proyect.vista.CalculadoraActivity;
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

        View addView = LayoutInflater.from(context).inflate(R.layout.fila_asignatura,parent,false);
        return new ListaAsignaturaAdapter.MyViewHolder(addView);
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
        RelativeLayout viewF, viewB;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            titulo = itemView.findViewById(R.id.textViewAsignaturaNombre);
            viewF = itemView.findViewById(R.id.rl);
            viewB = itemView.findViewById(R.id.view_background);

        }

    }

    public void removeItem(int position){
        asignaturaArrayList.remove(position);
        notifyItemRemoved(position);
    }

    public void restoreItem(Asignatura asignatura, int position){
        asignaturaArrayList.add(position,asignatura);
        notifyItemInserted(position);
    }


}
