package com.example.doctorjava_project;

import androidx.room.Database;
import androidx.room.RoomDatabase;

/**
 * @author Created by Topias on 1/5/2019
 * @version 1.3
 * @since 1.0
 */

/**
 * This class defines the DayStats database.
 */
@Database(entities = {DayStats.class}, version = 3, exportSchema = false)
@androidx.room.TypeConverters({TypeConverters.class})
public abstract class DayStatsDatabase extends RoomDatabase {
    public abstract DayStatsDao dayStatsDao();
}
