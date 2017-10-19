package com.bov.vitali.training.presentation.main.view;

import com.bov.vitali.training.data.model.Film;
import com.bov.vitali.training.presentation.base.view.BaseView;

import java.util.List;

public interface PaginationContract {

    interface View extends BaseView {
        void renderFilms(List<Film> films);

        void clearView();

        void showResponseError();

        void hideResponseError();
    }

    interface Presenter {
        void resetAndRetrieve();

        void loadMoreFilms();
    }
}