package com.alfredoqt.glucoseprediction;

import android.content.Context;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class MainViewModel extends ViewModel {

    private Context mContext;
    private GlucosePredictionRepository repository;
    public MutableLiveData<RetrofitResource<List<GlucoseEntryEntity>>> currentEntries;
    public MutableLiveData<RetrofitResource<String>> uploadBulk;

    public MainViewModel(Context context) {
        mContext = context;
        repository = new GlucosePredictionRepository();
        currentEntries = new MutableLiveData<>();
        uploadBulk = new MutableLiveData<>();
    }

    private void onGetEntries() {
        RetrofitResource<List<GlucoseEntryEntity>> loading = new RetrofitResource<>();
        loading.status = RetrofitResourceStatus.LOADING;
        currentEntries.setValue(loading);

        GlucosePredictionDatabase.databaseWriteExecutor
                .execute(new Runnable() {
                    @Override
                    public void run() {
                        List<GlucoseEntryEntity> entries = GlucosePredictionDatabase
                                .getInstance(mContext)
                                .glucoseDao()
                                .getAll();
                        GlucosePredictionDatabase.getInstance(mContext)
                                .glucoseDao()
                                .deleteAll();
                        RetrofitResource<List<GlucoseEntryEntity>> resource = new RetrofitResource<>();
                        resource.status = RetrofitResourceStatus.SUCCESS;
                        currentEntries.postValue(resource);
                    }
                });
    }

    private void onUploadEntries(List<GlucoseEntryEntity> entities) {
        RetrofitResource<String> loading = new RetrofitResource<>();
        loading.status = RetrofitResourceStatus.LOADING;
        uploadBulk.setValue(loading);

        GlucoseBulkEntry body = new GlucoseBulkEntry();
        body.username = SharedPreferencesUtils.getPreferences(mContext)
                .getString(mContext.getString(R.string.saved_username), "");
        body.data = new ArrayList<>();
        for (GlucoseEntryEntity entity : entities) {
            GlucoseHistoryEntry entry = new GlucoseHistoryEntry();
            entry.day = entity.day;
            entry.hour = entity.hour;
            entry.level = entity.level;
            entry.month = entity.month;
            entry.year = entity.year;
            body.data.add(entry);
        }

        repository.uploadBulk(body, new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                RetrofitResource<String> resource = new RetrofitResource<>();
                if (response.isSuccessful()) {
                    if (response.body().equals("ok")) {
                        resource.status = RetrofitResourceStatus.SUCCESS;
                    } else {
                        resource.status = RetrofitResourceStatus.ERROR;
                    }
                } else {
                    resource.status = RetrofitResourceStatus.ERROR;
                }
                uploadBulk.setValue(resource);
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                RetrofitResource<String> resource = new RetrofitResource<>();
                resource.status = RetrofitResourceStatus.ERROR;
                uploadBulk.setValue(resource);
            }
        });
    }

}
