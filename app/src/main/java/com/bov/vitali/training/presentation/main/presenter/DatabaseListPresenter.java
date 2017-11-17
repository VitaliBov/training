package com.bov.vitali.training.presentation.main.presenter;

import com.arellomobile.mvp.InjectViewState;
import com.bov.vitali.training.presentation.base.presenter.BasePresenter;
import com.bov.vitali.training.presentation.main.view.DatabaseListContract;

@InjectViewState
public class DatabaseListPresenter extends BasePresenter<DatabaseListContract.View>
        implements DatabaseListContract.Presenter {

}