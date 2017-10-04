package com.bov.vitali.training.presentation.main.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;

import com.arellomobile.mvp.presenter.InjectPresenter;
import com.bov.vitali.training.R;
import com.bov.vitali.training.common.navigation.BackButtonListener;
import com.bov.vitali.training.presentation.base.fragment.BaseFragment;
import com.bov.vitali.training.presentation.main.presenter.UserPresenter;
import com.bov.vitali.training.presentation.main.view.UserView;

import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.FragmentArg;

@EFragment(R.layout.fragment_user)
public class UserFragment extends BaseFragment implements UserView, BackButtonListener {
    @FragmentArg String actionBarTitle;
    @InjectPresenter UserPresenter presenter;

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setActionBarTitle();
    }

    @Override
    public boolean onBackPressed() {
        Fragment fragment = getChildFragmentManager().findFragmentById(R.id.user_container);
        if (fragment != null
                && fragment instanceof BackButtonListener
                && ((BackButtonListener) fragment).onBackPressed()) {
            return true;
        } else {
            getActivity().finishAffinity();
            return true;
        }
    }

    private void setActionBarTitle() {
        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle(actionBarTitle);
    }
}