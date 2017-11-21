package com.bov.vitali.training.presentation.login.presenter;

import android.support.annotation.NonNull;
import android.util.Log;

import com.bov.vitali.training.App;
import com.bov.vitali.training.BuildConfig;
import com.bov.vitali.training.data.net.TrainingApi;
import com.bov.vitali.training.presentation.navigation.Screens;
import com.bov.vitali.training.common.preferences.Preferences;
import com.bov.vitali.training.common.utils.Constants;
import com.bov.vitali.training.data.net.response.LoginResponse;
import com.bov.vitali.training.data.net.response.UserResponse;
import com.bov.vitali.training.presentation.base.presenter.BasePresenter;
import com.bov.vitali.training.presentation.login.view.SplashContract;

import javax.inject.Inject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import ru.terrakok.cicerone.Router;

public class SplashPresenter extends BasePresenter<SplashContract.View> implements SplashContract.Presenter {
    @Inject Router router;
    @Inject TrainingApi api;

    public SplashPresenter() {
        App.instance.getAppComponent().inject(this);
    }

    @Override
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

    @Override
    public void navigateToLoginFragment() {
        router.navigateTo(Screens.LOGIN_FRAGMENT);
    }

    @Override
    public void navigateToBottomNavigationActivity() {
        router.navigateTo(Screens.MAIN_ACTIVITY);
    }

    @Override
    public void getRefreshToken() {
        Call<LoginResponse> tokenCall = api.refreshToken(
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
                    getUserId();
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

    private void getUserId() {
        Call<UserResponse> userCall = api.getUser();
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

    @Override
    public void onBackPressed() {
        router.exit();
    }
}