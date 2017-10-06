package com.bov.vitali.training.presentation.base.activity;

import com.arellomobile.mvp.MvpAppCompatActivity;
import com.bov.vitali.training.App;

import org.androidannotations.annotations.EActivity;

@EActivity
public abstract class BaseActivity extends MvpAppCompatActivity {

    @Override
    protected void onPause() {
        super.onPause();
        App.INSTANCE.getNavigatorHolder().removeNavigator();
    }
}