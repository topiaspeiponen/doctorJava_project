package com.example.doctorjava_project;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;

import java.util.Calendar;
import java.util.Date;

import androidx.room.Room;

/**
 * @author Created by Topias on 4.5.2019
 * @version 1.0
 * @since 1.0
 */

/**
 * This class is for sending the data received during the day to the database table DayStats at daily interval
 */
public class DataAlarm extends BroadcastReceiver {
    private static final String TAG = "DataAlarm";
    /**
     * The Application context.
     */
    Context applicationContext = MainActivity.getContextOfApplication();

    @Override
    public void onReceive(Context context, Intent intent) {
        if (!intent.hasExtra("activate")) {

        } else {
            Log.d(TAG, "doctorDebug: DataAlarm triggered, sending data to database!");
            final DayStatsDatabase db = Room.databaseBuilder(context, DayStatsDatabase.class, "production")
                    .allowMainThreadQueries()
                    .fallbackToDestructiveMigration()
                    .build();
            Date date = Calendar.getInstance().getTime();

            /**
             * Getting the current StepCount for the day, putting it into the DB
             * and resetting it to 0 and saving it again
             */
            SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(applicationContext);
            int lastStepCountSaved = prefs.getInt("StepCount", 0);

            db.dayStatsDao().insertAll(new DayStats(date, lastStepCountSaved));

            SharedPreferences.Editor prefEditor = prefs.edit();
            prefEditor.putInt("StepCount", 0);
            prefEditor.commit();
        }
    }
}
