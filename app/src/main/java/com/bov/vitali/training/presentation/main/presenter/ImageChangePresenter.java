package com.bov.vitali.training.presentation.main.presenter;

import com.arellomobile.mvp.InjectViewState;
import com.bov.vitali.training.presentation.base.presenter.BasePresenter;
import com.bov.vitali.training.presentation.main.view.ImageChangeContract;

import ru.terrakok.cicerone.Router;

@InjectViewState
public class ImageChangePresenter extends BasePresenter<ImageChangeContract.View> implements ImageChangeContract.Presenter {
    private Router router;

    public ImageChangePresenter(Router router) {
        this.router = router;
    }

    @Override
    public void onBackPressed() {
        router.exit();
    }
}