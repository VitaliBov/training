package com.bov.vitali.training.presentation.main.bottom;

import com.arellomobile.mvp.MvpView;
import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy;
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType;

@StateStrategyType(AddToEndSingleStrategy.class)
interface BottomNavigationView extends MvpView {
    int USER_TAB_POSITION = 0;
    int PUBLICATION_TAB_POSITION = 1;

    void highlightTab(int position);

    void selectStartTab();
}