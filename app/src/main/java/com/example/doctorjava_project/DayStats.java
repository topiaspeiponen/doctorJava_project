package com.example.doctorjava_project;

import java.util.Date;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

/**
 * @author Created by Topias on 1/5/2019
 * @version 1.0
 * @since 1.0
 */

/**
 * This the DayStats database entity, which is essentially the table for the day's history stats
 */
@Entity (tableName = "day_stats")
public class DayStats {
    @PrimaryKey (autoGenerate = true)
    private int id;
    @ColumnInfo (name = "date")
    private Date dayDate;
    @ColumnInfo (name = "points")
    private int points;

    public DayStats(Date dayDate, int points) {
        this.dayDate = dayDate;
        this.points = points;
    }
    public int getId() {
        return id;
    } public void setId(int id) {
        this.id = id;
    }

    public Date getDayDate() {
        return dayDate;
    } public void setDayDate(Date dayDate) {
        this.dayDate = dayDate;
    }

    public int getPoints() {
        return points;
    } public void setPoints(int points) {
        this.points = points;
    }
}
