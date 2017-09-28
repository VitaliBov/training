package com.bov.vitali.training.presentation.login.presenter;

import com.bov.vitali.training.TrainingApplication;
import com.bov.vitali.training.common.navigation.Screens;
import com.bov.vitali.training.presentation.base.presenter.BasePresenter;
import com.bov.vitali.training.presentation.login.view.SplashView;

public class SplashPresenter extends BasePresenter<SplashView> {

    public void navigateToLoginFragment() {
        TrainingApplication.INSTANCE.getRouter().navigateTo(Screens.LOGIN_FRAGMENT);
    }

    public void navigateToMainActivity() {
        TrainingApplication.INSTANCE.getRouter().navigateTo(Screens.MAIN_ACTIVITY);
    }
}