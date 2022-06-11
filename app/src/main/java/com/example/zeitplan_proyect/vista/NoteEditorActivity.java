package com.example.zeitplan_proyect.vista;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import androidx.fragment.app.Fragment;

import com.example.zeitplan_proyect.MainActivity2;
import com.example.zeitplan_proyect.R;
import com.example.zeitplan_proyect.vista.NoteActivity;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.HashSet;

public class NoteEditorActivity extends Fragment {
    int noteId;
    NoteActivity noteActivity = new NoteActivity();;


    public void onBackPressed(){



        super.getActivity().onBackPressed();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        final View view = inflater.inflate(R.layout.activity_note_editor, container, false);
        ((MainActivity2) getActivity()).getSupportActionBar().setTitle("Notas Rápidas");
        FloatingActionButton shareBtn =  ((MainActivity2) getActivity()).findViewById(R.id.share);
        shareBtn.setVisibility(View.GONE);

        EditText editText = view.findViewById(R.id.editText);

        // Cargamos los datos de NoteActivity
        Intent intent = this.requireActivity().getIntent();

        // Accessing the data using key and value
        noteId = getArguments().getInt("my_key");
        if (noteId != -1) {
            editText.setText(noteActivity.presenterNotes.getNoteId(noteId));
        } else {

            noteActivity.presenterNotes.addNote("");
            noteId = noteActivity.presenterNotes.getUserNotes().size() - 1;
            noteActivity.arrayAdapter.notifyDataSetChanged();

        }

        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                noteActivity.presenterNotes.getUserNotes().set(noteId, String.valueOf(charSequence));
                NoteActivity.arrayAdapter.notifyDataSetChanged();
                // Creamos un objeto SharedPreferences para almacenar el texto de las notas cuando se cierre la app.
                SharedPreferences sharedPreferences = getContext().getSharedPreferences("com.example.notes", Context.MODE_PRIVATE);
                HashSet<String> set = new HashSet(noteActivity.presenterNotes.getUserNotes());
                sharedPreferences.edit().putStringSet("notes", set).apply();
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });


        return view;
    }
}