package com.example.doctorjava_project;

import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

public interface DaoAccess {
    @Insert
    void insertOnlySingleDay (Day days);
    @Insert
    void insertMultipleMovies (List<Days> daysList);
    @Query('SELECT * FROM days WHERE dayId = :dayId'')
    Days fetchOneDaybyDayId (int dayId);
    @Update
    void updateMovie (Days days);
    @Delete
    void deleteMovie (Days days);
}
