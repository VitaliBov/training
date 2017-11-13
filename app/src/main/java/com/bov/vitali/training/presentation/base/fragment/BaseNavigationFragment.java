package com.bov.vitali.training.presentation.base.fragment;

import com.arellomobile.mvp.MvpPresenter;
import com.arellomobile.mvp.MvpView;
import com.bov.vitali.training.presentation.navigation.RouterProvider;

import ru.terrakok.cicerone.Router;

public abstract class BaseNavigationFragment<P extends MvpPresenter<V>, V extends MvpView> extends BaseFragment {

    protected Router getRouter() {
        return ((RouterProvider)getParentFragment()).getRouter();
    }
}