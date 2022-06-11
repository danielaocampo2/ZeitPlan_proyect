package com.example.zeitplan_proyect.DataBase;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Build;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import com.example.zeitplan_proyect.R;
import com.example.zeitplan_proyect.model.Event;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;


import java.util.ArrayList;
@RequiresApi(api = Build.VERSION_CODES.O)
public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {
    Context context;
    ArrayList<Event> eventoArrayList;
    Context ensayo;

    public MyAdapter(Context context, ArrayList<Event> eventArrayList, Context nuevo) {
        this.context = context;
        this.eventoArrayList = eventArrayList;
        this.ensayo=nuevo;
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
        String fecha = event.getFecha_inicio();
        String hora = event.getTiempoIni() ;//+ " - " + event.getTiempoFi();
        holder.fechaYHora.setText(fecha );//+ ", "+ hora);
        holder.tipo.setText(event.getTipo()); // holder.Age.setText(String.valueOf(user.age))
        holder.prioridad.setText(String.valueOf(event.getPrioridad())+"%");
        holder.tx_hora.setText(hora);
        holder.tx_idEvento.setText("holi dani");


        holder.btn_eliminar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                //Toast.makeText(context, "Debe elegir fecha y hora",Toast.LENGTH_LONG).show();
                AlertDialog.Builder dialogo1 = new AlertDialog.Builder(ensayo);
                dialogo1.setTitle("¿Está seguro que desea eliminarlo? ");
               // dialogo1.setMessage("Se eliminará "+ holder.titulo);
                dialogo1.setCancelable(false);
                dialogo1.setPositiveButton("Confirmar", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialogo1, int id) {
                        // Elimar nota
                        Log.i("holi", "onClick: "+holder.tx_idEvento.getText());

                    }
                });
                dialogo1.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialogo1, int id) {
                        Toast.makeText(context, "Cancelado", Toast.LENGTH_LONG).show();
                    }
                });
                dialogo1.show();
            }
        });

        holder.btn_editar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        holder.btn_ver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

    }

    @Override
    public int getItemCount() {
        return eventoArrayList.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{

        TextView titulo, prioridad, tipo, fechaYHora, tx_hora, tx_idEvento;
        ImageButton btn_eliminar, btn_ver, btn_editar;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            titulo= itemView.findViewById(R.id.tx_titulo);
            prioridad= itemView.findViewById(R.id.tx_prioridad);
            tipo= itemView.findViewById(R.id.tx_tipo);
            fechaYHora=itemView.findViewById(R.id.tx_fecha);
            tx_hora=itemView.findViewById(R.id.txt_hora);
            btn_eliminar=itemView.findViewById(R.id.btn_eliminar);
            btn_ver=itemView.findViewById(R.id.btn_ver);
            btn_editar=itemView.findViewById(R.id.btn_editar);
            tx_idEvento=itemView.findViewById(R.id.tx_idEvento);
        }
    }
}