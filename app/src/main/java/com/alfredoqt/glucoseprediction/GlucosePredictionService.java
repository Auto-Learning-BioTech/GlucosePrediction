package com.alfredoqt.glucoseprediction;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface GlucosePredictionService {

    @POST("insert")
    Call<Void> postNewGlucoseEntry(@Body NewEntryBody body);

    @GET("get/graph_data")
    Call<GlucoseHistory> getGlucoseHistory(@Query("name") String csv);

    @GET("status")
    Call<Void> requestStatus(@Query("hour") String hour, @Query("token") String token);

}
