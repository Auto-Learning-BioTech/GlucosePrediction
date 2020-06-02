package com.alfredoqt.glucoseprediction;

import com.google.gson.annotations.SerializedName;

public class GlucoseEntry {

    @SerializedName("year")
    public String year;

    @SerializedName("month")
    public String month;

    @SerializedName("day")
    public String day;

    @SerializedName("hour")
    public String hour;

    @SerializedName("level")
    public String level;

}
