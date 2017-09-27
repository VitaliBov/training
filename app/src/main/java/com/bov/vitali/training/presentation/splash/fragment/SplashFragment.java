package com.bov.vitali.training.presentation.splash.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.arellomobile.mvp.presenter.InjectPresenter;
import com.bov.vitali.training.R;
import com.bov.vitali.training.common.navigation.Screens;
import com.bov.vitali.training.presentation.base.fragment.BaseFragment;
import com.bov.vitali.training.presentation.splash.presenter.SplashPresenter;
import com.bov.vitali.training.presentation.splash.view.SplashView;

import org.androidannotations.annotations.EFragment;

@EFragment(R.layout.fragment_login)
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
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return super.onCreateView(inflater, container, savedInstanceState);
    }
}