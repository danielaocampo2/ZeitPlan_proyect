package com.example.zeitplan_proyect.vista;

import android.os.Build;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import com.example.zeitplan_proyect.R;

import java.time.LocalDate;
import java.util.ArrayList;
@RequiresApi(api = Build.VERSION_CODES.O)
public class CalendarViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener
{
    private final ArrayList<LocalDate> days;
    public final View parentView;
    public final TextView dayMonth;
    public final TextView casella1, casella2, casella3, casella4;
    private final CalendarAdapter.OnItemListener onItemListener;

    public CalendarViewHolder(@NonNull View itemView, CalendarAdapter.OnItemListener onItemListener, ArrayList<LocalDate> days) {
        super(itemView);
        parentView = itemView.findViewById(R.id.parentView);
        dayMonth = itemView.findViewById(R.id.cellDayText);
        casella1 = itemView.findViewById(R.id.casella1);
        casella2 = itemView.findViewById(R.id.casella2);
        casella3 = itemView.findViewById(R.id.casella3);
        casella4 = itemView.findViewById(R.id.casella4);

        this.onItemListener = onItemListener;
        itemView.setOnClickListener(this);
        this.days = days;
    }


    @Override
    public void onClick(View view)
    {
        onItemListener.onItemClick(getAdapterPosition(), days.get(getAdapterPosition()));
    }
}
