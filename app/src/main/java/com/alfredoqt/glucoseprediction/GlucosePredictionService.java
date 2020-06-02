package com.alfredoqt.glucoseprediction;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface GlucosePredictionService {

    @POST("insert")
    Call<Void> postNewGlucoseEntry(@Body NewEntryBody body);

    @FormUrlEncoded
    @POST("get_history")
    Call<List<GlucoseHistoryEntry>> getGlucoseHistory(@Field("username") String username, @Field("days") String days);

    @GET("status")
    Call<Void> requestStatus(@Query("hour") String hour, @Query("token") String token);

    @POST("insert_json_db")
    Call<String> uploadBulk(@Body GlucoseBulkEntry body);

    @FormUrlEncoded
    @POST("user_predict")
    Call<String> userPredict(@Field("username") String username, @Field("hour") String hour);

}
