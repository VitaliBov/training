package com.bov.vitali.training.presentation.login.fragment;

import android.os.Bundle;
import android.os.Handler;

import com.arellomobile.mvp.presenter.InjectPresenter;
import com.bov.vitali.training.R;
import com.bov.vitali.training.presentation.base.fragment.BaseFragment;
import com.bov.vitali.training.presentation.login.presenter.SplashPresenter;
import com.bov.vitali.training.presentation.login.view.SplashContract;

import org.androidannotations.annotations.EFragment;

@EFragment(R.layout.fragment_splash)
public class SplashFragment extends BaseFragment<SplashPresenter, SplashContract.View> implements SplashContract.View {
    private static final int SPLASH_DISPLAY_LENGTH = 1000;
    @InjectPresenter SplashPresenter presenter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        delaySplashScreen();
    }

    private void delaySplashScreen() {
        new Handler().postDelayed(() -> presenter.navigateToNextScreen(), SPLASH_DISPLAY_LENGTH);
    }
}