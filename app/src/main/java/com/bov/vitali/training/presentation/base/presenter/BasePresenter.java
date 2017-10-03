package com.bov.vitali.training.presentation.base.presenter;

import com.arellomobile.mvp.MvpPresenter;
import com.arellomobile.mvp.MvpView;
import com.bov.vitali.training.TrainingApplication;

public class BasePresenter<View extends MvpView> extends MvpPresenter<View> {

    public void onBackPressed() {
        TrainingApplication.INSTANCE.getRouter().exit();
    }
}