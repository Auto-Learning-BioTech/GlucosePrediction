package com.alfredoqt.glucoseprediction;

import android.content.Context;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GlucosePredictionHistoryViewModel extends ViewModel {

    private GlucosePredictionRepository mRepository;
    public MutableLiveData<RetrofitResource<List<GlucoseHistoryEntry>>> mHistory;
    private Context mContext;

    public GlucosePredictionHistoryViewModel(Context context) {
        mRepository = new GlucosePredictionRepository();
        mHistory = new MutableLiveData<>();
        mContext = context;
    }

    public void onGetHistory(String days) {
        RetrofitResource<List<GlucoseHistoryEntry>> loading = new RetrofitResource<>();
        loading.status = RetrofitResourceStatus.LOADING;
        mHistory.setValue(loading);
        String username = SharedPreferencesUtils.getPreferences(mContext)
                .getString(mContext.getString(R.string.saved_username), "");
        mRepository.getHistory(username, days, new Callback<List<GlucoseHistoryEntry>>() {
            @Override
            public void onResponse(Call<List<GlucoseHistoryEntry>> call, Response<List<GlucoseHistoryEntry>> response) {
                RetrofitResource<List<GlucoseHistoryEntry>> resource = new RetrofitResource<>();
                if (response.isSuccessful()) {
                    resource.result = response.body();
                    resource.status = RetrofitResourceStatus.SUCCESS;
                } else {
                    resource.status = RetrofitResourceStatus.ERROR;
                }
                mHistory.setValue(resource);
            }

            @Override
            public void onFailure(Call<List<GlucoseHistoryEntry>> call, Throwable t) {
                RetrofitResource<List<GlucoseHistoryEntry>> resource = new RetrofitResource<>();
                resource.status = RetrofitResourceStatus.ERROR;
                mHistory.setValue(resource);
            }
        });
    }

}
