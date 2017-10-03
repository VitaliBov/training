package com.bov.vitali.training.presentation.main.fragment;

import com.arellomobile.mvp.presenter.InjectPresenter;
import com.bov.vitali.training.R;
import com.bov.vitali.training.presentation.base.fragment.BaseFragment;
import com.bov.vitali.training.presentation.main.presenter.ProfilePresenter;
import com.bov.vitali.training.presentation.main.view.ProfileView;

import org.androidannotations.annotations.EFragment;

@EFragment(R.layout.fragment_profile)
public class ProfileFragment extends BaseFragment implements ProfileView {

    @InjectPresenter ProfilePresenter presenter;

    @Override
    public boolean onBackPressed() {
        getActivity().finishAffinity();
        return true;
    }
}