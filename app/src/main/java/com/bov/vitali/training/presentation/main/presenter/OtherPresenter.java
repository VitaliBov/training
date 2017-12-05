package com.bov.vitali.training.presentation.main.presenter;

import com.arellomobile.mvp.InjectViewState;
import com.bov.vitali.training.App;
import com.bov.vitali.training.presentation.base.presenter.BasePresenter;
import com.bov.vitali.training.presentation.main.view.OtherContract;

import javax.inject.Inject;

import ru.terrakok.cicerone.Router;

@InjectViewState
public class OtherPresenter extends BasePresenter<OtherContract.View> implements OtherContract .Presenter {
    @Inject Router router;

    public OtherPresenter() {
        App.instance.getAppComponent().inject(this);
    }

    @Override
    public void onBackPressed() {
        router.finishChain();
    }
}