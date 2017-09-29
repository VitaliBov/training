package com.bov.vitali.training.presentation.login.presenter;

import com.arellomobile.mvp.InjectViewState;
import com.bov.vitali.training.TrainingApplication;
import com.bov.vitali.training.presentation.base.presenter.BasePresenter;
import com.bov.vitali.training.presentation.login.view.LoginWebView;

@InjectViewState
public class LoginWebViewPresenter extends BasePresenter<LoginWebView> {

    public void onBackPressed() {
        TrainingApplication.INSTANCE.getRouter().exit();
    }
}