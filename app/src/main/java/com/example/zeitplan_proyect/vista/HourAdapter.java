package com.example.zeitplan_proyect.vista;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.zeitplan_proyect.MainActivity2;
import com.example.zeitplan_proyect.model.Event;
import com.example.zeitplan_proyect.R;
import com.example.zeitplan_proyect.model.HourEvent;
import com.example.zeitplan_proyect.presenter.PresenterCalendarUtils;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

@RequiresApi(api = Build.VERSION_CODES.O)
public class HourAdapter extends ArrayAdapter<Integer>
{

    PresenterCalendarUtils PresCal;
    ArrayList<Event> events;

    public HourAdapter(@NonNull Context context, ArrayList<Event> events, ArrayList<Integer> hours) {
        super(context, 0, hours);
        PresCal = PresenterCalendarUtils.getInstance();
        this.events = events;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        int hour = getItem(position);

        if(convertView == null)
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.hour_cell,parent,false);

        setHour(convertView, LocalTime.of(hour, 0));
        setEvents(convertView, hour);

        return convertView;
    }

    private void setHour(View convertView, LocalTime time) {
        TextView timeTV = convertView.findViewById(R.id.timeTV);
        timeTV.setText(PresCal.formattedShortTime(time));
    }

    private void setEvents(View convertView, int hour) {
        TextView event1= convertView.findViewById(R.id.event1);
        TextView event2= convertView.findViewById(R.id.event2);
        TextView event3= convertView.findViewById(R.id.event3);


        ArrayList<Event> eventsHour = new ArrayList<>();
        for(Event event : events){
            int eventHourIn = event.getTiempoIniLT().getHour();
            int eventHourFi = 24;//event.getTiempoFiLTHour();
            if (eventHourIn <= hour && hour <= eventHourFi) eventsHour.add(event);
        }

        switch (eventsHour.size()){
            case 0:
                hideEvent(event1);
                hideEvent(event2);
                hideEvent(event3);
                break;
            case 1:
                setEvent(event1, eventsHour.get(0));
                hideEvent(event2);
                hideEvent(event3);
                break;
            case 2:
                setEvent(event1, eventsHour.get(0));
                setEvent(event2, eventsHour.get(1));
                hideEvent(event3);
                break;
            case 3:
                setEvent(event1, eventsHour.get(0));
                setEvent(event2, eventsHour.get(1));
                setEvent(event3, eventsHour.get(2));
                break;
            default:
                setEvent(event1, eventsHour.get(1));
                setEvent(event2, eventsHour.get(2));
                event3.setVisibility(View.VISIBLE);
                event3.setText("+ events");
                break;
        }


    }

    private void setEvent(TextView tv, Event event) {
            tv.setText(event.getNombre());
            tv.setVisibility(View.VISIBLE);
    }

    private void hideEvent(TextView event) {
        event.setVisibility(View.INVISIBLE);
    }

    public static class NoteActivity extends Fragment {

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
            ((MainActivity2) getActivity()).getSupportActionBar().setTitle("Notas R??pidas");
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
                            .setTitle("??Estas seguro?")
                            .setMessage("??Desea eliminar esta nota?")
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

    public static class NoteEditorActivity extends Fragment {
        int noteId;

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
        {
            final View view = inflater.inflate(R.layout.activity_note_editor, container, false);
            ((MainActivity2) getActivity()).getSupportActionBar().setTitle("Notas R??pidas");
            FloatingActionButton shareBtn =  ((MainActivity2) getActivity()).findViewById(R.id.share);
            shareBtn.setVisibility(View.GONE);

            EditText editText = view.findViewById(R.id.editText);

            // Cargamos los datos de NoteActivity
            Intent intent = this.requireActivity().getIntent();

            // Accessing the data using key and value
            noteId = getArguments().getInt("my_key");
            if (noteId != -1) {
                editText.setText(NoteActivity.notes.get(noteId));
            } else {

                NoteActivity.notes.add("");
                noteId = NoteActivity.notes.size() - 1;
                NoteActivity.arrayAdapter.notifyDataSetChanged();

            }

            editText.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                }

                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                    NoteActivity.notes.set(noteId, String.valueOf(charSequence));
                    NoteActivity.arrayAdapter.notifyDataSetChanged();
                    // Creamos un objeto SharedPreferences para almacenar el texto de las notas cuando se cierre la app.
                    SharedPreferences sharedPreferences = getContext().getSharedPreferences("com.example.notes", Context.MODE_PRIVATE);
                    HashSet<String> set = new HashSet(NoteActivity.notes);
                    sharedPreferences.edit().putStringSet("notes", set).apply();
                }

                @Override
                public void afterTextChanged(Editable editable) {
                }
            });
            return view;
        }
    }
}
