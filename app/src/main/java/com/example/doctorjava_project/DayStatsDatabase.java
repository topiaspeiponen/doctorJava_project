package com.example.doctorjava_project;

import androidx.room.Database;
import androidx.room.RoomDatabase;

/**
 * @author Created by Topias on 1/5/2019
 * @version 1.0
 * @since 1.0
 */

@Database(entities = {DayStats.class}, version = 1, exportSchema = false)
public abstract class DayStatsDatabase extends RoomDatabase {
    public abstract DayStatsDao dayStatsDao();
}
