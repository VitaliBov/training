package com.bov.vitali.training.presentation.main.bottom;

import com.arellomobile.mvp.InjectViewState;
import com.bov.vitali.training.common.navigation.Screens;
import com.bov.vitali.training.presentation.base.presenter.BasePresenter;

import ru.terrakok.cicerone.Router;

@InjectViewState
public class BottomNavigationPresenter extends BasePresenter<BottomNavigationView> {
    private Router router;

    BottomNavigationPresenter(Router router) {
        this.router = router;
    }

    void onTabUserClick() {
        getViewState().highlightTab(BottomNavigationView.USER_TAB_POSITION);
        router.replaceScreen(Screens.USER_FRAGMENT);
    }

    void onTabPublicationsClick() {
        getViewState().highlightTab(BottomNavigationView.PUBLICATION_TAB_POSITION);
        router.replaceScreen(Screens.PUBLICATIONS_FRAGMENT);
    }
}