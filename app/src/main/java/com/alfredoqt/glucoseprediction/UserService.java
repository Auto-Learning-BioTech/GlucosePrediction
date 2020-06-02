package com.alfredoqt.glucoseprediction;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface UserService {

    @FormUrlEncoded
    @POST("login_check")
    Call<String> loginCheck(@Field("username") String username);

    @FormUrlEncoded
    @POST("register_user")
    Call<String> registerUser(@Field("username") String username, @Field("device_token") String deviceToken);

    @FormUrlEncoded
    @POST("update_device_token")
    Call<String> updateDeviceToken(@Field("username") String username, @Field("device_token") String deviceToken);

}
