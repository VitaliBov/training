package com.bov.vitali.training.presentation.main.presenter;

import android.support.annotation.NonNull;

import com.arellomobile.mvp.InjectViewState;
import com.bov.vitali.training.App;
import com.bov.vitali.training.BuildConfig;
import com.bov.vitali.training.data.net.response.FilmResponse;
import com.bov.vitali.training.presentation.base.presenter.BasePresenter;
import com.bov.vitali.training.presentation.main.view.PaginationContract;

import javax.inject.Inject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import ru.terrakok.cicerone.Router;

@InjectViewState
public class PaginationPresenter extends BasePresenter<PaginationContract.View>
        implements PaginationContract.Presenter {
    private static final String LANGUAGE = "en_US";
    private static final int PAGE_START = 1;
    private int currentPage = PAGE_START;
    @Inject Router router;

    public PaginationPresenter() {
        App.INSTANCE.getAppComponent().inject(this);
    }

    @Override
    public void loadMoreFilms() {
        Call<FilmResponse> paginationCall = App.getFilmsApi().getTopRatedFilms(
                BuildConfig.THE_MOVIE_DB_IP_KEY, LANGUAGE, currentPage);
        paginationCall.enqueue(new Callback<FilmResponse>() {
            @Override
            public void onResponse(
                    @NonNull Call<FilmResponse> call, @NonNull Response<FilmResponse> response) {
                if (response.isSuccessful()) {
                    getViewState().renderFilms(response.body().getResults());
                    currentPage++;
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
        getViewState().clearView();
        loadMoreFilms();
    }

    @Override
    public void onBackPressed() {
        router.finishChain();
    }
}