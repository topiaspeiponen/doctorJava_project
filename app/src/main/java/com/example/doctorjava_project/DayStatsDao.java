package com.example.doctorjava_project;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

/**
 * @author Created by Topias on 1/5/2019
 * @version 1.0
 * @since 1.0
 */

/**
 * This is a DAO (Data Access Object) that essentially contains
 * all the usable queries for that specific table.
 */
@Dao
public interface DayStatsDao {
    @Query("SELECT * FROM day_stats ORDER BY points DESC LIMIT 14")
    List<DayStats> getAllDayStats();

    @Insert
    void insertAll(DayStats... dayStats);
}
