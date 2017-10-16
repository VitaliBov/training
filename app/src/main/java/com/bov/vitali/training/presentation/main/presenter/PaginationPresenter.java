package com.bov.vitali.training.presentation.main.presenter;

import android.support.annotation.NonNull;

import com.arellomobile.mvp.InjectViewState;
import com.bov.vitali.training.App;
import com.bov.vitali.training.data.net.response.FilmResponse;
import com.bov.vitali.training.presentation.base.presenter.BasePresenter;
import com.bov.vitali.training.presentation.main.view.PaginationContract;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

@InjectViewState
public class PaginationPresenter extends BasePresenter<PaginationContract.View>
        implements PaginationContract.Presenter {

    @Override
    public void getFilms(String apiKey, String language, int pageIndex, boolean isFirst) {
        Call<FilmResponse> paginationCall = App.getFilmsApi().getTopRatedFilms(apiKey, language, pageIndex);
        paginationCall.enqueue(new Callback<FilmResponse>() {
            @Override
            public void onResponse(@NonNull Call<FilmResponse> call, @NonNull Response<FilmResponse> response) {
                if (response.isSuccessful()) {
                    if (isFirst) {
                        getViewState().setFirstPageFilms(response.body().getResults());
                    } else {
                        getViewState().setNextPageFilms(response.body().getResults());
                    }
                } else {
                    getViewState().showResponseError();
                }
            }

            @Override
            public void onFailure(@NonNull Call<FilmResponse> call, Throwable t) {
                getViewState().showResponseError();
            }
        });
    }
}