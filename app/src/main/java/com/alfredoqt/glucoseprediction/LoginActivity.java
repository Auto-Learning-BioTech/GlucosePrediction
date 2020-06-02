package com.alfredoqt.glucoseprediction;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;

public class LoginActivity extends AppCompatActivity {

    private RelativeLayout mProgress;
    private TextInputEditText mEditText;
    private LoginViewModel mViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mProgress = findViewById(R.id.progress);
        mEditText = findViewById(R.id.user_text_field);

        mViewModel = new LoginViewModel(this);

        configureObservers();
    }

    private void configureObservers() {
        mViewModel.mUserLogged.observe(this, new Observer<RetrofitResource<String>>() {
            @Override
            public void onChanged(RetrofitResource<String> resource) {
                handleLogin(resource);
            }
        });
    }

    private void handleLogin(RetrofitResource<String> resource) {
        if (resource.status == RetrofitResourceStatus.LOADING) {
            if (mProgress.getVisibility() != View.VISIBLE) {
                mProgress.setVisibility(View.VISIBLE);
            }
            ViewUtils.hideKeyboard(mProgress);
        } else if (resource.status == RetrofitResourceStatus.ERROR) {
            if (mProgress.getVisibility() != View.GONE) {
                mProgress.setVisibility(View.GONE);
            }
            if (resource.result != null) {
                Toast.makeText(getApplicationContext(), resource.result, Toast.LENGTH_LONG).show();
            }
        } else {
            if (mProgress.getVisibility() != View.GONE) {
                mProgress.setVisibility(View.GONE);
            }
            Intent intent = new Intent(this, MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            finish();
            startActivity(intent);
        }
    }

    public void clickSend(View view) {
        if (TextUtils.isEmpty(mEditText.getText().toString())) {
            return;
        }

        mViewModel.onLoginUser(mEditText.getText().toString());
    }

    public void clickRegister(View view) {
        // We know it comes after
        finish();
    }
}
