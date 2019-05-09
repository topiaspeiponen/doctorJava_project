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

    /**
     * Instantiates a new Day stats.
     *
     * @param dayDate the day date
     * @param points  the points
     */
    public DayStats(Date dayDate, int points) {
        this.dayDate = dayDate;
        this.points = points;
    }

    /**
     * Gets id.
     *
     * @return the id
     */
    public int getId() {
        return id;
    }

    /**
     * Sets id.
     *
     * @param id the id
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Gets day date.
     *
     * @return the day date
     */
    public Date getDayDate() {
        return dayDate;
    }

    /**
     * Sets day date.
     *
     * @param dayDate the day date
     */
    public void setDayDate(Date dayDate) {
        this.dayDate = dayDate;
    }

    /**
     * Gets points.
     *
     * @return the points
     */
    public int getPoints() {
        return points;
    }

    /**
     * Sets points.
     *
     * @param points the points
     */
    public void setPoints(int points) {
        this.points = points;
    }
}
