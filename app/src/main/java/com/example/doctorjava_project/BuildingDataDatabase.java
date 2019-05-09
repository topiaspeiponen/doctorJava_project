package com.example.doctorjava_project;

/**
 * @author Created by Topias on 8/5/2019
 * @version 1.0
 * @since 1.0
 */

import androidx.room.Database;
import androidx.room.RoomDatabase;

/**
 * This class defines the BuildingData database.
 */
@Database(entities = {BuildingData.class}, version = 1, exportSchema = false)
public abstract class BuildingDataDatabase extends RoomDatabase {
    /**
     * Building data dao building data dao.
     *
     * @return the building data dao
     */
    public abstract BuildingDataDao buildingDataDao();
}
