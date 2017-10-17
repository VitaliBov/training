package com.bov.vitali.training.presentation.main.presenter;

import android.support.annotation.NonNull;

import com.arellomobile.mvp.InjectViewState;
import com.bov.vitali.training.App;
import com.bov.vitali.training.BuildConfig;
import com.bov.vitali.training.data.net.response.FilmResponse;
import com.bov.vitali.training.presentation.base.presenter.BasePresenter;
import com.bov.vitali.training.presentation.main.view.PaginationContract;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

@InjectViewState
public class PaginationPresenter extends BasePresenter<PaginationContract.View>
        implements PaginationContract.Presenter {
    private static final String LANGUAGE = "en_US";
    private static final int PAGE_START = 1;
    private int currentPage = PAGE_START;

    @Override
    public void loadMoreFilms() {
        Call<FilmResponse> paginationCall = App.getFilmsApi().getTopRatedFilms(BuildConfig.THE_MOVIE_DB_IP_KEY, LANGUAGE, currentPage);
        paginationCall.enqueue(new Callback<FilmResponse>() {
            @Override
            public void onResponse(@NonNull Call<FilmResponse> call, @NonNull Response<FilmResponse> response) {
                if (response.isSuccessful()) {
                    getViewState().renderFilms(response.body().getResults());
                    currentPage += 1;
                } else {
                    getViewState().showResponseError();
                }
            }

            @Override
            public void onFailure(@NonNull Call<FilmResponse> call, @NonNull Throwable t) {
                getViewState().showResponseError();
            }
        });
    }

    @Override
    public void resetAndRetrieve() {
        currentPage = PAGE_START;
        getViewState().resetView();
    }

//    @Override
//    public void getFilms(int pageIndex, boolean isFirst) {
//        Call<FilmResponse> paginationCall = App.getFilmsApi().getTopRatedFilms(BuildConfig.THE_MOVIE_DB_IP_KEY, LANGUAGE, pageIndex);
//        paginationCall.enqueue(new Callback<FilmResponse>() {
//            @Override
//            public void onResponse(
//                    @NonNull Call<FilmResponse> call, @NonNull Response<FilmResponse> response) {
//                if (response.isSuccessful()) {
//                    if (isFirst) {
//                        getViewState().setFirstPageFilms(response.body().getResults());
//                    } else {
//                        getViewState().setNextPageFilms(response.body().getResults());
//                    }
//                } else {
//                    getViewState().showResponseError();
//                }
//            }
//
//            @Override
//            public void onFailure(@NonNull Call<FilmResponse> call, @NonNull Throwable t) {
//                getViewState().showResponseError();
//            }
//        });
//    }
}