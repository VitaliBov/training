package com.bov.vitali.training.presentation.login.presenter;

import com.bov.vitali.training.TrainingApplication;
import com.bov.vitali.training.common.navigation.Screens;
import com.bov.vitali.training.presentation.base.presenter.BasePresenter;
import com.bov.vitali.training.presentation.login.view.LoginView;

public class LoginPresenter extends BasePresenter<LoginView> {

    public void navigateToMainActivity() {
        TrainingApplication.INSTANCE.getRouter().navigateTo(Screens.MAIN_ACTIVITY);
    }
}