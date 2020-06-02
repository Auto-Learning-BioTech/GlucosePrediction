package com.alfredoqt.glucoseprediction;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class GlucoseBulkEntry {

    @SerializedName("username")
    public String username;

    @SerializedName("data")
    public List<GlucoseHistoryEntry> data;

}
