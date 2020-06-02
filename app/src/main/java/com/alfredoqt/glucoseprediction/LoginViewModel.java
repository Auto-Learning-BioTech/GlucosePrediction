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

public class LoginViewModel extends ViewModel {

    private UserRepository mRepository;
    public MutableLiveData<RetrofitResource<String>> mUserLogged;
    private Context mContext;

    public LoginViewModel(Context context) {
        mContext = context;
        mRepository = new UserRepository();
        mUserLogged = new MutableLiveData<>();
    }

    public void onLoginUser(final String username) {
        RetrofitResource<String> loading = new RetrofitResource<>();
        loading.status = RetrofitResourceStatus.LOADING;
        mUserLogged.setValue(loading);

        FirebaseInstanceId.getInstance().getInstanceId()
                .addOnCompleteListener(new OnCompleteListener<InstanceIdResult>() {
                    @Override
                    public void onComplete(@NonNull Task<InstanceIdResult> task) {
                        if (!task.isSuccessful()) {
                            RetrofitResource<String> error = new RetrofitResource<>();
                            error.status = RetrofitResourceStatus.ERROR;
                            mUserLogged.setValue(error);
                            return;
                        }

                        // Get new Instance ID token
                        final String token = task.getResult().getToken();

                        mRepository.loginCheck(username, new Callback<String>() {
                            @Override
                            public void onResponse(Call<String> call, Response<String> response) {
                                RetrofitResource<String> result = new RetrofitResource<>();
                                if (response.isSuccessful()) {
                                    if (response.body().equals("non existent")) {
                                        result.result = response.body();
                                        result.status = RetrofitResourceStatus.ERROR;
                                        mUserLogged.setValue(result);
                                    } else { // Yep, pretty much. Hope it does not throw some error
                                        mRepository.updateDeviceToken(username, token, new Callback<String>() {
                                            @Override
                                            public void onResponse(Call<String> call, Response<String> responseUpdate) {
                                                RetrofitResource<String> resultUpdate = new RetrofitResource<>();
                                                resultUpdate.result = responseUpdate.body();
                                                if (responseUpdate.isSuccessful()) {
                                                    if (!responseUpdate.body().equals("updated")) {
                                                        resultUpdate.status = RetrofitResourceStatus.ERROR;
                                                        mUserLogged.setValue(resultUpdate);
                                                    } else {
                                                        resultUpdate.status = RetrofitResourceStatus.SUCCESS;
                                                        SharedPreferencesUtils.getPreferences(mContext)
                                                                .edit()
                                                                .putString(mContext.getString(R.string.saved_username), username)
                                                                .apply();
                                                        mUserLogged.setValue(resultUpdate);
                                                    }
                                                } else {
                                                    resultUpdate.status = RetrofitResourceStatus.ERROR;
                                                    mUserLogged.setValue(resultUpdate);
                                                }
                                            }

                                            @Override
                                            public void onFailure(Call<String> call, Throwable t) {

                                            }
                                        });
                                    }
                                } else {
                                    result.status = RetrofitResourceStatus.ERROR;
                                    mUserLogged.setValue(result);
                                }
                            }

                            @Override
                            public void onFailure(Call<String> call, Throwable t) {
                                RetrofitResource<String> result = new RetrofitResource<>();
                                result.status = RetrofitResourceStatus.ERROR;
                                mUserLogged.setValue(result);
                            }
                        });
                    }
                });
    }

}
