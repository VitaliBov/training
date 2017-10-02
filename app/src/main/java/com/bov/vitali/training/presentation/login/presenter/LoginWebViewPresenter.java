package com.bov.vitali.training.presentation.login.presenter;

import com.arellomobile.mvp.InjectViewState;
import com.bov.vitali.training.BuildConfig;
import com.bov.vitali.training.TrainingApplication;
import com.bov.vitali.training.common.preferences.Preferences;
import com.bov.vitali.training.common.utils.Constants;
import com.bov.vitali.training.data.network.response.LoginResponse;
import com.bov.vitali.training.presentation.base.presenter.BasePresenter;
import com.bov.vitali.training.presentation.login.view.LoginWebView;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

@InjectViewState
public class LoginWebViewPresenter extends BasePresenter<LoginWebView> {

    public void getToken(String code) {
        Call<LoginResponse> tokenCall = TrainingApplication.getApi().getToken(
             code, BuildConfig.MEDIUM_CLIENT_ID, BuildConfig.MEDIUM_CLIENT_SECRET, Constants.GRANT_TYPE, Constants.REDIRECT_URL);
        tokenCall.enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                Preferences.setTokenType(TrainingApplication.appContext(), response.body().getTokenType());
                Preferences.setAccessToken(TrainingApplication.appContext(), response.body().getAccessToken());
                Preferences.setRefreshToken(TrainingApplication.appContext(), response.body().getRefreshToken());
                Preferences.setScope(TrainingApplication.appContext(), response.body().getScope());
                Preferences.setExpiresAt(TrainingApplication.appContext(), response.body().getExpiresAt());
            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                t.printStackTrace();
            }
        });

    }

    public void onBackPressed() {
        TrainingApplication.INSTANCE.getRouter().exit();
    }
}