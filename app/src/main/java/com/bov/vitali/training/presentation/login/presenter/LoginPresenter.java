package com.bov.vitali.training.presentation.login.presenter;

import com.arellomobile.mvp.InjectViewState;
import com.bov.vitali.training.TrainingApplication;
import com.bov.vitali.training.common.navigation.Screens;
import com.bov.vitali.training.presentation.base.presenter.BasePresenter;
import com.bov.vitali.training.presentation.login.view.LoginView;

@InjectViewState
public class LoginPresenter extends BasePresenter<LoginView> {

    public void navigateToLoginWebViewFragment() {
        TrainingApplication.INSTANCE.getRouter().navigateTo(Screens.LOGIN_WEB_VIEW_FRAGMENT);
    }

    public void onBackPressed() {
        TrainingApplication.INSTANCE.getRouter().finishChain();
    }
}