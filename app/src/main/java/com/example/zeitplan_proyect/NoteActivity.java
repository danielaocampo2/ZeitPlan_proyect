package com.example.zeitplan_proyect;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.zeitplan_proyect.vista.MainActivity2;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.HashSet;


public class NoteActivity extends Fragment {

    static ArrayList<String> notes = new ArrayList<>();
    static ArrayAdapter arrayAdapter;


    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        super.onOptionsItemSelected(item);

        if (item.getItemId() == R.id.add_note) {

            // Moverse de NoteActivity a NotesEditorActivity
            Intent intent = new Intent(getContext(), NoteEditorActivity.class);
            startActivity(intent);
            return true;
        }

        return false;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        final View view = inflater.inflate(R.layout.activity_note, container, false);
        ((MainActivity2) getActivity()).getSupportActionBar().setTitle("Notas Rápidas");
        FloatingActionButton shareBtn =  ((MainActivity2) getActivity()).findViewById(R.id.share);
        shareBtn.setVisibility(View.GONE);
        ImageButton add_button = view.findViewById(R.id.btn_add);
        ListView listView = view.findViewById(R.id.listView);
        SharedPreferences sharedPreferences = getContext().getSharedPreferences("com.example.notes", Context.MODE_PRIVATE);
        HashSet<String> set = (HashSet<String>) sharedPreferences.getStringSet("notes", null);

        if (set == null) {

            notes.add("Example note");
        } else {
            notes = new ArrayList(set);
        }

        // Usando el listView ArrayAdapter para mostrar el contenido de la nota.
        arrayAdapter = new ArrayAdapter(getContext(), android.R.layout.simple_expandable_list_item_1, notes);

        listView.setAdapter(arrayAdapter);

        add_button.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v) {
                NoteEditorActivity noteEditorActivity = new NoteEditorActivity();
                Bundle bundle = new Bundle();
                bundle.putInt("my_key", notes.size());
                notes.add("");
                noteEditorActivity.setArguments(bundle);
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.fragment, noteEditorActivity);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();

            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                // Ir de NoteActivity a NotesEditorActivity
                Bundle bundle = new Bundle();
                bundle.putInt("my_key", i);
                NoteEditorActivity noteEditorActivity = new NoteEditorActivity();
                noteEditorActivity.setArguments(bundle);
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.fragment, noteEditorActivity);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        });

        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {

                final int itemToDelete = i;
                // Borrar una nota de la app.
                new AlertDialog.Builder(getContext())
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .setTitle("¿Estas seguro?")
                        .setMessage("¿Desea eliminar esta nota?")
                        .setPositiveButton("Si", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                notes.remove(itemToDelete);
                                arrayAdapter.notifyDataSetChanged();
                                SharedPreferences sharedPreferences = getContext().getSharedPreferences("com.example.notes", Context.MODE_PRIVATE);
                                HashSet<String> set = new HashSet(NoteActivity.notes);
                                sharedPreferences.edit().putStringSet("notes", set).apply();
                                Toast.makeText(getContext(), "Nota Eliminada", Toast.LENGTH_SHORT).show();
                            }
                        }).setNegativeButton("No", null).show();
                return true;
            }
        });
        return view;
    }
}