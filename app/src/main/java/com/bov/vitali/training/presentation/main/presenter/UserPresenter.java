package com.bov.vitali.training.presentation.main.presenter;

import android.support.annotation.NonNull;

import com.arellomobile.mvp.InjectViewState;
import com.bov.vitali.training.App;
import com.bov.vitali.training.data.net.response.UserResponse;
import com.bov.vitali.training.presentation.base.presenter.BasePresenter;
import com.bov.vitali.training.presentation.main.view.UserContract;

import javax.inject.Inject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import ru.terrakok.cicerone.Router;

@InjectViewState
public class UserPresenter extends BasePresenter<UserContract.View> implements UserContract.Presenter {
    @Inject Router router;

    public UserPresenter() {
        App.INSTANCE.getAppComponent().inject(this);
    }

    @Override
    public void getUser() {
        Call<UserResponse> userCall = App.getTrainingApi().getUser();
        userCall.enqueue(new Callback<UserResponse>() {
            @Override
            public void onResponse(@NonNull Call<UserResponse> call, @NonNull
                    Response<UserResponse> response) {
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

    @Override
    public void onBackPressed() {
        router.finishChain();
    }
}