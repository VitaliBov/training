package com.bov.vitali.training.presentation.login.presenter;

import com.arellomobile.mvp.InjectViewState;
import com.bov.vitali.training.App;
import com.bov.vitali.training.presentation.navigation.Screens;
import com.bov.vitali.training.presentation.base.presenter.BasePresenter;
import com.bov.vitali.training.presentation.login.view.LoginContract;

@InjectViewState
public class LoginPresenter extends BasePresenter<LoginContract.View> implements LoginContract.Presenter {

    @Override
    public void navigateToLoginWebViewFragment() {
        App.INSTANCE.getRouter().navigateTo(Screens.LOGIN_WEB_VIEW_FRAGMENT);
    }

    @Override
    public void onBackPressed() {
        App.INSTANCE.getRouter().finishChain();
    }
}