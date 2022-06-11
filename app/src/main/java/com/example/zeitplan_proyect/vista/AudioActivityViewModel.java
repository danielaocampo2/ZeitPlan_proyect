package com.example.zeitplan_proyect.vista;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.zeitplan_proyect.DataBase.Firebase;
import com.example.zeitplan_proyect.model.AudioNota;
import com.example.zeitplan_proyect.presenter.PresenterAudioNotas;
import com.example.zeitplan_proyect.presenter.PresenterNotes;

import java.util.ArrayList;

public class AudioActivityViewModel extends AndroidViewModel implements PresenterAudioNotas.vmInterface {

    private final MutableLiveData<ArrayList<AudioNota>> mAudioNotas;
    private final MutableLiveData<String> mToast;
    public static final String TAG = "ViewModel";

    //Constructor
    public AudioActivityViewModel(Application application){
        super(application);

        mAudioNotas = new MutableLiveData<>();
        mToast = new MutableLiveData<>();
        PresenterAudioNotas da = new PresenterAudioNotas(this);
        da.getAudioCollection();
    }

    //public getter. Not mutable , read-only
    public LiveData<ArrayList<AudioNota>> getAudioCards(){
        return mAudioNotas;
    }

    public AudioNota getAudioNote(int idx){
        return mAudioNotas.getValue().get(idx);
    }

    public void addAudioCard(String description, String localPath, String owner){
        AudioNota ac = new AudioNota(description, localPath, owner);
        if (ac != null) {
            mAudioNotas.getValue().add(ac);
            // Inform observer.
            mAudioNotas.setValue(mAudioNotas.getValue());
            ac.saveAudioNota();
        }
    }
    public void removeAudioCard(int idx){

    }

    public LiveData<String> getToast(){
        return mToast;
    }

    public void setCollection(ArrayList<AudioNota> ac) {
        mAudioNotas.setValue(ac);
    }

    public void setToast(String t) {
        mToast.setValue(t);
    }
}




