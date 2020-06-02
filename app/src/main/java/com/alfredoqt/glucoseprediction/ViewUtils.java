package com.alfredoqt.glucoseprediction;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

public abstract class ViewUtils {

    public static void showKeyboard(View view) {
        ((InputMethodManager)view.getContext().getSystemService(Context.INPUT_METHOD_SERVICE)).showSoftInput(view, 1);
    }

    public static void hideKeyboard(View view) {
        ((InputMethodManager)view.getContext().getSystemService(Context.INPUT_METHOD_SERVICE)).hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

}
