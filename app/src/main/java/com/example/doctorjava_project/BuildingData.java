package com.example.doctorjava_project;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

/**
 * @author Created by Topias on 8/5/2019
 * @version 1.0
 * @since 1.0
 */

/**
 * This the BuildingData database entity, which is essentially the table for the building's data
 */
@Entity (tableName = "building_data")
public class BuildingData {
    @PrimaryKey(autoGenerate = true)
    private int id;
    @ColumnInfo (name = "bought")
    private int bought;
    @ColumnInfo (name = "total")
    private long total;
    @ColumnInfo (name = "base_price")
    private double basePrice;

    public BuildingData(int id, int bought, long total, double basePrice) {
        this.id = id;
        this.bought = bought;
        this.total = total;
        this.basePrice = basePrice;
    }

    public int getId() {
        return id;
    } public void setId(int id) {
        this.id = id;
    }

    public int getBought() {
        return bought;
    } public void setBought(int bought) {
        this.bought = bought;
    }

    public long getTotal() {
        return total;
    } public void setTotal(long total) {
        this.total = total;
    }

    public double getBasePrice() {
        return basePrice;
    } public void setBasePrice(double basePrice) {
        this.basePrice = basePrice;
    }
}
