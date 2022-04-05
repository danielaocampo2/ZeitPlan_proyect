package com.example.zeitplan_proyect;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class CalendarAdapter extends RecyclerView.Adapter<CalendarViewHolder>
{
    private final ArrayList<String> daysMonth;
    private final OnItemListener onItemListener;

    public CalendarAdapter(ArrayList<String> daysMonth, OnItemListener onItemListener) {
        this.daysMonth = daysMonth;
        this.onItemListener = onItemListener;
    }

    @NonNull
    @Override
    public CalendarViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.casella_calendari, parent, false);
        ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
        layoutParams.height = (int) (parent.getHeight() * 0.16666666666);
        return new CalendarViewHolder(view, onItemListener);
    }

    @Override
    public void onBindViewHolder(@NonNull CalendarViewHolder holder, int position)
    {
        holder.dayMonth.setText(daysMonth.get(position));
    }

    @Override
    public int getItemCount()
    {
        return daysMonth.size();
    }

    public interface OnItemListener
    {
        void onItemClick(int position, String dayText);
    }

}
