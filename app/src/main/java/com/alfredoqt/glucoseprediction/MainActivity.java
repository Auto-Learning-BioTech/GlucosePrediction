package com.alfredoqt.glucoseprediction;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;

import com.google.android.material.tabs.TabLayout;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ViewPager viewPager = findViewById(R.id.view_pager);
        TabLayout tabLayout = findViewById(R.id.tab_layout);

        tabLayout.setupWithViewPager(viewPager);

        GlucoseViewPagerAdapter adapter = new GlucoseViewPagerAdapter(
                getSupportFragmentManager(),
                new Fragment[]{new NewEntryFragment(), new HistoryFragment()},
                new String[]{getString(R.string.action_new_entry), getString(R.string.action_see_history)}
        );

        viewPager.setAdapter(adapter);
    }

}
