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

    /**
     * Instantiates a new Building data.
     *
     * @param id        the id
     * @param bought    the bought
     * @param total     the total
     * @param basePrice the base price
     */
    public BuildingData(int id, int bought, long total, double basePrice) {
        this.id = id;
        this.bought = bought;
        this.total = total;
        this.basePrice = basePrice;
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
     * Gets bought.
     *
     * @return the bought
     */
    public int getBought() {
        return bought;
    }

    /**
     * Sets bought.
     *
     * @param bought the bought
     */
    public void setBought(int bought) {
        this.bought = bought;
    }

    /**
     * Gets total.
     *
     * @return the total
     */
    public long getTotal() {
        return total;
    }

    /**
     * Sets total.
     *
     * @param total the total
     */
    public void setTotal(long total) {
        this.total = total;
    }

    /**
     * Gets base price.
     *
     * @return the base price
     */
    public double getBasePrice() {
        return basePrice;
    }

    /**
     * Sets base price.
     *
     * @param basePrice the base price
     */
    public void setBasePrice(double basePrice) {
        this.basePrice = basePrice;
    }
}
