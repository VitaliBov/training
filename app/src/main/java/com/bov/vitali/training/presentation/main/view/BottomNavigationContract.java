package com.bov.vitali.training.presentation.main.view;

import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy;
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType;
import com.bov.vitali.training.presentation.base.view.BaseActivityView;

public interface BottomNavigationContract  {

    @StateStrategyType(AddToEndSingleStrategy.class)
    interface View extends BaseActivityView {
        int USER_TAB_POSITION = 0;
        int PUBLICATION_TAB_POSITION = 1;
        int PAGINATION_TAB_POSITION = 2;

        void highlightTab(int position);

        void selectStartTab();

        void initBottomNavigationBar();

        void initContainers();
    }

    interface Presenter {
        void onTabUserClick();

        void onTabPublicationsClick();

        void onTabPaginationClick();
    }
}