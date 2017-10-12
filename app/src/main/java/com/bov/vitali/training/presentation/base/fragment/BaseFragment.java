package com.bov.vitali.training.presentation.base.fragment;

import android.os.Bundle;

import com.arellomobile.mvp.MvpAppCompatFragment;
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.bov.vitali.training.common.navigation.BackButtonListener;
import com.bov.vitali.training.common.utils.AndroidUtils;
import com.bov.vitali.training.presentation.base.presenter.BasePresenter;
import com.bov.vitali.training.presentation.base.view.BaseView;

public class BaseFragment extends MvpAppCompatFragment implements BaseView, BackButtonListener {

    @InjectPresenter BasePresenter presenter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        AndroidUtils.hideKeyboard(getActivity());
    }

    @Override
    public boolean onBackPressed() {
        presenter.onBackPressed();
        return false;
    }
}