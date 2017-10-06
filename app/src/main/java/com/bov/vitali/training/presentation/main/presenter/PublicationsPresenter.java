package com.bov.vitali.training.presentation.main.presenter;

import android.support.annotation.NonNull;

import com.arellomobile.mvp.InjectViewState;
import com.bov.vitali.training.App;
import com.bov.vitali.training.data.model.Publication;
import com.bov.vitali.training.presentation.base.presenter.BasePresenter;
import com.bov.vitali.training.presentation.main.view.PublicationsView;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

@InjectViewState
public class PublicationsPresenter extends BasePresenter<PublicationsView> {

    public void getPublications(String userId) {
        Call<Publication> publicationCall = App.getApi().getPublications(userId);
        publicationCall.enqueue(new Callback<Publication>() {
            @Override
            public void onResponse(@NonNull Call<Publication> call, @NonNull Response<Publication> response) {
                if (response.isSuccessful()) {
                    getViewState().setPublications(response.body());
                } else {
                    getViewState().showError();
                }
            }

            @Override
            public void onFailure(@NonNull Call<Publication> call, @NonNull Throwable t) {
                t.printStackTrace();
            }
        });
    }
}