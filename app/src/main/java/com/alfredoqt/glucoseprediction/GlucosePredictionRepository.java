package com.alfredoqt.glucoseprediction;

import java.util.List;

import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class GlucosePredictionRepository {

    private GlucosePredictionService mService;

    public GlucosePredictionRepository() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(ApiConstants.URL)
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        mService = retrofit.create(GlucosePredictionService.class);
    }

    public void postNewEntry(NewEntryBody body, Callback<Void> callback) {
        mService.postNewGlucoseEntry(body).enqueue(callback);
    }

    public void getHistory(String username, String days, Callback<List<GlucoseHistoryEntry>> callback) {
        mService.getGlucoseHistory(username, days).enqueue(callback);
    }

    public void getStatus(String hour, String token, Callback<Void> callback) {
        mService.requestStatus(hour, token).enqueue(callback);
    }

    public void uploadBulk(GlucoseBulkEntry body, Callback<String> callback) {
        mService.uploadBulk(body).enqueue(callback);
    }

    public void userPredict(String username, String hour, Callback<String> callback) {
        mService.userPredict(username, hour).enqueue(callback);
    }

}
