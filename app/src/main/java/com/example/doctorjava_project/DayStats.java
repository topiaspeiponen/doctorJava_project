package com.example.doctorjava_project;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

/**
 * @author Created by Topias on 1/5/2019
 * @version 1.0
 * @since 1.0
 */

@Entity
public class DayStats {
    @PrimaryKey (autoGenerate = true)
    private int id;
    @ColumnInfo (name = "date")
    private int dayDate;
    @ColumnInfo (name = "points")
    private int points;

    public DayStats(int dayDate, int points) {
        this.dayDate = dayDate;
        this.points = points;
    }
    public int getId() {
        return id;
    } public void setId(int id) {
        this.id = id;
    }

    public int getDayDate() {
        return dayDate;
    } public void setDayDate(int dayDate) {
        this.dayDate = dayDate;
    }

    public int getPoints() {
        return points;
    } public void setPoints(int points) {
        this.points = points;
    }
}
