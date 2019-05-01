package com.example.doctorjava_project;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = {DayStats.class}, version = 1)
public abstract class DayStatsDatabase extends RoomDatabase {
    public abstract DayStatsDao dayStatsDao();
}
