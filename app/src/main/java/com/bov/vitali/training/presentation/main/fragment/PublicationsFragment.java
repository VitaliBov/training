package com.bov.vitali.training.presentation.main.fragment;

import com.arellomobile.mvp.presenter.InjectPresenter;
import com.bov.vitali.training.R;
import com.bov.vitali.training.presentation.base.fragment.BaseFragment;
import com.bov.vitali.training.presentation.main.presenter.PublicationsPresenter;
import com.bov.vitali.training.presentation.main.view.PublicationsView;

import org.androidannotations.annotations.EFragment;

@EFragment(R.layout.fragment_publications)
public class PublicationsFragment extends BaseFragment implements PublicationsView {

    @InjectPresenter PublicationsPresenter presenter;

    @Override
    public boolean onBackPressed() {
        getActivity().finishAffinity();
        return true;
    }
}