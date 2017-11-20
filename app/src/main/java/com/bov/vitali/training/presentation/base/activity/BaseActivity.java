package com.bov.vitali.training.presentation.base.activity;

import com.arellomobile.mvp.MvpAppCompatActivity;
import com.arellomobile.mvp.MvpPresenter;
import com.arellomobile.mvp.MvpView;

import org.androidannotations.annotations.EActivity;

@EActivity
public abstract class BaseActivity<P extends MvpPresenter<V>, V extends MvpView> extends MvpAppCompatActivity {

}