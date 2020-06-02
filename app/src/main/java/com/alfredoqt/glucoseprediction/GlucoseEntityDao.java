package com.alfredoqt.glucoseprediction;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface GlucoseEntityDao {

    @Query("SELECT * FROM glucose_entry")
    List<GlucoseEntryEntity> getAll();

    @Insert
    void insertEntry(GlucoseEntryEntity entity);

    @Query("DELETE FROM glucose_entry")
    void deleteAll();

}
