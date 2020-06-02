package com.alfredoqt.glucoseprediction;

import android.content.Context;
import android.content.SharedPreferences;

public abstract class SharedPreferencesUtils {

    public static SharedPreferences getPreferences(Context context) {
        return context.getSharedPreferences(context.getString(R.string.preferences_name), Context.MODE_PRIVATE);
    }

}
