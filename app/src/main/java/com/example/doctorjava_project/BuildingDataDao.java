package com.example.doctorjava_project;

/**
 * @author Created by Topias on 8/5/2019
 * @version 1.0
 * @since 1.0
 */

import java.util.ArrayList;
import java.util.List;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

/**
 * This is a DAO (Data Access Object) that essentially contains
 * all the usable queries for that specific table.
 */
@Dao
public interface BuildingDataDao {
    @Query("SELECT * FROM building_data")
    List<BuildingData> getAllBuildingData();

    @Query ("SELECT * FROM building_data WHERE id = :id LIMIT 1")
    public BuildingData findOneBuilding(int id);

    @Query("DELETE FROM building_data")
    public void nukeTable();

    @Insert
    void insertAll(BuildingData... buildingData);
}
