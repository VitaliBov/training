package com.bov.vitali.training.presentation.main.presenter;

import android.support.annotation.NonNull;

import com.arellomobile.mvp.InjectViewState;
import com.bov.vitali.training.App;
import com.bov.vitali.training.data.net.response.PublicationResponse;
import com.bov.vitali.training.presentation.base.presenter.BasePresenter;
import com.bov.vitali.training.presentation.main.view.PublicationsView;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

@InjectViewState
public class PublicationsPresenter extends BasePresenter<PublicationsView> {

    public void getPublications(String userId) {
        Call<PublicationResponse> publicationCall = App.getApi().getPublications(userId);
        publicationCall.enqueue(new Callback<PublicationResponse>() {
            @Override
            public void onResponse(@NonNull Call<PublicationResponse> call, @NonNull Response<PublicationResponse> response) {
                if (response.isSuccessful()) {
                    getViewState().setPublications(response.body().getPublications());
                } else {
                    getViewState().showResponseError();
                }
            }

            @Override
            public void onFailure(@NonNull Call<PublicationResponse> call, @NonNull Throwable t) {
                getViewState().showResponseError();
            }
        });
    }
}