package com.bov.vitali.training.presentation.login.fragment;

import com.arellomobile.mvp.presenter.InjectPresenter;
import com.bov.vitali.training.R;
import com.bov.vitali.training.presentation.base.fragment.BaseFragment;
import com.bov.vitali.training.presentation.login.presenter.LoginPresenter;
import com.bov.vitali.training.presentation.login.view.LoginContract;
import com.bov.vitali.training.presentation.navigation.BackButtonListener;

import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EFragment;

import static com.bov.vitali.training.R.id.btnLogin;

@EFragment(R.layout.fragment_login)
public class LoginFragment extends BaseFragment<LoginPresenter, LoginContract.View>
        implements LoginContract.View, BackButtonListener {
    @InjectPresenter LoginPresenter presenter;

    @Click({btnLogin})
    void onClick() {
        presenter.navigateToLoginWebViewFragment();
    }

    @Override
    public boolean onBackPressed() {
        presenter.onBackPressed();
        return false;
    }
}