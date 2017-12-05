package com.bov.vitali.training.presentation.login.presenter;

import com.arellomobile.mvp.InjectViewState;
import com.bov.vitali.training.App;
import com.bov.vitali.training.presentation.navigation.Screens;
import com.bov.vitali.training.presentation.base.presenter.BasePresenter;
import com.bov.vitali.training.presentation.login.view.LoginContract;

import javax.inject.Inject;

import ru.terrakok.cicerone.Router;

@InjectViewState
public class LoginPresenter extends BasePresenter<LoginContract.View> implements LoginContract.Presenter {
    @Inject Router router;

    public LoginPresenter() {
        App.instance.getAppComponent().inject(this);
    }

    @Override
    public void navigateToLoginWebViewFragment() {
        router.navigateTo(Screens.LOGIN_WEB_VIEW_FRAGMENT);
    }

    @Override
    public void onBackPressed() {
        router.finishChain();
    }
}