package com.bov.vitali.training.presentation.login.presenter;

import android.support.annotation.NonNull;
import android.util.Log;

import com.bov.vitali.training.App;
import com.bov.vitali.training.BuildConfig;
import com.bov.vitali.training.common.navigation.Screens;
import com.bov.vitali.training.common.preferences.Preferences;
import com.bov.vitali.training.common.utils.Constants;
import com.bov.vitali.training.data.net.response.LoginResponse;
import com.bov.vitali.training.data.net.response.UserResponse;
import com.bov.vitali.training.presentation.base.presenter.BasePresenter;
import com.bov.vitali.training.presentation.login.view.SplashView;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SplashPresenter extends BasePresenter<SplashView> {

    public void navigateToNextScreen() {
        if (isValidToken()) {
            navigateToBottomNavigationActivity();
        } else {
            navigateToLoginFragment();
        }
    }

    private boolean isValidToken() {
        Long timeStamp = System.currentTimeMillis();
        if (Preferences.getAccessToken(App.appContext()).isEmpty()) {
            return false;
        } else if (timeStamp > Preferences.getExpiresAt(App.appContext())) {
            getRefreshToken();
            return false;
        } else {
            return true;
        }
    }

    private void navigateToLoginFragment() {
        App.INSTANCE.getRouter().navigateTo(Screens.LOGIN_FRAGMENT);
    }

    private void navigateToBottomNavigationActivity() {
        App.INSTANCE.getRouter().navigateTo(Screens.BOTTOM_NAVIGATION_ACTIVITY);
    }

    private void getRefreshToken() {
        Call<LoginResponse> tokenCall = App.getApi().refreshToken(
                Preferences.getRefreshToken(App.appContext()), BuildConfig.MEDIUM_CLIENT_ID,
                BuildConfig.MEDIUM_CLIENT_SECRET, Constants.GRANT_TYPE_REFRESH);
        tokenCall.enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(@NonNull Call<LoginResponse> call, @NonNull Response<LoginResponse> response) {
                if (response.isSuccessful()) {
                    Preferences.setTokenType(App.appContext(), response.body().getTokenType());
                    Preferences.setAccessToken(App.appContext(), response.body().getAccessToken());
                    Preferences.setRefreshToken(App.appContext(), response.body().getRefreshToken());
                    Preferences.setScope(App.appContext(), response.body().getScope());
                    Preferences.setExpiresAt(App.appContext(), response.body().getExpiresAt());
                    getUser();
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

    private void getUser() {
        Call<UserResponse> userCall = App.getApi().getUser();
        userCall.enqueue(new Callback<UserResponse>() {
            @Override
            public void onResponse(@NonNull Call<UserResponse> call, @NonNull Response<UserResponse> response) {
                if (response.isSuccessful()) {
                    Preferences.setUserId(App.appContext(), response.body().getUser().getId());
                } else {
                    Log.e("Error", "Error");
                }
            }

            @Override
            public void onFailure(@NonNull Call<UserResponse> call, @NonNull Throwable t) {
                t.printStackTrace();
            }
        });
    }
}