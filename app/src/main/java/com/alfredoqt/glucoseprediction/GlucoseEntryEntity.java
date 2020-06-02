package com.alfredoqt.glucoseprediction;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "glucose_entry")
public class GlucoseEntryEntity {

    @PrimaryKey(autoGenerate = true)
    public int uid;

    @ColumnInfo(name = "year")
    public String year;

    @ColumnInfo(name = "month")
    public String month;

    @ColumnInfo(name = "day")
    public String day;

    @ColumnInfo(name = "hour")
    public String hour;

    @ColumnInfo(name = "level")
    public String level;

}
