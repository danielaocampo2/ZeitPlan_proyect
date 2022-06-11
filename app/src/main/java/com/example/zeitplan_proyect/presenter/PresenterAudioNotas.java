package com.example.zeitplan_proyect.presenter;

import android.app.Activity;
import android.app.Application;
import android.net.Uri;
import android.provider.MediaStore;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.zeitplan_proyect.DataBase.Firebase;
import com.example.zeitplan_proyect.MainActivity2;
import com.example.zeitplan_proyect.model.AudioNota;
import com.example.zeitplan_proyect.vista.AudioActivity;
import com.example.zeitplan_proyect.vista.AudioActivityViewModel;
import com.example.zeitplan_proyect.vista.AudioAdapter;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageMetadata;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class PresenterAudioNotas {

    public static final String TAG = "PresenterNotasVoz";
    Firebase firebase = new Firebase();
    public static vmInterface listener;
    private AppCompatActivity mActivity;
    PresenterAudioNotas presenterAudioNotas;
    AudioAdapter audioAdapter;
    AudioNota audioNota;

    private static PresenterAudioNotas instance;

    public static PresenterAudioNotas getInstance(){
        if(instance == null){

            instance = new PresenterAudioNotas(listener);
        }
        return instance;
    }

    public PresenterAudioNotas(vmInterface listener){
        this.listener = listener;
        presenterAudioNotas = this;
    }

    public interface vmInterface{
        void setCollection(ArrayList<AudioNota> ac);
        void setToast(String s);
    }


    public void getAudioCollection(){
        Log.d(TAG,"updateaudioNotas");
        firebase.mFirestore.collection("audioNotas")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {

                            ArrayList<AudioNota> retrieved_ac = new ArrayList<AudioNota>() ;
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Log.d(TAG, document.getId() + " => " + document.getData());
                                retrieved_ac.add(new AudioNota( document.getString("description"), document.getString("url"), document.getString("userid")));
                            }
                            listener.setCollection(retrieved_ac);

                        } else {
                            Log.d(TAG, "Error getting documents: ", task.getException());
                        }
                    }
                });
    }
    public void saveDocument (String id, String description, String userid, String url) {

        // Create a new user with a first and last name
        userid = firebase.getIdUser();
        Map<String, Object> note = new HashMap<>();
        note.put("id", id);
        note.put("description", description);
        note.put("userid", userid);
        note.put("url", url);

        Log.d(TAG, "saveDocument");
        // Add a new document with a generated ID
        firebase.mFirestore.collection("audioNotas").document(id).set(note)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Log.d(TAG, "DocumentSnapshot added");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w(TAG, "Error adding document", e);
                    }
                });
    }

    public void saveDocumentWithFile (String id, String description, String userid, String path) {

        Uri file = Uri.fromFile(new File(path));
        Log.d("File",file.toString());
        StorageReference storageRef = firebase.storage.getReference();
        StorageReference audioRef = storageRef.child("audio"+File.separator+file.getLastPathSegment());
        Log.d("audioRef",audioRef.toString());
        //set metadata
        StorageMetadata metadata = new StorageMetadata.Builder().setContentType("audio/gp3").build();
        UploadTask uploadTask = audioRef.putFile(file,metadata);

        Task<Uri> urlTask = uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
            @Override
            public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                if (!task.isSuccessful()) {
                    Log.d(TAG, "Error uploading audioRef", task.getException());
                    throw task.getException();
                }

                // Continue with the task to get the download URL
                Log.d(TAG, "AudioRef succesfull", task.getException());
                return audioRef.getDownloadUrl();
            }
        }).addOnCompleteListener(new OnCompleteListener<Uri>() {
            @Override
            public void onComplete(@NonNull Task<Uri> task) {
                if (task.isSuccessful()) {
                    Uri downloadUri = task.getResult();

                    saveDocument(id, description, userid, downloadUri.toString());
                } else {
                    // Handle failures
                    // ...
                }
            }
        });


        uploadTask.addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                double progress = (100.0 * taskSnapshot.getBytesTransferred()) / taskSnapshot.getTotalByteCount();
                Log.d(TAG, "Upload is " + progress + "% done");
            }
        });
    }

    public HashMap<String, String> getDocuments () {
        firebase.mFirestore.collection("audioNotas")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Log.d(TAG, document.getId() + " => " + document.getData());
                            }
                        } else {
                            Log.w(TAG, "Error getting documents.", task.getException());
                        }
                    }
                });

        return new HashMap<>();
    }

    /*
    public void setCollection(ArrayList<AudioNota> audioNotaArrayList){
        audioActivityViewModel.setCollection(audioNotaArrayList);
    }
    public void saveAudioNota(){
        audioNota.saveAudioNota();
    }
    public AudioActivityViewModel getAudioActivityViewModel(){
        return audioActivityViewModel;
    }
    public void setAudioActivityViewModel(AudioActivityViewModel audioActivityViewModel){
        this.audioActivityViewModel = audioActivityViewModel;
    }

     */
}
