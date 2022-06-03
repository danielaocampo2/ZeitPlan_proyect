package com.example.zeitplan_proyect.DataBase;

import android.content.Context;
import android.os.Build;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import com.example.zeitplan_proyect.R;
import com.example.zeitplan_proyect.model.Event;
import com.example.zeitplan_proyect.model.Login;

import java.time.format.DateTimeFormatter;
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

        holder.titulo.setText(event.getNombre());
        holder.tipo.setText(event.getType()); // holder.Age.setText(String.valueOf(user.age))
        holder.prioridad.setText(String.valueOf(event.getPriority()));
        DateTimeFormatter JEFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        String formattedString =event.getDate().format(JEFormatter);
        holder.fecha.setText(formattedString);


    }

    @Override
    public int getItemCount() {
        return eventoArrayList.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{

        TextView titulo, prioridad, tipo, fecha;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            titulo= itemView.findViewById(R.id.tx_titulo);
            prioridad= itemView.findViewById(R.id.tx_prioridad);
            tipo= itemView.findViewById(R.id.tx_tipo);
            fecha=itemView.findViewById(R.id.tx_fecha);
        }
    }
}