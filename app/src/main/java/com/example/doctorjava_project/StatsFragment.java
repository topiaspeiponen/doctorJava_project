package com.example.doctorjava_project;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

/**
 * @author Created by Topias on 25/04/2019.
 * @version 1.1
 * @since 1.0
 */

/**
 * The StatsFragment class (Loads on STATISTICS page) holds all the operations in regards
 * to operating the statistics on the page.
 */
public class StatsFragment extends Fragment {
    /**
     * The Recycler view.
     */
    RecyclerView recyclerView;
    /**
     * The Adapter.
     */
    RecyclerView.Adapter adapter;
    private static final String TAG = "StatsFragment";
    /**
     * The Application context.
     */
    Context applicationContext = MainActivity.getContextOfApplication();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_stats, null);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState){
        Context context = this.getActivity();
        Button weekButton = getView().findViewById(R.id.buttonDummy);
        Button buttonClear = getView().findViewById(R.id.buttonClear);

        //Accesses the database
        final DayStatsDatabase db = Room.databaseBuilder(getContext(), DayStatsDatabase.class, "production")
                .allowMainThreadQueries()
                .fallbackToDestructiveMigration()
                .build();

        //Gets all the objects in DayStats from the database and savesa them into a list
        final List<DayStats> dayStatsList = db.dayStatsDao().getAllDayStats();
        db.close();

        //Implementing the list into the recyclerView using an adapter
        recyclerView = getView().findViewById(R.id.RecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this.getActivity()));
        adapter = new DayStatsAdapter(dayStatsList);
        recyclerView.setAdapter(adapter);


        /**
         * Dummy row button, creates a dummy row with current date and current seconds as points
         */
        weekButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Log.d(TAG, "Dummy row created!");

                //Accesses the database
                final DayStatsDatabase db = Room.databaseBuilder(getContext(), DayStatsDatabase.class, "production")
                        .allowMainThreadQueries()
                        .fallbackToDestructiveMigration()
                        .build();

                /**
                 * Getting the current StepCount for the day and resetting it to 0 and saving it again
                 */
                Date date = Calendar.getInstance().getTime();
                SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(applicationContext);
                int lastStepCountSaved = prefs.getInt("StepCount", 0);
                Log.d(TAG, Integer.toString(lastStepCountSaved));

                db.dayStatsDao().insertAll(new DayStats(date, lastStepCountSaved));

                //Gets all the objects in DayStats from the database and savesa them into a list
                final List<DayStats> dayStatsList = db.dayStatsDao().getAllDayStats();
                db.close();

                recyclerView.setLayoutManager(new LinearLayoutManager(context));
                adapter = new DayStatsAdapter(dayStatsList);
                recyclerView.setAdapter(adapter);

                SharedPreferences.Editor prefEditor = prefs.edit();
                prefEditor.putInt("StepCount", 0);
                prefEditor.commit();
            }
        });

        /**
         * Clear table button, clears the entire table
         */
        buttonClear.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Log.d(TAG, "Table nuked!");
                db.dayStatsDao().nukeTable();

                //Accesses the database
                final DayStatsDatabase db = Room.databaseBuilder(getContext(), DayStatsDatabase.class, "production")
                        .allowMainThreadQueries()
                        .fallbackToDestructiveMigration()
                        .build();

                //Gets all the objects in DayStats from the database and savesa them into a list
                final List<DayStats> dayStatsList = db.dayStatsDao().getAllDayStats();
                db.close();

                recyclerView.setLayoutManager(new LinearLayoutManager(context));
                adapter = new DayStatsAdapter(dayStatsList);
                recyclerView.setAdapter(adapter);
            }
        });
    }
}