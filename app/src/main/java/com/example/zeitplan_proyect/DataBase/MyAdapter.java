package com.example.zeitplan_proyect.DataBase;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.zeitplan_proyect.R;
import com.example.zeitplan_proyect.model.EventoNuevo;

import java.util.ArrayList;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {
    Context context;
    ArrayList<EventoNuevo> eventoNuevoArrayList;

    public MyAdapter(Context context, ArrayList<EventoNuevo> eventoNuevoArrayList) {
        this.context = context;
        this.eventoNuevoArrayList = eventoNuevoArrayList;
    }

    @NonNull
    @Override
    public MyAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.view_event_single,parent,false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        EventoNuevo eventoNuevo = eventoNuevoArrayList.get(position);
        holder.descripcion.setText(eventoNuevo.getDescripcion());
        holder.titulo.setText(eventoNuevo.getTitulo());
        holder.idUser.setText(eventoNuevo.getIdUser()); // holder.Age.setText(String.valueOf(user.age))
    }

    @Override
    public int getItemCount() {
        return eventoNuevoArrayList.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{

        TextView titulo, descripcion, idUser;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            titulo= itemView.findViewById(R.id.tx_titulo);
            descripcion= itemView.findViewById(R.id.tx_descripcion);
            idUser= itemView.findViewById(R.id.tx_tipo);
        }
    }
}
