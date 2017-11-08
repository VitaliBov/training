package com.bov.vitali.training.presentation.main.presenter;

import com.arellomobile.mvp.InjectViewState;
import com.bov.vitali.training.App;
import com.bov.vitali.training.common.navigation.Screens;
import com.bov.vitali.training.presentation.base.presenter.BaseActivityPresenter;
import com.bov.vitali.training.presentation.main.view.BottomNavigationContract;

@InjectViewState
public class BottomNavigationPresenter extends BaseActivityPresenter<BottomNavigationContract.View>
        implements BottomNavigationContract.Presenter {

    @Override
    public void onTabUserClick() {
        getViewState().highlightTab(BottomNavigationContract.View.USER_TAB_POSITION);
        App.INSTANCE.getRouter().replaceScreen(Screens.USER_FRAGMENT);
    }

    @Override
    public void onTabPublicationsClick() {
        getViewState().highlightTab(BottomNavigationContract.View.PUBLICATION_TAB_POSITION);
        App.INSTANCE.getRouter().replaceScreen(Screens.PUBLICATIONS_FRAGMENT);
    }

    @Override
    public void onTabPaginationClick() {
        getViewState().highlightTab(BottomNavigationContract.View.PAGINATION_TAB_POSITION);
        App.INSTANCE.getRouter().replaceScreen(Screens.PAGINATION_FRAGMENT);
    }

    @Override
    public void onTabContainerClick() {
        getViewState().highlightTab(BottomNavigationContract.View.CONTAINER_TAB_POSITION);
        App.INSTANCE.getRouter().replaceScreen(Screens.TAB_CONTAINER_FRAGMENT);
    }
}