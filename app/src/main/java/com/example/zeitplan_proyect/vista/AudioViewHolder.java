package com.example.zeitplan_proyect.vista;

import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.zeitplan_proyect.R;

public class AudioViewHolder extends RecyclerView.ViewHolder{

    private final TextView textView;
    private final LinearLayout audioLayout;
    private final ImageButton playButton;

    public AudioViewHolder(View view) {
        super(view);
        // Define click listener for the ViewHolder's View

        textView = view.findViewById(R.id.textViewAudio);
        audioLayout = view.findViewById(R.id.audio_item);
        playButton = view.findViewById(R.id.play_buttonAudio);
    }

    public TextView getTextView() {
        return textView;
    }

    public LinearLayout getLayout() {
        return audioLayout;
    }

    public ImageButton getPlayButton() {return playButton;}
}
