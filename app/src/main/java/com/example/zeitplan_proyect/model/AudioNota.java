package com.example.zeitplan_proyect.model;

import android.util.Log;

import com.example.zeitplan_proyect.DataBase.Firebase;
import com.example.zeitplan_proyect.presenter.PresenterAudioNotas;

import java.util.HashMap;
import java.util.UUID;

public class AudioNota {
    private String noteId;
    private final String audioDesc;
    private final String address;
    private final String owner;
    private final PresenterAudioNotas db = PresenterAudioNotas.getInstance();
    //private final AppDatabase db;

    // Description, uri, duration, format, owner
    public AudioNota(String description, String localPath, String owner) {
        this.audioDesc = description;
        this.address = localPath;
        this.owner = owner;
        UUID uuid = UUID.randomUUID();
        this.noteId = uuid.toString();
    }
    public String getAddress () {
        return this.address;
    }
    public String getDescription () {
        return this.audioDesc;
    }

    private void setNoteId (String id) {
        this.noteId = id;
    }

    public void saveAudioNota() {

        Log.d("saveAudioCard", "saveAudioCard-> saveDocument");
        db.saveDocumentWithFile(this.noteId, this.audioDesc, this.owner, this.address);
    }

    public AudioNota getCard() {
        // ask database and if true, return audioCard
        HashMap<String, String> hm = db.getDocuments();
        if (hm != null) {
            AudioNota ac = new AudioNota(hm.get("description"), "", hm.get("owner"));
            ac.setNoteId(hm.get("noteid"));
            return ac;
        } else {
            return null;
        }
    }
    public String getNoteId(){
        return noteId;
    }

    public String getOwner(){
        return owner;
    }
}
