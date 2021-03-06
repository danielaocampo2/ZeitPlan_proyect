package com.example.zeitplan_proyect.DataBase;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.example.zeitplan_proyect.MainActivity2;
import com.example.zeitplan_proyect.R;
import com.example.zeitplan_proyect.model.Event;
import com.example.zeitplan_proyect.vista.Activity_add_asignatura;
import com.example.zeitplan_proyect.vista.Activity_crear;
import com.example.zeitplan_proyect.vista.LlistaEventsActivity;
import com.example.zeitplan_proyect.vista.RegistroActivity;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;


import java.util.ArrayList;
@RequiresApi(api = Build.VERSION_CODES.O)
public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {
    Context context;
    ArrayList<Event> eventoArrayList;
    Context context2;
    private FirebaseFirestore mFirestore =FirebaseFirestore.getInstance();
    LlistaEventsActivity lista = new LlistaEventsActivity();


    public MyAdapter(Context context, ArrayList<Event> eventArrayList, Context nuevo) {
        this.context = context;
        this.eventoArrayList = eventArrayList;
        this.context2 =nuevo;


        Log.i( "MyAdapter: ","context  "+context);
        Log.i( "MyAdapter: ","nuevo   "+nuevo);


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
        String hora = event.getTiempoIni();// + " - " + event.getTiempoFi();
        holder.fechaYHora.setText(fecha );//+ ", "+ hora);
        holder.tipo.setText(event.getTipo()); // holder.Age.setText(String.valueOf(user.age))
        holder.prioridad.setText(String.valueOf(event.getPrioridad())+"%");
        holder.tx_hora.setText(hora);
        holder.tx_idEvento.setText(event.getIdEvento());



        holder.btn_eliminar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {



                //Toast.makeText(context, "Debe elegir fecha y hora",Toast.LENGTH_LONG).show();
                AlertDialog.Builder dialogo1 = new AlertDialog.Builder(context2);
                dialogo1.setTitle("??Est?? seguro que desea eliminarlo? ");
               // dialogo1.setMessage("Se eliminar?? "+ holder.titulo);
                dialogo1.setCancelable(false);
                dialogo1.setPositiveButton("Confirmar", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialogo1, int id) {
                        // Elimar nota
                        Log.i("holi", "onClick: "+holder.tx_idEvento.getText());
                        deleteEvent(holder.tx_idEvento.getText().toString());
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

                //Crear bundle, que son los datos que pasaremos
                Bundle datosAEnviar = new Bundle();
                // datos en formato clave, valor
                String id = holder.tx_idEvento.getText().toString();
                datosAEnviar.putString("id", id);
                datosAEnviar.putString("funcion", "editar");
                //  m??s datos..
               // datosAEnviar.putInt("edad", 21);
               // datosAEnviar.putString("nombre", "Parzibyte");

                FragmentActivity activity = (FragmentActivity) context2;
                Activity_crear activity_crear = new Activity_crear();

                activity_crear.setArguments(datosAEnviar);
                FragmentManager fragmentManager = activity.getSupportFragmentManager();

                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                //getParentFragmentManager().setFragmentResult("requestKey", result);
                fragmentTransaction.replace(R.id.fragment, activity_crear);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        });
        holder.btn_ver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle datosAEnviar = new Bundle();
                // datos en formato clave, valor
                String id = holder.tx_idEvento.getText().toString();
                datosAEnviar.putString("id", id);
                datosAEnviar.putString("funcion", "ver");
                //  m??s datos..
                // datosAEnviar.putInt("edad", 21);
                // datosAEnviar.putString("nombre", "Parzibyte");

                FragmentActivity activity = (FragmentActivity) context2;
                Activity_crear activity_crear = new Activity_crear();

                activity_crear.setArguments(datosAEnviar);
                FragmentManager fragmentManager = activity.getSupportFragmentManager();

                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                //getParentFragmentManager().setFragmentResult("requestKey", result);
                fragmentTransaction.replace(R.id.fragment, activity_crear);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        });

    }

    public void deleteEvent(String id){
        mFirestore.collection("evento").document(id).delete().addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                Toast.makeText(context, "Eliminado correctamente", Toast.LENGTH_LONG).show();
                //notifyDataSetChanged();

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(context, "Error al eliminar", Toast.LENGTH_LONG).show();

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