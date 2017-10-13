package com.bov.vitali.training.presentation.base.activity;

import com.arellomobile.mvp.MvpPresenter;
import com.arellomobile.mvp.MvpView;
import com.bov.vitali.training.App;

import org.androidannotations.annotations.EActivity;

@EActivity
public abstract class BaseNavigationActivity<P extends MvpPresenter<V>, V extends MvpView> extends BaseActivity {

    @Override
    protected void onPause() {
        super.onPause();
        App.INSTANCE.getNavigatorHolder().removeNavigator();
    }
}