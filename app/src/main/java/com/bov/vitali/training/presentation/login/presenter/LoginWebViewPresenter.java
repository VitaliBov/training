package com.bov.vitali.training.presentation.login.presenter;

import android.support.annotation.NonNull;
import android.util.Log;

import com.arellomobile.mvp.InjectViewState;
import com.bov.vitali.training.App;
import com.bov.vitali.training.BuildConfig;
import com.bov.vitali.training.data.net.TrainingApi;
import com.bov.vitali.training.presentation.navigation.Screens;
import com.bov.vitali.training.common.preferences.Preferences;
import com.bov.vitali.training.common.utils.Constants;
import com.bov.vitali.training.data.net.response.LoginResponse;
import com.bov.vitali.training.data.net.response.UserResponse;
import com.bov.vitali.training.presentation.base.presenter.BasePresenter;
import com.bov.vitali.training.presentation.login.view.LoginWebViewContract;

import javax.inject.Inject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import ru.terrakok.cicerone.Router;

@InjectViewState
public class LoginWebViewPresenter extends BasePresenter<LoginWebViewContract.View>
        implements LoginWebViewContract.Presenter {
    private static final String HOST = "https://medium.com/m/oauth/authorize?";
    private static final String CLIENT_ID = "client_id=";
    private static final String SCOPE = "&scope=";
    private static final String SCOPE_PARAMETER = "basicProfile,publishPost,listPublications";
    private static final String STATE = "&state=";
    private static final String STATE_PARAMETER = "training";
    private static final String RESPONSE_TYPE = "&response_type=";
    private static final String RESPONSE_TYPE_PARAMETER = "code";
    private static final String REDIRECT_URL = "&redirect_uri=";
    private static final String REDIRECT_URL_PARAMETER = "https://bitbucket.org/vitalibov/training/";
    @Inject Router router;
    @Inject TrainingApi api;

    public LoginWebViewPresenter() {
        App.INSTANCE.getAppComponent().inject(this);
    }

    @Override
    public void getToken(String code) {
        Call<LoginResponse> tokenCall = api.getToken(
                code, BuildConfig.MEDIUM_CLIENT_ID, BuildConfig.MEDIUM_CLIENT_SECRET, Constants.GRANT_TYPE, Constants.REDIRECT_URL);
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
                    navigateToBottomNavigationActivity();
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
    public void navigateToBottomNavigationActivity() {
        router.navigateTo(Screens.MAIN_ACTIVITY);
    }

    public String getUrl() {
        return HOST +
                CLIENT_ID + BuildConfig.MEDIUM_CLIENT_ID +
                SCOPE + SCOPE_PARAMETER +
                STATE + STATE_PARAMETER +
                RESPONSE_TYPE + RESPONSE_TYPE_PARAMETER +
                REDIRECT_URL + REDIRECT_URL_PARAMETER;
    }

    @Override
    public void onBackPressed() {
        router.exit();
    }
}