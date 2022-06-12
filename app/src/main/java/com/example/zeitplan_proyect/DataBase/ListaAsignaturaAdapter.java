package com.example.zeitplan_proyect.DataBase;

import android.content.Context;
import android.content.Intent;
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
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.example.zeitplan_proyect.Fragments.FragmentHelper;
import com.example.zeitplan_proyect.MainActivity2;
import com.example.zeitplan_proyect.R;
import com.example.zeitplan_proyect.model.Asignatura;
import com.example.zeitplan_proyect.vista.ActivityAsignatura;
import com.example.zeitplan_proyect.vista.Activity_add_asignatura;
import com.example.zeitplan_proyect.vista.Activity_crear;
import com.example.zeitplan_proyect.vista.CalculadoraActivity;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;

import java.util.ArrayList;

public class ListaAsignaturaAdapter extends RecyclerView.Adapter<ListaAsignaturaAdapter.MyViewHolder>{

    Context context, context1;
    ArrayList<Asignatura> asignaturaArrayList;


    public ListaAsignaturaAdapter(Context context, ArrayList<Asignatura> asignaturaArrayList, Context context1) {
        this.context = context;
        this.context1 = context1;
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
        holder.calculadora.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentActivity activity = (FragmentActivity) context1;
                CalculadoraActivity calculadoraActivity = new CalculadoraActivity();

                FragmentManager fragmentManager = activity.getSupportFragmentManager();

                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.fragment, calculadoraActivity);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        });

    }


    @Override
    public int getItemCount() {
        return asignaturaArrayList.size();
    }




    public static class MyViewHolder extends RecyclerView.ViewHolder{

        TextView titulo;
        RelativeLayout viewF, viewB;
        ImageButton calculadora;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            titulo = itemView.findViewById(R.id.textViewAsignaturaNombre);
            viewF = itemView.findViewById(R.id.rl);
            viewB = itemView.findViewById(R.id.view_background);
            calculadora = itemView.findViewById(R.id.acceder_calculadora);

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
