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

/**
 * @author Created by Topias on 1/5/2019
 * @version 1.0
 * @since 1.0
 */

/**
 * This class is the adapter for implementing the database into the
 * RecyclerView component in the Stats fragment.
 */
public class DayStatsAdapter extends RecyclerView.Adapter<DayStatsAdapter.ViewHolder> {

    /**
     * The Day stats array list.
     */
    List<DayStats> dayStatsArrayList;

    /**
     * Instantiates a new Day stats adapter.
     *
     * @param dayStatsArrayList the day stats array list
     */
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
        holder.dayDate.setText(dayStatsArrayList.get(position).getDayDate().getDate() + "/" + (dayStatsArrayList.get(position).getDayDate().getMonth()+1)
        + "/" + (dayStatsArrayList.get(position).getDayDate().getYear() + 1900));
        holder.dayPoints.setText(Integer.toString(dayStatsArrayList.get(position).getPoints()));
    }

    @Override
    public int getItemCount() {
        return dayStatsArrayList.size();
    }

    /**
     * The type View holder.
     */
    public class ViewHolder extends RecyclerView.ViewHolder {
        /**
         * The Day date.
         */
        public TextView dayDate, /**
         * The Day points.
         */
        dayPoints;

        /**
         * Instantiates a new View holder.
         *
         * @param itemView the item view
         */
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            dayDate = itemView.findViewById(R.id.dayDate);
            dayPoints = itemView.findViewById(R.id.dayPoints);
        }
    }
}
