package com.bov.vitali.training.presentation.main.fragment;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.arellomobile.mvp.presenter.InjectPresenter;
import com.bov.vitali.training.R;
import com.bov.vitali.training.presentation.navigation.BackButtonListener;
import com.bov.vitali.training.data.model.User;
import com.bov.vitali.training.presentation.base.fragment.BaseFragment;
import com.bov.vitali.training.presentation.main.presenter.UserPresenter;
import com.bov.vitali.training.presentation.main.view.UserContract;
import com.squareup.picasso.Picasso;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;

@EFragment(R.layout.fragment_user)
public class UserFragment extends BaseFragment<UserPresenter, UserContract.View> implements UserContract.View, BackButtonListener {
    @InjectPresenter UserPresenter presenter;
    @ViewById ViewGroup contentUser;
    @ViewById ViewGroup emptyView;
    @ViewById ImageView ivUserAvatar;
    @ViewById TextView tvUserName;
    @ViewById TextView tvUserUsername;
    @ViewById TextView tvUserUrl;
    @ViewById TextView emptyViewText;
    private User user;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter.getUser();
        setRetainInstance(true);
    }

    @AfterViews
    public void afterViews() {
        hideResponseError();
    }

    @Override
    public void setUser(User user) {
        this.user = user;
        onBind();
        if (user == null) showResponseError();
    }

    private void onBind() {
        if (!user.getName().isEmpty()) {
            tvUserName.setText(user.getName());
        } else {
            tvUserName.setText(R.string.user_absence_name);
        }
        if (!user.getUsername().isEmpty()) {
            tvUserUsername.setText(user.getUsername());
        } else {
            tvUserUsername.setText(R.string.user_absence_username);
        }
        if (!user.getUrl().isEmpty()) {
            tvUserUrl.setText(user.getUrl());
        } else {
            tvUserUrl.setText(R.string.user_absence_url);
        }
        if (!user.getImageUrl().isEmpty()) {
            Picasso.with(getActivity())
                    .load(user.getImageUrl())
                    .into(ivUserAvatar);
        }
    }

    @Override
    public void showResponseError() {
        emptyView.setVisibility(View.VISIBLE);
        contentUser.setVisibility(View.GONE);
        emptyViewText.setText(getResources().getString(R.string.user_error_response));
    }

    @Override
    public void hideResponseError() {
        emptyView.setVisibility(View.GONE);
        contentUser.setVisibility(View.VISIBLE);
    }
}