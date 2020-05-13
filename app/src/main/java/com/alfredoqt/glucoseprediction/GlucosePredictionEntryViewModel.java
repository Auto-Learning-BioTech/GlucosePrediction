package com.alfredoqt.glucoseprediction;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GlucosePredictionEntryViewModel extends ViewModel {

    private GlucosePredictionRepository mRepository;
    public MutableLiveData<RetrofitResource<Void>> mNewEntry;

    public GlucosePredictionEntryViewModel() {
        mRepository = new GlucosePredictionRepository();
        mNewEntry = new MutableLiveData<>();
    }

    public void onNewEntry(String hour, String glucose, String day, String month, String username) {
        NewEntryBody body = new NewEntryBody();
        body.hour = hour;
        body.glucose = glucose;
        body.day = day;
        body.month = month;
        body.username = username;

        RetrofitResource<Void> loading = new RetrofitResource<>();
        loading.status = RetrofitResourceStatus.LOADING;
        mNewEntry.setValue(loading);

        mRepository.postNewEntry(body, new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                RetrofitResource<Void> resource = new RetrofitResource<>();
                if (response.isSuccessful()) {
                    resource.status = RetrofitResourceStatus.SUCCESS;
                } else {
                    resource.status = RetrofitResourceStatus.ERROR;
                }
                mNewEntry.setValue(resource);
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                RetrofitResource<Void> resource = new RetrofitResource<>();
                resource.status = RetrofitResourceStatus.ERROR;
                mNewEntry.setValue(resource);
            }
        });
    }

}
