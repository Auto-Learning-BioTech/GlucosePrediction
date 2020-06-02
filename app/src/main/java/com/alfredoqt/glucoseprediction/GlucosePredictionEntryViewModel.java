package com.alfredoqt.glucoseprediction;

import android.content.Context;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class GlucosePredictionEntryViewModel extends ViewModel {

    public MutableLiveData<RetrofitResource<Void>> mNewEntry;
    private Context mContext;

    public GlucosePredictionEntryViewModel(Context context) {
        mNewEntry = new MutableLiveData<>();
        mContext = context;
    }

    public void onNewEntry(String year, String month, String day, String hour, String level) {
        final GlucoseEntryEntity entity = new GlucoseEntryEntity();
        entity.year = year;
        entity.month = month;
        entity.day = day;
        entity.hour = hour;
        entity.level = level;

        RetrofitResource<Void> loading = new RetrofitResource<>();
        loading.status = RetrofitResourceStatus.LOADING;
        mNewEntry.setValue(loading);

        GlucosePredictionDatabase.databaseWriteExecutor
                .execute(new Runnable() {
                    @Override
                    public void run() {
                        GlucosePredictionDatabase.getInstance(mContext)
                                .glucoseDao()
                                .insertEntry(entity);

                        RetrofitResource<Void> resource = new RetrofitResource<>();
                        resource.status = RetrofitResourceStatus.SUCCESS;
                        mNewEntry.postValue(resource);
                    }
                });
    }

}
