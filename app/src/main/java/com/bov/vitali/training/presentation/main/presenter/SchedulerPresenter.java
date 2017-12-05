package com.bov.vitali.training.presentation.main.presenter;

import com.arellomobile.mvp.InjectViewState;
import com.bov.vitali.training.presentation.base.presenter.BasePresenter;
import com.bov.vitali.training.presentation.main.view.SchedulerContract;

import ru.terrakok.cicerone.Router;

@InjectViewState
public class SchedulerPresenter extends BasePresenter<SchedulerContract.View> implements SchedulerContract.Presenter {
    private Router router;

    public SchedulerPresenter(Router router) {
        this.router = router;
    }

    @Override
    public void onBackPressed() {
        router.exit();
    }
}