package com.bov.vitali.training.presentation.base.fragment;

import android.os.Bundle;

import com.arellomobile.mvp.MvpAppCompatFragment;
import com.bov.vitali.training.common.utils.AndroidUtils;

public class BaseFragment extends MvpAppCompatFragment {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public void onDetach() {
        super.onDetach();
        AndroidUtils.hideKeyboard(getActivity());
    }
}