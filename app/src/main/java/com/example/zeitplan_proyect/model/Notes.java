package com.example.zeitplan_proyect.model;

import java.util.ArrayList;
import java.util.HashSet;

public class Notes {
    ArrayList<String> notes;
    public static Notes instance;


    public Notes(){
        notes = new ArrayList<>();
    }

    public static Notes getInstance(){
        if(instance == null){
            instance = new Notes();
        }
        return instance;

    }
    public ArrayList<String> getNotes(){
        return notes;
    }
    public void setNotes(HashSet<String> list){
        notes = new ArrayList<>(list);
    }
    public String getNoteId(int id){
        return notes.get(id);
    }
}


