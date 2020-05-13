package com.alfredoqt.glucoseprediction;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;

import java.util.ArrayList;
import java.util.List;

public class HistoryFragment extends Fragment {

    private LineChart chart;
    private GlucosePredictionHistoryViewModel mViewModel;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mViewModel = new GlucosePredictionHistoryViewModel();
        configureObservers();
        mViewModel.onGetHistory("personal.csv");
    }

    private void configureObservers() {
        mViewModel.mHistory.observe(this, new Observer<RetrofitResource<GlucoseHistory>>() {
            @Override
            public void onChanged(RetrofitResource<GlucoseHistory> resource) {
                if (resource.status == RetrofitResourceStatus.SUCCESS) {
                    if (chart != null) {
                        List<Entry> yEntries = new ArrayList<>();
                        for (GlucoseHistory.GlucoseHistoryEntry entry : resource.result.data) {
                            yEntries.add(new Entry(Integer.parseInt(entry.hour), Integer.parseInt(entry.level)));
                        }
                        LineDataSet set = new LineDataSet(yEntries, "Glucose vs. Hour");
                        List<ILineDataSet> sets = new ArrayList<>();
                        sets.add(set);
                        LineData data = new LineData(sets);

                        chart.setData(data);
                    }
                } else if (resource.status == RetrofitResourceStatus.ERROR) {
                    Toast.makeText(getContext(), "Error", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_history, container, false);
        chart = view.findViewById(R.id.line_chart);


        return view;
    }
}
