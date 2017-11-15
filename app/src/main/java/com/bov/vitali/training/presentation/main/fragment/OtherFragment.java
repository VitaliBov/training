package com.bov.vitali.training.presentation.main.fragment;

import com.arellomobile.mvp.presenter.InjectPresenter;
import com.bov.vitali.training.R;
import com.bov.vitali.training.presentation.base.fragment.BaseNavigationFragment;
import com.bov.vitali.training.presentation.main.presenter.OtherPresenter;
import com.bov.vitali.training.presentation.main.view.OtherContract;
import com.bov.vitali.training.presentation.navigation.BackButtonListener;
import com.bov.vitali.training.presentation.navigation.Screens;

import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EFragment;

@EFragment(R.layout.fragment_other)
public class OtherFragment extends BaseNavigationFragment<OtherPresenter, OtherContract.View> implements OtherContract.View, BackButtonListener {
    @InjectPresenter OtherPresenter presenter;

    @Click(R.id.btnNavigateToScheduler)
    public void navigateToSchedulerFragment() {
        getRouter().navigateTo(Screens.SCHEDULER_FRAGMENT);
    }

    @Click(R.id.btnNavigateToDatabase)
    public void navigateToDatabaseFragment() {
        getRouter().navigateTo(Screens.DATABASE_FRAGMENT);
    }

    @Click(R.id.btnNavigateToDatabaseList)
    public void navigateToDatabaseListFragment() {
        getRouter().navigateTo(Screens.DATABASE_LIST_FRAGMENT);
    }

    @Click(R.id.btnNavigateToDatabaseLiveData)
    public void navigateToDatabaseLiveDataFragment() {
        getRouter().navigateTo(Screens.DATABASE_LIVE_DATA_FRAGMENT);
    }
}