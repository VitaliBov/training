package com.bov.vitali.training.presentation.main.view;

import com.bov.vitali.training.data.database.entity.User;
import com.bov.vitali.training.presentation.base.view.BaseView;

import java.util.List;

public interface DatabaseListContract {

    interface View extends BaseView {
        void setUsers(List<User> users);

        void showResponseError();

        void hideResponseError();

        void showUpdatingSpinner();

        void hideUpdatingSpinner();
    }

    interface Presenter {
        void getUsers();
    }
}