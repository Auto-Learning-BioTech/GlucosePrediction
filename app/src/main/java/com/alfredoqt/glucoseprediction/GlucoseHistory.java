package com.alfredoqt.glucoseprediction;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class GlucoseHistory {

    @SerializedName("data")
    public List<GlucoseHistoryEntry> data;

}
