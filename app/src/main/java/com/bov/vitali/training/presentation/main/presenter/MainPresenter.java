package com.bov.vitali.training.presentation.main.presenter;

import com.arellomobile.mvp.InjectViewState;
import com.bov.vitali.training.App;
import com.bov.vitali.training.presentation.main.view.MainContract;
import com.bov.vitali.training.presentation.navigation.Screens;
import com.bov.vitali.training.presentation.base.presenter.BaseActivityPresenter;

import javax.inject.Inject;

import ru.terrakok.cicerone.Router;

@InjectViewState
public class MainPresenter extends BaseActivityPresenter<MainContract.View> implements MainContract.Presenter {
    @Inject Router router;

    public MainPresenter() {
        App.INSTANCE.getAppComponent().inject(this);
    }

    @Override
    public void onTabUserClick() {
        getViewState().highlightTab(MainContract.View.USER_TAB_POSITION);
        router.replaceScreen(Screens.USER_FRAGMENT);
    }

    @Override
    public void onTabPublicationsClick() {
        getViewState().highlightTab(MainContract.View.PUBLICATION_TAB_POSITION);
        router.replaceScreen(Screens.PUBLICATIONS_FRAGMENT);
    }

    @Override
    public void onTabPaginationClick() {
        getViewState().highlightTab(MainContract.View.PAGINATION_TAB_POSITION);
        router.replaceScreen(Screens.PAGINATION_FRAGMENT);
    }

    @Override
    public void onTabOtherClick() {
        getViewState().highlightTab(MainContract.View.OTHER_TAB_POSITION);
        router.replaceScreen(Screens.OTHER_FRAGMENT);
    }

    public void onBackPressed() {
        router.exit();
    }
}