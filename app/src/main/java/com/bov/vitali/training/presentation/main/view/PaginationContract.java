package com.bov.vitali.training.presentation.main.view;

import com.bov.vitali.training.data.model.Film;
import com.bov.vitali.training.presentation.base.view.BaseView;

import java.util.List;

public interface PaginationContract {

    interface View extends BaseView {
        void setFirstPageFilms(List<Film> results);

        void setNextPageFilms(List<Film> results);

        void showResponseError();
    }

    interface Presenter {
        void getFilms(String apiKey, String language, int pageIndex, boolean isFirst);
    }
}