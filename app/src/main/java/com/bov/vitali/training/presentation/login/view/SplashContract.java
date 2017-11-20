package com.bov.vitali.training.presentation.login.view;

import com.bov.vitali.training.presentation.base.view.BaseView;

public interface SplashContract {

    interface View extends BaseView {

    }

    interface Presenter {
        void navigateToNextScreen();

        void navigateToLoginFragment();

        void navigateToBottomNavigationActivity();

        void getRefreshToken();

        void onBackPressed();
    }
}