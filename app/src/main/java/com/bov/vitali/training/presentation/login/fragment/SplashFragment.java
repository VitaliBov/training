package com.bov.vitali.training.presentation.login.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.arellomobile.mvp.presenter.InjectPresenter;
import com.bov.vitali.training.R;
import com.bov.vitali.training.common.navigation.Screens;
import com.bov.vitali.training.presentation.base.fragment.BaseFragment;
import com.bov.vitali.training.presentation.login.presenter.SplashPresenter;
import com.bov.vitali.training.presentation.login.view.SplashView;

import org.androidannotations.annotations.EFragment;

@EFragment(R.layout.fragment_splash)
public class SplashFragment extends BaseFragment implements SplashView {

    @InjectPresenter SplashPresenter presenter;

    public static SplashFragment getInstance(int position) {
        SplashFragment fragment = new SplashFragment_();
        Bundle b = new Bundle();
        b.putInt(Screens.KEY, position);
        fragment.setArguments(b);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        delaySplashScreen();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_splash, container, false);
    }

    private void delaySplashScreen() {
        int splashDisplayLength = 1000;
        new Handler().postDelayed(new Runnable() {
            public void run() {
                presenter.navigateToLoginFragment();
            }
        }, splashDisplayLength);
    }
}