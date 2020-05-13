package com.alfredoqt.glucoseprediction;

import com.google.gson.annotations.SerializedName;

public class NewEntryBody {

    @SerializedName("hour")
    public String hour;

    @SerializedName("glucose")
    public String glucose;

    @SerializedName("day")
    public String day;

    @SerializedName("month")
    public String month;

    @SerializedName("username")
    public String username;

}
