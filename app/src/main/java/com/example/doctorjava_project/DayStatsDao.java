package com.example.doctorjava_project;

import java.util.ArrayList;
import java.util.List;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

/**
 * @author Created by Topias on 1/5/2019
 * @version 1.0
 * @since 1.0
 */

@Dao
public interface DayStatsDao {
    @Query("SELECT * FROM daystats")
    List<DayStats> getAllDayStats();

    @Insert
    void insertAll(DayStats... dayStats);
}
