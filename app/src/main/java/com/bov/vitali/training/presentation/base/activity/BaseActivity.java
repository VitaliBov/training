package com.bov.vitali.training.presentation.base.activity;

import com.arellomobile.mvp.MvpAppCompatActivity;
import com.bov.vitali.training.TrainingApplication;

import org.androidannotations.annotations.EActivity;

@EActivity
public abstract class BaseActivity extends MvpAppCompatActivity {

    @Override
    protected void onPause() {
        super.onPause();
        TrainingApplication.INSTANCE.getNavigatorHolder().removeNavigator();
    }
}