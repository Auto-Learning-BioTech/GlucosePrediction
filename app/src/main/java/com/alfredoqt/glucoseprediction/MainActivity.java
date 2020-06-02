package com.alfredoqt.glucoseprediction;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.material.tabs.TabLayout;

import org.threeten.bp.LocalDateTime;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();
    private MainViewModel mViewModel;

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

        mViewModel = new MainViewModel(this);

        configureObservers();
    }

    private void configureObservers() {
        mViewModel.userPredict.observe(this, new Observer<RetrofitResource<String>>() {
            @Override
            public void onChanged(RetrofitResource<String> resource) {
                handleUserPredict(resource);
            }
        });
        mViewModel.currentEntries.observe(this, new Observer<RetrofitResource<List<GlucoseEntryEntity>>>() {
            @Override
            public void onChanged(RetrofitResource<List<GlucoseEntryEntity>> resource) {
                handleCurrentEntries(resource);
            }
        });
        mViewModel.uploadBulk.observe(this, new Observer<RetrofitResource<String>>() {
            @Override
            public void onChanged(RetrofitResource<String> resource) {
                handleUpload(resource);
            }
        });
    }

    private void handleUpload(RetrofitResource<String> resource) {
        if (resource.status == RetrofitResourceStatus.ERROR) {
            Toast.makeText(getApplicationContext(), "Unknown", Toast.LENGTH_LONG).show();
        } else if (resource.status == RetrofitResourceStatus.SUCCESS) {
            Toast.makeText(getApplicationContext(), "Success", Toast.LENGTH_LONG).show();
        }
    }

    private void handleCurrentEntries(RetrofitResource<List<GlucoseEntryEntity>> resource) {
        if (resource.status == RetrofitResourceStatus.ERROR) {
            Toast.makeText(getApplicationContext(), "Unknown", Toast.LENGTH_LONG).show();
        } else if (resource.status == RetrofitResourceStatus.SUCCESS) {
            mViewModel.onUploadEntries(resource.result);
        }
    }

    private void handleUserPredict(RetrofitResource<String> resource) {
        if (resource.status == RetrofitResourceStatus.ERROR) {
            Toast.makeText(getApplicationContext(), "Unknown", Toast.LENGTH_LONG).show();
        } else if (resource.status == RetrofitResourceStatus.SUCCESS) {
            Toast.makeText(getApplicationContext(), "Success", Toast.LENGTH_LONG).show();
        }
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
        LocalDateTime localDateTime = LocalDateTime.now();
        String hour = String.valueOf(localDateTime.getHour());
        mViewModel.onUserPredict(hour);
    }

    private void handleBulkUpload() {
        mViewModel.onGetEntries();
    }


}
