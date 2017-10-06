package com.bov.vitali.training.presentation.login.presenter;

import com.arellomobile.mvp.InjectViewState;
import com.bov.vitali.training.App;
import com.bov.vitali.training.common.navigation.Screens;
import com.bov.vitali.training.presentation.base.presenter.BasePresenter;
import com.bov.vitali.training.presentation.login.view.LoginView;

@InjectViewState
public class LoginPresenter extends BasePresenter<LoginView> {

    public void navigateToLoginWebViewFragment() {
        App.INSTANCE.getRouter().navigateTo(Screens.LOGIN_WEB_VIEW_FRAGMENT);
    }

    @Override
    public void onBackPressed() {
        App.INSTANCE.getRouter().finishChain();
    }
}