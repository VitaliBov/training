package com.bov.vitali.training.presentation.splash.presenter;

import com.bov.vitali.training.TrainingApplication;
import com.bov.vitali.training.common.navigation.Screens;
import com.bov.vitali.training.presentation.base.presenter.BasePresenter;
import com.bov.vitali.training.presentation.splash.view.SplashView;

public class SplashPresenter extends BasePresenter<SplashView> {

    public void navigateNext() {
        TrainingApplication.INSTANCE.getRouter().navigateTo(Screens.LOGIN_ACTIVITY);
    }
}