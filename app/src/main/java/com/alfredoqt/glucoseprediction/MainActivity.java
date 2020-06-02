package com.alfredoqt.glucoseprediction;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.log_out:
                handleLogout();
                return true;
            case R.id.status_request:
                handleStatusRequest();
                return true;
            case R.id.bulk_upload:
                handleBulkUpload();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void handleLogout() {
        SharedPreferencesUtils.getPreferences(this)
                .edit()
                .remove(getString(R.string.saved_username))
                .apply();
        // Delete all local entries
        GlucosePredictionDatabase.databaseWriteExecutor
                .execute(new Runnable() {
                    @Override
                    public void run() {
                        GlucosePredictionDatabase.getInstance(MainActivity.this)
                                .glucoseDao()
                                .deleteAll();
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Intent intent = new Intent(MainActivity.this, RegisterActivity.class);
                                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                finish();
                                startActivity(intent);
                            }
                        });
                    }
                });
    }

    private void handleStatusRequest() {
    }

    private void handleBulkUpload() {
    }


}
