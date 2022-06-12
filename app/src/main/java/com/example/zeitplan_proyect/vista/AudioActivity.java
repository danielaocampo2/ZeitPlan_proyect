package com.example.zeitplan_proyect.vista;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.app.Activity;
import android.view.LayoutInflater;

import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.lifecycle.ViewModelProvider;

import com.example.zeitplan_proyect.MainActivity2;
import com.example.zeitplan_proyect.R;
import com.example.zeitplan_proyect.model.AudioNota;
import com.example.zeitplan_proyect.presenter.PresenterAudioNotas;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputLayout;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.drawable.ColorDrawable;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.PopupWindow;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;


public class AudioActivity extends Fragment implements AudioAdapter.playerInterface {
    private final String TAG = "AudioActivity";
    private Context parentContext;
    private AudioActivity audioActivity;
    private AppCompatActivity mActivity;
    public AudioAdapter.playerInterface listener;
    public ArrayList arrayList = new ArrayList<String>();
    Bundle bundle = new Bundle();
    // Requesting permission to RECORD_AUDIO
    private boolean permissionToRecordAccepted = false;
    private final String [] permissions = {Manifest.permission.RECORD_AUDIO};
    private static final int REQUEST_RECORD_AUDIO_PERMISSION = 200;

    private MediaRecorder recorder;
    private boolean isRecording = false;
    private boolean isPlaying = false;
    String fileName;

    private AudioActivityViewModel audioActivityViewModel;
    private RecyclerView mRecyclerView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final View view = inflater.inflate(R.layout.activity_audio_recyclerview, container, false);
        ((MainActivity2) getActivity()).getSupportActionBar().setTitle("Notas de Voz");
        FloatingActionButton shareBtn =  ((MainActivity2) getActivity()).findViewById(R.id.share);
        shareBtn.setVisibility(View.GONE);
        parentContext = getActivity().getBaseContext();
        mActivity = (AppCompatActivity) this.getActivity();
        ActivityCompat.requestPermissions(mActivity, permissions, REQUEST_RECORD_AUDIO_PERMISSION);


        // Define RecyclerView elements: 1) Layout Manager and 2) Adapter
        mRecyclerView = view.findViewById(R.id.recyclerview);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this.getContext()));
        setLiveDataObservers();

        // Floating button functionality
        ExtendedFloatingActionButton extendedFab = view.findViewById(R.id.extended_fab);
        extendedFab.setOnClickListener((v) -> {
            // Change Extended FAB aspect and handle recording
            if (isRecording) {
                extendedFab.extend();
                extendedFab.setIcon(
                        ContextCompat.getDrawable(
                                parentContext, android.R.drawable.ic_input_add));
                stopRecording();
                showPopup(mRecyclerView);

            } else {
                extendedFab.shrink();
                extendedFab.setIcon(
                        ContextCompat.getDrawable(
                                parentContext, android.R.drawable.ic_btn_speak_now));
                startRecording();
            }
        });
        audioActivity = this;
        return view;
    }

    public void setLiveDataObservers() {
        //Subscribe the activity to the observable
        audioActivityViewModel = new ViewModelProvider(this).get(AudioActivityViewModel.class);

        final Observer<ArrayList<AudioNota>> observer = new Observer<ArrayList<AudioNota>>() {
            @Override
            public void onChanged(ArrayList<AudioNota> ac) {
                AudioAdapter newAdapter = new AudioAdapter(parentContext, ac,(AudioAdapter.playerInterface) listener, audioActivity);
                mRecyclerView.swapAdapter(newAdapter, false);
                newAdapter.notifyDataSetChanged();
            }
        };

        final Observer<String> observerToast = new Observer<String>() {
            @Override
            public void onChanged(String t) {
                Toast.makeText(parentContext, t, Toast.LENGTH_SHORT).show();
            }
        };

        audioActivityViewModel.getAudioCards().observe(this, observer);
        audioActivityViewModel.getToast().observe(this, observerToast);
    }


    private void startRecording() {
        Log.d("startRecording", "startRecording");

        recorder = new MediaRecorder();
        DateFormat df = new SimpleDateFormat("yyMMddHHmmss", Locale.GERMANY);
        String date = df.format(Calendar.getInstance().getTime());
        fileName =  this.getContext().getExternalCacheDir().getAbsolutePath()+ File.separator +date+".3gp";
        Log.d("startRecording", fileName);

        recorder.setOutputFile(fileName);
        recorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        recorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
        recorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);

        try {
            recorder.prepare();
        } catch (IOException e) {
            Log.d("startRecording", "prepare() failed");
        }

        recorder.start();
        isRecording = true;
    }

    private void stopRecording() {
        recorder.stop();
        recorder.reset();
        recorder.release();
        recorder = null;
        isRecording = false;
    }

    public void startPlaying(int recyclerItem) {

        try {
            MediaPlayer player = new MediaPlayer();
            fileName = audioActivityViewModel.getAudioNote(recyclerItem).getAddress();
            Log.d("startPlaying", fileName);
            player.setDataSource(fileName);
            player.prepare();
            player.start();
        } catch (IOException e) {
            Log.d("startPlaying", "prepare() failed");
        }
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_RECORD_AUDIO_PERMISSION) {
            permissionToRecordAccepted = grantResults[0] == PackageManager.PERMISSION_GRANTED;
        }
        if (!permissionToRecordAccepted );
    }
    public void deleteNota(int recyclerItem){
        Log.d("AudioActivity", "audioCard id:" + audioActivityViewModel.getAudioNote(recyclerItem).getNoteId());
        audioActivityViewModel.removeAudioCard(recyclerItem);
        Log.d("AudioActivity", "remove audioCard");

    }

    public void showPopup(View anchorView) {

        View popupView = getLayoutInflater().inflate(R.layout.popup_audio, null);
        PopupWindow popupWindow = new PopupWindow(popupView, 800, 600);
        popupWindow.setFocusable(true);
        popupWindow.setBackgroundDrawable(new ColorDrawable());
        popupWindow.showAtLocation(anchorView, Gravity.CENTER, 0, 0);

        // Initialize objects from layout
        TextInputLayout saveDescr = popupView.findViewById(R.id.note_description);
        Button saveButton = popupView.findViewById(R.id.save_button);
        saveButton.setOnClickListener((v) -> {
            String text = saveDescr.getEditText().getText().toString();
            audioActivityViewModel.addAudioCard(text, fileName, "");

            popupWindow.dismiss();
        });
    }


}

