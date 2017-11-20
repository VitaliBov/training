package com.bov.vitali.training.presentation.main.presenter;

import android.support.annotation.NonNull;

import com.arellomobile.mvp.InjectViewState;
import com.bov.vitali.training.App;
import com.bov.vitali.training.data.net.TrainingApi;
import com.bov.vitali.training.data.net.response.PublicationResponse;
import com.bov.vitali.training.presentation.base.presenter.BasePresenter;
import com.bov.vitali.training.presentation.main.view.PublicationsContract;

import javax.inject.Inject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import ru.terrakok.cicerone.Router;

@InjectViewState
public class PublicationsPresenter extends BasePresenter<PublicationsContract.View>
        implements PublicationsContract.Presenter {
    @Inject Router router;
    @Inject TrainingApi api;

    public PublicationsPresenter() {
        App.INSTANCE.getAppComponent().inject(this);
    }

    @Override
    public void getPublications(String userId) {
        Call<PublicationResponse> publicationCall = api.getPublications(userId);
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

    @Override
    public void onBackPressed() {
        router.finishChain();
    }
}