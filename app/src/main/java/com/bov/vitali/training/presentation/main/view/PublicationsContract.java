package com.bov.vitali.training.presentation.main.view;

import com.bov.vitali.training.data.model.Publication;
import com.bov.vitali.training.presentation.base.view.BaseView;

import java.util.List;

public interface PublicationsContract  {

    interface View extends BaseView {
        void setPublications(List<Publication> publications);

        void showResponseError();

        void hideResponseError();

        void showUpdatingSpinner();

        void hideUpdatingSpinner();
    }

    interface Presenter {
        void getPublications(String userId);

        void onBackPressed();
    }
}