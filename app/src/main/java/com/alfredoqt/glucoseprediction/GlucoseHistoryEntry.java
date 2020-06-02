package com.alfredoqt.glucoseprediction;

import com.google.gson.annotations.SerializedName;

public class GlucoseHistoryEntry {

    @SerializedName("datetime")
    public String datetime;

    @SerializedName("glucose_level")
    public String glucoseLevel;

    @SerializedName("hour")
    public String hour;

    @SerializedName("day")
    public String day;

    @SerializedName("username_fk")
    public String usernameFK;

    @SerializedName("year")
    public String year;

    @SerializedName("month")
    public String month;

}
