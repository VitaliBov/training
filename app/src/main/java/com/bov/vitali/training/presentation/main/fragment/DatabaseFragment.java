package com.bov.vitali.training.presentation.main.fragment;

import com.arellomobile.mvp.presenter.InjectPresenter;
import com.bov.vitali.training.R;
import com.bov.vitali.training.presentation.base.fragment.BaseFragment;
import com.bov.vitali.training.presentation.main.presenter.DatabasePresenter;
import com.bov.vitali.training.presentation.main.view.DatabaseContract;
import com.bov.vitali.training.presentation.navigation.BackButtonListener;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EFragment;

@EFragment(R.layout.fragment_database)
public class DatabaseFragment extends BaseFragment<DatabasePresenter, DatabaseContract.View> implements DatabaseContract.View, BackButtonListener {
    @InjectPresenter DatabasePresenter presenter;

    @AfterViews
    public void afterViews() {

    }
}