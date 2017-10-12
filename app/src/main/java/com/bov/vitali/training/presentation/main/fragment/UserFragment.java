package com.bov.vitali.training.presentation.main.fragment;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.arellomobile.mvp.presenter.InjectPresenter;
import com.bov.vitali.training.App;
import com.bov.vitali.training.R;
import com.bov.vitali.training.common.navigation.BackButtonListener;
import com.bov.vitali.training.common.utils.AndroidUtils;
import com.bov.vitali.training.data.model.User;
import com.bov.vitali.training.presentation.base.fragment.BaseFragment;
import com.bov.vitali.training.presentation.main.presenter.UserPresenter;
import com.bov.vitali.training.presentation.main.view.UserView;
import com.squareup.picasso.Picasso;

import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;

@EFragment(R.layout.fragment_user)
public class UserFragment extends BaseFragment implements UserView, BackButtonListener {
    @InjectPresenter UserPresenter presenter;
    @ViewById ImageView ivUserAvatar;
    @ViewById TextView tvUserName;
    @ViewById TextView tvUserUsername;
    @ViewById TextView tvUserUrl;
    private User user;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter.getUser();
        setRetainInstance(true);
    }

    @Override
    public void setUser(User user) {
        this.user = user;
        onBind();
    }

    private void onBind() {
        if (!user.getName().isEmpty()) {
            tvUserName.setText(user.getName());
        } else {
            tvUserName.setText(R.string.absence_name);
        }
        if (!user.getUsername().isEmpty()) {
            tvUserUsername.setText(user.getUsername());
        } else {
            tvUserUsername.setText(R.string.absence_username);
        }
        if (!user.getUrl().isEmpty()) {
            tvUserUrl.setText(user.getUrl());
        } else {
            tvUserUrl.setText(R.string.absence_url);
        }
        if (!user.getImageUrl().isEmpty()) {
            Picasso.with(getActivity())
                    .load(user.getImageUrl())
                    .into(ivUserAvatar);
        }
    }

    @Override
    public void showResponseError() {
        AndroidUtils.toast(App.appContext(), App.appContext().getResources().getString(R.string.error));


    }
}