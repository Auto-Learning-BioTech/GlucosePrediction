package com.alfredoqt.glucoseprediction;

import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class GlucosePredictionRepository {

    private GlucosePredictionService mService;

    public GlucosePredictionRepository() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(ApiConstants.URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        mService = retrofit.create(GlucosePredictionService.class);
    }

    public void postNewEntry(NewEntryBody body, Callback<Void> callback) {
        mService.postNewGlucoseEntry(body).enqueue(callback);
    }

    public void getHistory(String fileName, Callback<GlucoseHistory> callback) {
        mService.getGlucoseHistory(fileName).enqueue(callback);
    }

    public void getStatus(String hour, String token, Callback<Void> callback) {
        mService.requestStatus(hour, token).enqueue(callback);
    }

}
