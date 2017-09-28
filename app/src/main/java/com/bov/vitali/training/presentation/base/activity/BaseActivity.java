package com.bov.vitali.training.presentation.base.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.bov.vitali.training.TrainingApplication;

import org.androidannotations.annotations.EActivity;

@EActivity
public abstract class BaseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void onPause() {
        super.onPause();
        TrainingApplication.INSTANCE.getNavigatorHolder().removeNavigator();
    }
}