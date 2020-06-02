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
import com.google.android.material.textfield.TextInputLayout;

public class RegisterActivity extends AppCompatActivity {

    private RelativeLayout mProgress;
    private TextInputEditText mEditText;
    private RegisterViewModel mViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        mProgress = findViewById(R.id.progress);
        mEditText = findViewById(R.id.user_text_field);

        mViewModel = new RegisterViewModel(this);

        configureObservers();
    }

    private void configureObservers() {
        mViewModel.mUserCreated.observe(this, new Observer<RetrofitResource<String>>() {
            @Override
            public void onChanged(RetrofitResource<String> resource) {
                handleCreation(resource);
            }
        });
    }

    private void handleCreation(RetrofitResource<String> resource) {
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
        // At least three characters
        if (mEditText.getText().toString().length() < 4) {
            return;
        }

        mViewModel.onRegisterUser(mEditText.getText().toString());
    }

    public void clickLogin(View view) {
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
    }
}
