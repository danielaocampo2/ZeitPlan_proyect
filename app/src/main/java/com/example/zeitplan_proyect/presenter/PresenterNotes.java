package com.example.zeitplan_proyect.presenter;

import com.example.zeitplan_proyect.model.CalendarUtils;
import com.example.zeitplan_proyect.model.Notes;
import com.example.zeitplan_proyect.model.User;

import java.util.ArrayList;
import java.util.HashSet;

public class PresenterNotes {
    Notes notes;

    public PresenterNotes(){
        notes = Notes.getInstance();
    }
    public ArrayList<String> getUserNotes(){
        return notes.getNotes();
    }
    public void addNote(String sequence){
        notes.getNotes().add(sequence);
    }
    public void setNotes(HashSet<String> list){
        notes.setNotes(list);
    }
    public String getNoteId(int id){
        return notes.getNoteId(id);
    }
}
