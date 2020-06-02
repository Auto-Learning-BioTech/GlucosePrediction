package com.alfredoqt.glucoseprediction;

import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class UserRepository {

    private UserService mService;

    public UserRepository() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(ApiConstants.URL)
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        mService = retrofit.create(UserService.class);
    }

    public void loginCheck(String username, Callback<String> callback) {
        mService.loginCheck(username).enqueue(callback);
    }

    public void registerUser(String username, String deviceToken, Callback<String> callback) {
        mService.registerUser(username, deviceToken).enqueue(callback);
    }

    public void updateDeviceToken(String username, String deviceToken, Callback<String> callback) {
        mService.updateDeviceToken(username, deviceToken).enqueue(callback);
    }

}
