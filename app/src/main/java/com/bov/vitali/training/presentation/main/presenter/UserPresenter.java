package com.bov.vitali.training.presentation.main.presenter;

import android.support.annotation.NonNull;

import com.arellomobile.mvp.InjectViewState;
import com.bov.vitali.training.App;
import com.bov.vitali.training.data.net.response.UserResponse;
import com.bov.vitali.training.presentation.base.presenter.BasePresenter;
import com.bov.vitali.training.presentation.main.view.UserView;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

@InjectViewState
public class UserPresenter extends BasePresenter<UserView> {

    public void getUser() {
        Call<UserResponse> userCall = App.getApi().getUser();
        userCall.enqueue(new Callback<UserResponse>() {
            @Override
            public void onResponse(@NonNull Call<UserResponse> call, @NonNull Response<UserResponse> response) {
                if (response.isSuccessful()) {
                    getViewState().setUser(response.body().getUser());
                } else {
                    getViewState().showResponseError();
                }
            }

            @Override
            public void onFailure(@NonNull Call<UserResponse> call, @NonNull Throwable t) {
                getViewState().showResponseError();
            }
        });
    }
}