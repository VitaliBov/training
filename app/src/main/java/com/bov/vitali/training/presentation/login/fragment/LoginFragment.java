package com.bov.vitali.training.presentation.login.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.arellomobile.mvp.presenter.InjectPresenter;
import com.bov.vitali.training.R;
import com.bov.vitali.training.common.navigation.Screens;
import com.bov.vitali.training.presentation.base.fragment.BaseFragment;
import com.bov.vitali.training.presentation.login.presenter.LoginPresenter;
import com.bov.vitali.training.presentation.login.view.LoginView;

import org.androidannotations.annotations.EFragment;

@EFragment(R.layout.fragment_login)
public class LoginFragment extends BaseFragment implements LoginView {

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
        return inflater.inflate(R.layout.fragment_login, container, false);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
}