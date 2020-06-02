package com.alfredoqt.glucoseprediction;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterViewModel extends ViewModel {

    private UserRepository mRepository;
    public MutableLiveData<RetrofitResource<String>> mUserCreated;
    private Context mContext;

    public RegisterViewModel(Context context) {
        mContext = context;
        mRepository = new UserRepository();
        mUserCreated = new MutableLiveData<>();
    }

    public void onRegisterUser(final String username) {
        RetrofitResource<String> loading = new RetrofitResource<>();
        loading.status = RetrofitResourceStatus.LOADING;
        mUserCreated.setValue(loading);

        FirebaseInstanceId.getInstance().getInstanceId()
                .addOnCompleteListener(new OnCompleteListener<InstanceIdResult>() {
                    @Override
                    public void onComplete(@NonNull Task<InstanceIdResult> task) {
                        if (!task.isSuccessful()) {
                            RetrofitResource<String> error = new RetrofitResource<>();
                            error.status = RetrofitResourceStatus.ERROR;
                            mUserCreated.setValue(error);
                            return;
                        }

                        // Get new Instance ID token
                        String token = task.getResult().getToken();

                        mRepository.registerUser(username, token, new Callback<String>() {
                            @Override
                            public void onResponse(Call<String> call, Response<String> response) {
                                RetrofitResource<String> result = new RetrofitResource<>();
                                if (response.isSuccessful()) {
                                    result.result = response.body();
                                    if (response.body().equals("user already exists")) {
                                        result.status = RetrofitResourceStatus.ERROR;
                                    } else {
                                        result.status = RetrofitResourceStatus.SUCCESS;
                                        // Maybe not such a great idea to do it here
                                        SharedPreferencesUtils.getPreferences(mContext)
                                                .edit()
                                                .putString(mContext.getString(R.string.saved_username), username)
                                                .apply();
                                    }
                                } else {
                                    result.status = RetrofitResourceStatus.ERROR;
                                }
                                mUserCreated.setValue(result);
                            }

                            @Override
                            public void onFailure(Call<String> call, Throwable t) {
                                RetrofitResource<String> result = new RetrofitResource<>();
                                result.status = RetrofitResourceStatus.ERROR;
                                mUserCreated.setValue(result);
                            }
                        });
                    }
                });
    }

}
