package com.alfredoqt.glucoseprediction;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;

import org.threeten.bp.LocalDateTime;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class NewEntryFragment extends Fragment {

    private GlucosePredictionEntryViewModel mViewModel;
    private Button button;
    private TextInputEditText editText;

    public NewEntryFragment() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mViewModel = new GlucosePredictionEntryViewModel(getContext());
        configureObservers();
    }

    private void configureObservers() {
        mViewModel.mNewEntry.observe(this, new Observer<RetrofitResource<Void>>() {
            @Override
            public void onChanged(RetrofitResource<Void> resource) {
                if (resource.status == RetrofitResourceStatus.LOADING) {
                    if (button != null) {
                        button.setEnabled(false);
                    }
                    if (editText != null) {
                        editText.setText("");
                    }
                } else if (resource.status == RetrofitResourceStatus.ERROR) {
                    Toast.makeText(getContext(), "Error", Toast.LENGTH_SHORT).show();
                    if (button != null) {
                        button.setEnabled(true);
                    }
                } else {
                    Toast.makeText(getContext(), "Success", Toast.LENGTH_SHORT).show();
                    if (button != null) {
                        button.setEnabled(true);
                    }
                }
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_new_entry, container, false);

        final TextView textView = view.findViewById(R.id.current_date_text);
        editText = view.findViewById(R.id.glucose_text_field);
        button = view.findViewById(R.id.send_button);

        SimpleDateFormat formatter
                = new SimpleDateFormat ("yyyy.MM.dd hh:mm:ss", Locale.getDefault());
        String nowDate = formatter.format(new Date());

        textView.setText(getString(R.string.msg_time, nowDate));

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(editText.getText().toString())) {
                    return;
                }
                try {
                    int level = Integer.parseInt(editText.getText().toString());
                    if (level < 30 || level > 500) {
                        return;
                    }
                    // Call view model
                    LocalDateTime localDateTime = LocalDateTime.now();
                    String year = String.valueOf(localDateTime.getYear());
                    String month = String.valueOf(localDateTime.getMonthValue());
                    String day = String.valueOf(localDateTime.getDayOfMonth());
                    String hour = String.valueOf(localDateTime.getHour());
                    mViewModel.onNewEntry(
                            year,
                            month,
                            day,
                            hour,
                            String.valueOf(level)
                    );
                } catch (NumberFormatException e) {
                    return;
                }
            }
        });

        return view;
    }
}
