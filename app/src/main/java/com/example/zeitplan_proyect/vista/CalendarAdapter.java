package com.example.zeitplan_proyect.vista;

import android.graphics.Color;
import android.os.Build;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import com.example.zeitplan_proyect.DataBase.Firebase;
import com.example.zeitplan_proyect.R;
import com.example.zeitplan_proyect.model.Event;
import com.example.zeitplan_proyect.presenter.PresenterCalendarUtils;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

@RequiresApi(api = Build.VERSION_CODES.O)
public class CalendarAdapter extends RecyclerView.Adapter<CalendarViewHolder>
{
    private final ArrayList<LocalDate> days;
    private final OnItemListener onItemListener;
    private final PresenterCalendarUtils PresCal;

    private final ArrayList<Event> eventos;

    public CalendarAdapter(ArrayList<LocalDate> days, OnItemListener onItemListener, ArrayList<Event> eventos) {
        this.days = days;
        this.onItemListener = onItemListener;
        this.PresCal = PresenterCalendarUtils.getInstance();

        this.eventos = eventos;
    }

    @NonNull
    @Override
    public CalendarViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.casella_calendari, parent, false);
        ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
        if(days.size() > 15) //month view
            layoutParams.height = (int) (parent.getHeight() * 0.16666666666);
        else //week view
            layoutParams.height = (int) parent.getHeight();

        return new CalendarViewHolder(view, onItemListener, days);
    }

    @Override
    public void onBindViewHolder(@NonNull CalendarViewHolder holder, int position)
    {
        LocalDate date = days.get(position);

        holder.dayMonth.setText(String.valueOf(date.getDayOfMonth()));
        if(date.equals(PresCal.getSelectedDate())){
            holder.parentView.setBackgroundColor(Color.LTGRAY);
        }

        if(date.getMonth().equals(PresCal.SelDateMonth())){
            holder.dayMonth.setTextColor(Color.BLACK);
        }else{
            holder.dayMonth.setTextColor(Color.LTGRAY);
        }

        int nEvents = PresCal.numEvents(date, eventos);

        switch (nEvents){
            default:
                holder.casella1.setVisibility(View.INVISIBLE);
            case 1:
                holder.casella2.setVisibility(View.INVISIBLE);
            case 2:
                holder.casella3.setVisibility(View.INVISIBLE);
            case 3:
                holder.casella4.setVisibility(View.INVISIBLE);
            case 4:
                break;
            case 5:
                holder.casella4.setText("+");
                break;
        }

    }

    @Override
    public int getItemCount()
    {
        return days.size();
    }

    public interface OnItemListener
    {
        void onItemClick(int position, LocalDate date);
    }

}
