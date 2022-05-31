package com.example.zeitplan_proyect.DataBase;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.zeitplan_proyect.R;


import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.zeitplan_proyect.model.EventoNuevo;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;

public class EventAdapter  extends FirestoreRecyclerAdapter<EventoNuevo, EventAdapter.ViewHolder> {


    /**
     * Create a new RecyclerView adapter that listens to a Firestore Query.  See {@link
     * FirestoreRecyclerOptions} for configuration options.
     *
     * @param options
     */
    public EventAdapter(@NonNull FirestoreRecyclerOptions<EventoNuevo> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull ViewHolder holder, int position, @NonNull EventoNuevo model) {
        holder.titulo.setText(model.getTitulo());
        holder.descripcion.setText(model.getDescripcion());
        holder.tipo.setText(model.getPrioridad());
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_event_single,parent,false);
        return new ViewHolder(v);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView titulo, descripcion, tipo;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            titulo= itemView.findViewById(R.id.tx_titulo);
            descripcion= itemView.findViewById(R.id.tx_descripcion);
            tipo= itemView.findViewById(R.id.tx_tipo);
        }
    }
}

