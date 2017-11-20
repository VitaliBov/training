package com.bov.vitali.training.presentation.main.view;

import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy;
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType;
import com.bov.vitali.training.data.model.User;
import com.bov.vitali.training.presentation.base.view.BaseView;

public interface UserContract {

    @StateStrategyType(AddToEndSingleStrategy.class)
    interface View extends BaseView {
        void setUser(User user);

        void showResponseError();

        void hideResponseError();
    }

    interface Presenter {
        void getUser();

        void onBackPressed();
    }
}