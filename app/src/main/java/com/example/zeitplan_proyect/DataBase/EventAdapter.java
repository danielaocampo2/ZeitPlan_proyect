package com.example.zeitplan_proyect.DataBase;

import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.RequiresApi;

import com.example.zeitplan_proyect.R;


import com.example.zeitplan_proyect.model.Event;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;

public class EventAdapter extends FirestoreRecyclerAdapter<Event, EventAdapter.ViewHolder>
{

    /**
     * Create a new RecyclerView adapter that listens to a Firestore Query.  See {@link
     * FirestoreRecyclerOptions} for configuration options.
     *
     * @param options
     */
    public EventAdapter(@NonNull @androidx.annotation.NonNull FirestoreRecyclerOptions<Event> options) {
        super(options);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onBindViewHolder(@NonNull @androidx.annotation.NonNull ViewHolder holder, int position, @NonNull @androidx.annotation.NonNull Event model) {
        holder.titulo.setText(model.getName());
        holder.descripcion.setText(model.getDescription());
        holder.tipo.setText(model.getPriority());
    }

    @NonNull
    @androidx.annotation.NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull @androidx.annotation.NonNull ViewGroup viewGroup, int viewType) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.view_event_single,viewGroup,false);
        return new ViewHolder(v);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView titulo, descripcion, tipo;

        public ViewHolder(@NonNull @androidx.annotation.NonNull View itemView) {
            super(itemView);
            titulo= itemView.findViewById(R.id.tx_titulo);
            descripcion= itemView.findViewById(R.id.tx_descripcion);
            tipo= itemView.findViewById(R.id.tx_tipo);

        }
    }
}
