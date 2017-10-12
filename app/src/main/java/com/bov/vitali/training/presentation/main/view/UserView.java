package com.bov.vitali.training.presentation.main.view;

import com.bov.vitali.training.data.model.User;
import com.bov.vitali.training.presentation.base.view.BaseView;

public interface UserView extends BaseView {

    void setUser(User user);

    void showResponseError();

    void hideResponseError();
}