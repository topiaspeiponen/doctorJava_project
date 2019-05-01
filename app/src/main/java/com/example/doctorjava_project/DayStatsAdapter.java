package com.example.doctorjava_project;

import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import org.w3c.dom.Text;
import java.util.ArrayList;
import java.util.List;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class DayStatsAdapter extends RecyclerView.Adapter<DayStatsAdapter.ViewHolder> {

    List<DayStats> dayStatsArrayList;

    public DayStatsAdapter(List<DayStats> dayStatsArrayList) {
        this.dayStatsArrayList = dayStatsArrayList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.day_stats_row,parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.dayDate.setText(Integer.toString(dayStatsArrayList.get(position).getDayDate()));
        holder.dayPoints.setText(Integer.toString(dayStatsArrayList.get(position).getPoints()));
    }

    @Override
    public int getItemCount() {
        return dayStatsArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView dayDate, dayPoints;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            dayDate = itemView.findViewById(R.id.dayDate);
            dayPoints = itemView.findViewById(R.id.dayPoints);
        }
    }
}
