package com.example.doctorjava_project;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class HistoryDatabase {
    @NonNull
    @PrimaryKey
    private int dayId;
    private int daysPoints;

    public HistoryDatabase() {

    }

    public int getDayId() {
        return dayId;
    } public void setDayId(int dayId) {
        this.dayId = dayId;
    }

    public int getDaysPoints() {
        return daysPoints;
    } public void setDaysPoints(int daysPoints) {
        this.daysPoints = daysPoints;
    }
}
