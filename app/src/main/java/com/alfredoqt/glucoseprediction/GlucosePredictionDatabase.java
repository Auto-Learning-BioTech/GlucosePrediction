package com.alfredoqt.glucoseprediction;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(entities = {GlucoseEntryEntity.class}, version = 1)
public abstract class GlucosePredictionDatabase extends RoomDatabase {

    public static String NAME = "glucose-prediction.db";
    private static GlucosePredictionDatabase instance;
    private static final int NUMBER_OF_THREADS = 4;
    static final ExecutorService databaseWriteExecutor =
            Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    // Singleton
    public static  synchronized GlucosePredictionDatabase getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(
                    context.getApplicationContext(),
                    GlucosePredictionDatabase.class,
                    NAME
            ).fallbackToDestructiveMigration()
                  .build();
            return instance;
        }
        return instance;
    }

    public abstract GlucoseEntityDao glucoseDao();

}
