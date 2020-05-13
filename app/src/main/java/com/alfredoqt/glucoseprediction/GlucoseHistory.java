package com.alfredoqt.glucoseprediction;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class GlucoseHistory {

    public class GlucoseHistoryEntry {

        @SerializedName("hour")
        public String hour;

        @SerializedName("level")
        public String level;

    }

    @SerializedName("data")
    public List<GlucoseHistoryEntry> data;

}
