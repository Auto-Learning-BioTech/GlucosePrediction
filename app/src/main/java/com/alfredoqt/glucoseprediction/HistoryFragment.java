package com.alfredoqt.glucoseprediction;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.charts.ScatterChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.data.ScatterData;
import com.github.mikephil.charting.data.ScatterDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.github.mikephil.charting.interfaces.datasets.IScatterDataSet;

import java.util.ArrayList;
import java.util.List;

public class HistoryFragment extends Fragment {

    private ScatterChart chart;
    private GlucosePredictionHistoryViewModel mViewModel;

    private static final String ONE_MONTH = "30";
    private static final String THREE_MONTHS = "90";
    private static final String ONE_YEAR = "365";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mViewModel = new GlucosePredictionHistoryViewModel(getContext());
        configureObservers();
        mViewModel.onGetHistory(ONE_MONTH);
    }

    private void configureObservers() {
        mViewModel.mHistory.observe(this, new Observer<RetrofitResource<List<GlucoseHistoryEntry>>>() {
            @Override
            public void onChanged(RetrofitResource<List<GlucoseHistoryEntry>> resource) {
                if (resource.status == RetrofitResourceStatus.SUCCESS) {
                    if (chart != null) {
                        List<Entry> yEntries = new ArrayList<>();
                        for (GlucoseHistoryEntry entry : resource.result) {
                            yEntries.add(new Entry(Integer.parseInt(entry.glucoseLevel), Integer.parseInt(entry.hour)));
                        }
                        ScatterDataSet set = new ScatterDataSet(yEntries, "Hour vs. Glucose");
                        List<IScatterDataSet> sets = new ArrayList<>();
                        sets.add(set);
                        ScatterData data = new ScatterData(sets);

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
