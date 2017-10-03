package com.bov.vitali.training.presentation.main.fragment;

import com.arellomobile.mvp.presenter.InjectPresenter;
import com.bov.vitali.training.R;
import com.bov.vitali.training.common.navigation.BackButtonListener;
import com.bov.vitali.training.presentation.base.fragment.BaseFragment;
import com.bov.vitali.training.presentation.main.presenter.MainPresenter;
import com.bov.vitali.training.presentation.main.view.MainView;

import org.androidannotations.annotations.EFragment;

@EFragment(R.layout.fragment_main)
public class MainFragment extends BaseFragment implements MainView, BackButtonListener {

    @InjectPresenter MainPresenter presenter;

    public boolean onBackPressed() {
        getActivity().finishAffinity();
        return true;
    }
}