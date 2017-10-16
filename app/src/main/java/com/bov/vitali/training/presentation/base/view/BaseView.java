package com.bov.vitali.training.presentation.base.view;

import android.support.annotation.StringRes;

import com.arellomobile.mvp.MvpView;

public interface BaseView extends MvpView {

    void showMessage(String message);

    void showMessage(@StringRes int stringRes);

    void toast(@StringRes int stringRes);

    void toast(String message);
}