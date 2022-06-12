package com.example.zeitplan_proyect.vista;

import android.app.Application;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.zeitplan_proyect.R;
import com.example.zeitplan_proyect.model.AudioNota;
import com.google.common.io.LineReader;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;

public class AudioAdapter  extends RecyclerView.Adapter<AudioViewHolder> {

    private final ArrayList<AudioNota> localDataSet;
    private final Context parentContext;
    private final playerInterface listener;
    private final AudioActivity audioActivity;

    /**
     * Initialize the dataset of the Adapter.
     *
     * @param dataSet String[] containing the data to populate views to be used
     * by RecyclerView.
     */
    public AudioAdapter(Context current, ArrayList<AudioNota> dataSet, playerInterface listener, AudioActivity audioActivity) {
        parentContext = current;
        localDataSet = dataSet;
        this.audioActivity = audioActivity;
        this.listener = listener;
    }

    // Create new views (invoked by the layout manager)
    @NonNull
    @Override
    public AudioViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        // Create a new view, which defines the UI of the list item
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.audio_item, viewGroup, false);
        return new AudioViewHolder(view);
    }

    private void playAudio(int position) {
        // Play audio for clicked note
        listener.startPlaying(position);
    }

    public interface playerInterface{
        void startPlaying(int fileName);
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(AudioViewHolder viewHolder, int position) {

        // Get element from your dataset at this position and replace the
        // contents of the view with that element
        int color = ContextCompat.getColor(parentContext, R.color.note);
       // viewHolder.getLayout().setBackgroundColor(color);
        //position = viewHolder.getAdapterPosition();
        viewHolder.getTextView().setText(
                localDataSet.get(position).getDescription());

        ImageButton playButton = viewHolder.getPlayButton();
        playButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String fileName;
                fileName = localDataSet.get(position).getAddress();
                Log.d("AudioAdapter","audio_pos "+ position);
                Log.d("AudioAdapter","audioFile "+ fileName);
                try{
                    MediaPlayer player = new MediaPlayer();
                    player.setDataSource(fileName);
                    player.prepare();
                    player.start();
            } catch (
            IOException e) {
                    Log.d("startPlaying", "prepare() failed");
                }
            }
        });
        viewHolder.getTextView().setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                // Borrar una nota de la app.
                new AlertDialog.Builder(view.getContext())
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .setTitle("¿Estas seguro?")
                        .setMessage("¿Desea eliminar esta nota de voz?")
                        .setPositiveButton("Si", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                                localDataSet.get(position).removeAudioNota(localDataSet.get(position).getNoteId());
                                audioActivity.deleteNota(position);
                                Toast.makeText(view.getContext(), "Nota Eliminada", Toast.LENGTH_SHORT).show();
                            }
                        }).setNegativeButton("No", null).show();
                return true;
            }
        });
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        if (localDataSet != null) {
            return localDataSet.size();
        }
        return 0;
    }

}