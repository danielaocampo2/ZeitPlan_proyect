package com.example.zeitplan_proyect.presenter;

import com.example.zeitplan_proyect.model.CalendarUtils;
import com.example.zeitplan_proyect.model.User;

import java.util.ArrayList;
import java.util.HashSet;

public class PresenterNotes {
    User user;

    public PresenterNotes(){
        user = User.getInstance();
    }
    public ArrayList<String> getUserNotes(){
        return user.getNotes();
    }
    public void addNote(String sequence){
        user.getNotes().add(sequence);
    }
    public void setNotes(HashSet<String> list){
        user.setNotes(list);
    }
    public String getNoteId(int id){
        return user.getNoteId(id);
    }
}
