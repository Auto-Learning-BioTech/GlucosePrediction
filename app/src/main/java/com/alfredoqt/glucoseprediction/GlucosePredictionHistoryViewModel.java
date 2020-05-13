package com.alfredoqt.glucoseprediction;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GlucosePredictionHistoryViewModel extends ViewModel {

    private GlucosePredictionRepository mRepository;
    public MutableLiveData<RetrofitResource<GlucoseHistory>> mHistory;

    public GlucosePredictionHistoryViewModel() {
        mRepository = new GlucosePredictionRepository();
        mHistory = new MutableLiveData<>();
    }

    public void onGetHistory(String csvName) {
        RetrofitResource<GlucoseHistory> loading = new RetrofitResource<>();
        loading.status = RetrofitResourceStatus.LOADING;
        mHistory.setValue(loading);

        mRepository.getHistory(csvName, new Callback<GlucoseHistory>() {
            @Override
            public void onResponse(Call<GlucoseHistory> call, Response<GlucoseHistory> response) {
                RetrofitResource<GlucoseHistory> resource = new RetrofitResource<>();
                if (response.isSuccessful()) {
                    resource.result = response.body();
                    resource.status = RetrofitResourceStatus.SUCCESS;
                } else {
                    resource.status = RetrofitResourceStatus.ERROR;
                }
                mHistory.setValue(resource);
            }

            @Override
            public void onFailure(Call<GlucoseHistory> call, Throwable t) {
                RetrofitResource<GlucoseHistory> resource = new RetrofitResource<>();
                resource.status = RetrofitResourceStatus.ERROR;
                mHistory.setValue(resource);
            }
        });
    }

}
