package com.bov.vitali.training.presentation.main.presenter;

import com.arellomobile.mvp.InjectViewState;
import com.bov.vitali.training.App;
import com.bov.vitali.training.presentation.main.view.MainContract;
import com.bov.vitali.training.presentation.navigation.Screens;
import com.bov.vitali.training.presentation.base.presenter.BaseActivityPresenter;

@InjectViewState
public class MainPresenter extends BaseActivityPresenter<MainContract.View>
        implements MainContract.Presenter {

    @Override
    public void onTabUserClick() {
        getViewState().highlightTab(MainContract.View.USER_TAB_POSITION);
        App.INSTANCE.getRouter().replaceScreen(Screens.USER_FRAGMENT);
    }

    @Override
    public void onTabPublicationsClick() {
        getViewState().highlightTab(MainContract.View.PUBLICATION_TAB_POSITION);
        App.INSTANCE.getRouter().replaceScreen(Screens.PUBLICATIONS_FRAGMENT);
    }

    @Override
    public void onTabPaginationClick() {
        getViewState().highlightTab(MainContract.View.PAGINATION_TAB_POSITION);
        App.INSTANCE.getRouter().replaceScreen(Screens.PAGINATION_FRAGMENT);
    }

    @Override
    public void onTabOtherClick() {
        getViewState().highlightTab(MainContract.View.OTHER_TAB_POSITION);
        App.INSTANCE.getRouter().replaceScreen(Screens.OTHER_FRAGMENT);
    }
}