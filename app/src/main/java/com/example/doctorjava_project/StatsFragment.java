package com.example.doctorjava_project;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

/**
 * @author Created by Topias on 25/04/2019.
 * @version 1.0
 * @since 1.0
 */

/**
 * Inflates the layout (renders it in the memory), which makes it appear
 * in the screen view.
 */
public class StatsFragment extends Fragment {
    RecyclerView recyclerView;
    RecyclerView.Adapter adapter;
    private static final String TAG = "StatsFragment";
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_stats, null);
    }
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState){

        Button weekButton = getView().findViewById(R.id.buttonLastWeek);


        final DayStatsDatabase db = Room.databaseBuilder(getContext(), DayStatsDatabase.class, "production")
                .allowMainThreadQueries()
                .build();

        final List<DayStats> dayStatsList = db.dayStatsDao().getAllDayStats();

        recyclerView = getView().findViewById(R.id.RecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this.getActivity()));
        adapter = new DayStatsAdapter(dayStatsList);
        recyclerView.setAdapter(adapter);

        /*lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Log.d(TAG, "onItemClick(" + i + ")");
                Intent nextActivity = new Intent(MainActivity.this, DaysDetailsActivity.class);
                nextActivity.putExtra(EXTRA, i);
                startActivity(nextActivity);
            }
        });*/

        weekButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Log.d(TAG, "onClick: pressed!");
                db.dayStatsDao().insertAll(new DayStats(130598, 3442234));
            }
        });

    }


}