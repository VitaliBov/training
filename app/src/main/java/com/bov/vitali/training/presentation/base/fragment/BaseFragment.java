package com.bov.vitali.training.presentation.base.fragment;

import android.support.annotation.StringRes;
import android.view.View;
import android.widget.Toast;

import com.arellomobile.mvp.MvpAppCompatFragment;
import com.arellomobile.mvp.MvpPresenter;
import com.arellomobile.mvp.MvpView;
import com.bov.vitali.training.App;
import com.bov.vitali.training.common.navigation.BackButtonListener;
import com.bov.vitali.training.common.utils.AndroidUtils;
import com.bov.vitali.training.presentation.base.common.CoordinatorLayoutContaining;
import com.bov.vitali.training.presentation.base.view.BaseView;

import static com.bov.vitali.training.common.utils.AndroidUtils.snackbar;

public abstract class BaseFragment<P extends MvpPresenter<V>, V extends MvpView> extends MvpAppCompatFragment implements BaseView, BackButtonListener {

    @Override
    public void onDetach() {
        super.onDetach();
        AndroidUtils.hideKeyboard(getActivity());
    }

    @Override
    public boolean onBackPressed() {
        App.INSTANCE.getRouter().exit();
        return false;
    }

    public View getActivityContentView() {
        if (getActivity() instanceof CoordinatorLayoutContaining) {
            return ((CoordinatorLayoutContaining) getActivity()).getCoordinatorLayout();
        } else {
            return getActivity().findViewById(android.R.id.content);
        }
    }

    @Override
    public void showMessage(String message) {
        if (message != null && !message.isEmpty()) {
            snackbar(getActivityContentView(), message);
        }
    }

    @Override
    public void showMessage(@StringRes int stringRes) {
        snackbar(getActivityContentView(), stringRes);
    }

    @Override
    public void toast(@StringRes int stringRes) {
        Toast.makeText(getActivity(), stringRes, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void toast(String message) {
        Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
    }
}