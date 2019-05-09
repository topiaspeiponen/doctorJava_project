package com.example.doctorjava_project;

import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

import androidx.room.TypeConverter;

/**
 * @author Created by Topias on 4.5.2019
 * @version 1.0
 * @since 1.0
 */

/**
 * This is a converter class that converts Date types to Long type and vice versa
 * This is used in database operations (Room)
 */
public class TypeConverters {
    /**
     * From timestamp date.
     *
     * @param value the value
     * @return the date
     */
    @TypeConverter
    public static Date fromTimestamp(Long value) {
        return value == null ? null : new Date(value);
    }

    /**
     * Date to timestamp long.
     *
     * @param date the date
     * @return the long
     */
    @TypeConverter
    public static Long dateToTimestamp(Date date) {
        if (date == null) {
            return null;
        } else {
            return date.getTime();
        }
    }
}
