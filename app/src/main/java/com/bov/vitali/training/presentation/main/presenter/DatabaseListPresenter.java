package com.bov.vitali.training.presentation.main.presenter;

import com.arellomobile.mvp.InjectViewState;
import com.bov.vitali.training.presentation.base.presenter.BasePresenter;
import com.bov.vitali.training.presentation.main.view.DatabaseListContract;

import ru.terrakok.cicerone.Router;

@InjectViewState
public class DatabaseListPresenter extends BasePresenter<DatabaseListContract.View>
        implements DatabaseListContract.Presenter {
    private Router router;

    public DatabaseListPresenter(Router router) {
        this.router = router;
    }

    @Override
    public void onBackPressed() {
        router.exit();
    }
}