package com.bov.vitali.training.presentation.login.view;

import com.bov.vitali.training.presentation.base.view.BaseView;

public interface LoginWebViewContract {

    interface View extends BaseView {

    }

    interface Presenter {
        void getToken(String code);

        void navigateToBottomNavigationActivity();
    }
}