package com.bov.vitali.training.presentation.base.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.arellomobile.mvp.MvpAppCompatFragment;
import com.bov.vitali.training.R;
import com.bov.vitali.training.common.utils.AndroidUtils;

public class BaseFragment extends MvpAppCompatFragment {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_base, container, false);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        AndroidUtils.hideKeyboard(getActivity());
    }
}