package com.bov.vitali.training.presentation.main.bottom;

import com.arellomobile.mvp.InjectViewState;
import com.bov.vitali.training.App;
import com.bov.vitali.training.common.navigation.Screens;
import com.bov.vitali.training.presentation.base.presenter.BasePresenter;

@InjectViewState
public class BottomNavigationPresenter extends BasePresenter<BottomNavigationView> {

    void onTabUserClick() {
        getViewState().highlightTab(BottomNavigationView.USER_TAB_POSITION);
        App.INSTANCE.getRouter().replaceScreen(Screens.USER_FRAGMENT);
    }

    void onTabPublicationsClick() {
        getViewState().highlightTab(BottomNavigationView.PUBLICATION_TAB_POSITION);
        App.INSTANCE.getRouter().replaceScreen(Screens.PUBLICATIONS_FRAGMENT);
    }
}