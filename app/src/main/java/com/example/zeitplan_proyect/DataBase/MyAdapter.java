package com.example.zeitplan_proyect.DataBase;

import android.content.Context;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import com.example.zeitplan_proyect.R;
import com.example.zeitplan_proyect.model.Event;

import java.util.ArrayList;
@RequiresApi(api = Build.VERSION_CODES.O)
public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {
    Context context;
    ArrayList<Event> eventoArrayList;

    public MyAdapter(Context context, ArrayList<Event> eventArrayList) {
        this.context = context;
        this.eventoArrayList = eventArrayList;
    }

    @NonNull
    @Override
    public MyAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.view_event_single,parent,false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Event event = eventoArrayList.get(position);
        holder.descripcion.setText(event.getDescripcion());
        holder.titulo.setText(event.getNombre());
        holder.idUser.setText(event.getId()); // holder.Age.setText(String.valueOf(user.age))
    }

    @Override
    public int getItemCount() {
        return eventoArrayList.size();
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
