package com.bov.vitali.training.presentation.main.fragment;

import android.graphics.Bitmap;

import com.arellomobile.mvp.presenter.InjectPresenter;
import com.arellomobile.mvp.presenter.ProvidePresenter;
import com.bov.vitali.training.R;
import com.bov.vitali.training.presentation.base.fragment.BaseFragment;
import com.bov.vitali.training.presentation.main.presenter.ImageChangePresenter;
import com.bov.vitali.training.presentation.main.view.ImageChangeContract;
import com.bov.vitali.training.presentation.navigation.BackButtonListener;
import com.bov.vitali.training.presentation.navigation.RouterProvider;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.FragmentArg;

@EFragment(R.layout.fragment_image_change)
public class ImageChangeFragment extends BaseFragment<ImageChangePresenter, ImageChangeContract.View>
        implements ImageChangeContract.View, BackButtonListener {
    @InjectPresenter ImageChangePresenter presenter;
    @FragmentArg Bitmap bitmap;

    @ProvidePresenter
    ImageChangePresenter provideImageChangePresenter() {
        return new ImageChangePresenter(((RouterProvider) getParentFragment()).getRouter());
    }

    @AfterViews
    public void afterViews() {
        toast(Integer.toString(bitmap.getGenerationId()));
    }

    @Override
    public boolean onBackPressed() {
        presenter.onBackPressed();
        return true;
    }
}