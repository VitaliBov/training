package com.bov.vitali.training.presentation.login.presenter;

import android.support.annotation.NonNull;
import android.util.Log;

import com.bov.vitali.training.BuildConfig;
import com.bov.vitali.training.TrainingApplication;
import com.bov.vitali.training.common.navigation.Screens;
import com.bov.vitali.training.common.preferences.Preferences;
import com.bov.vitali.training.common.utils.Constants;
import com.bov.vitali.training.data.network.response.LoginResponse;
import com.bov.vitali.training.presentation.base.presenter.BasePresenter;
import com.bov.vitali.training.presentation.login.view.SplashView;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SplashPresenter extends BasePresenter<SplashView> {

    public void navigateToNextScreen() {
        if (isValidToken()) {
            navigateToMainActivity();
        } else {
            navigateToLoginFragment();
        }
    }

    private void navigateToLoginFragment() {
        TrainingApplication.INSTANCE.getRouter().navigateTo(Screens.LOGIN_FRAGMENT);
    }

    private void navigateToMainActivity() {
        TrainingApplication.INSTANCE.getRouter().navigateTo(Screens.MAIN_ACTIVITY);
    }

    private boolean isValidToken() {
        Long timeStamp = System.currentTimeMillis();
        if (Preferences.getAccessToken(TrainingApplication.appContext()).isEmpty()) {
            return false;
        } else if (timeStamp > Preferences.getExpiresAt(TrainingApplication.appContext())) {
            getRefreshToken();
            return true;
        }
        return true;
    }

    private void getRefreshToken() {
        Call<LoginResponse> tokenCall = TrainingApplication.getApi().refreshToken(
                Preferences.getRefreshToken(TrainingApplication.appContext()), BuildConfig.MEDIUM_CLIENT_ID,
                BuildConfig.MEDIUM_CLIENT_SECRET, Constants.GRANT_TYPE_REFRESH);
        tokenCall.enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(@NonNull Call<LoginResponse> call, @NonNull Response<LoginResponse> response) {
                if (response.isSuccessful()) {
                    Preferences.setTokenType(TrainingApplication.appContext(), response.body().getTokenType());
                    Preferences.setAccessToken(TrainingApplication.appContext(), response.body().getAccessToken());
                    Preferences.setRefreshToken(TrainingApplication.appContext(), response.body().getRefreshToken());
                    Preferences.setScope(TrainingApplication.appContext(), response.body().getScope());
                    Preferences.setExpiresAt(TrainingApplication.appContext(), response.body().getExpiresAt());
                } else {
                    Log.e("Error", "Error");
                }
            }

            @Override
            public void onFailure(@NonNull Call<LoginResponse> call, @NonNull Throwable t) {
                t.printStackTrace();
            }
        });
    }
}