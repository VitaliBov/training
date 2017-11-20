package com.bov.vitali.training.presentation.main.view;

import com.bov.vitali.training.presentation.base.view.BaseView;

public interface SchedulerContract {

    interface View extends BaseView {

    }

    interface Presenter {
        void onBackPressed();
    }
}