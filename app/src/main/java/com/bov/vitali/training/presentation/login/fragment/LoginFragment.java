package com.bov.vitali.training.presentation.login.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.arellomobile.mvp.presenter.InjectPresenter;
import com.bov.vitali.training.R;
import com.bov.vitali.training.common.navigation.BackButtonListener;
import com.bov.vitali.training.common.navigation.Screens;
import com.bov.vitali.training.presentation.base.fragment.BaseFragment;
import com.bov.vitali.training.presentation.login.presenter.LoginPresenter;
import com.bov.vitali.training.presentation.login.view.LoginView;

import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EFragment;

import static com.bov.vitali.training.R.id.btnLogin;

@EFragment(R.layout.fragment_login)
public class LoginFragment extends BaseFragment implements LoginView, BackButtonListener {

    @InjectPresenter LoginPresenter presenter;

    public static LoginFragment getInstance(int position) {
        LoginFragment fragment = new LoginFragment_();
        Bundle b = new Bundle();
        b.putInt(Screens.KEY, position);
        fragment.setArguments(b);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_login, container, false);

        return view;
    }

    @Click({btnLogin})
    void onClick() {
        presenter.navigateToLoginWebViewFragment();
    }

    public boolean onBackPressed() {
        presenter.onBackPressed();
        return true;
    }
}