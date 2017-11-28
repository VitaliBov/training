package com.bov.vitali.training.presentation.main.fragment;

import android.graphics.Bitmap;
import android.net.Uri;
import android.widget.ImageView;

import com.arellomobile.mvp.presenter.InjectPresenter;
import com.arellomobile.mvp.presenter.ProvidePresenter;
import com.bov.vitali.training.R;
import com.bov.vitali.training.presentation.base.fragment.BaseFragment;
import com.bov.vitali.training.presentation.main.presenter.ImageChangePresenter;
import com.bov.vitali.training.presentation.main.view.ImageChangeContract;
import com.bov.vitali.training.presentation.navigation.BackButtonListener;
import com.bov.vitali.training.presentation.navigation.RouterProvider;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.FragmentArg;
import org.androidannotations.annotations.ViewById;

@EFragment(R.layout.fragment_image_change)
public class ImageChangeFragment extends BaseFragment<ImageChangePresenter, ImageChangeContract.View>
        implements ImageChangeContract.View, BackButtonListener {
    @InjectPresenter ImageChangePresenter presenter;
    @FragmentArg Uri uri;
    @ViewById ImageView ivTextToBitmap;

    @ProvidePresenter
    ImageChangePresenter provideImageChangePresenter() {
        return new ImageChangePresenter(((RouterProvider) getParentFragment()).getRouter());
    }

    @AfterViews
    public void afterViews() {
        onBind();
    }

    public void onBind() {
        Glide.with(getContext())
                .asBitmap()
                .load(uri)
                .into(new SimpleTarget<Bitmap>() {
                    @Override
                    public void onResourceReady(Bitmap resource, Transition<? super Bitmap> transition) {
                        ivTextToBitmap.setImageBitmap(resource);
                    }
                });
    }

    @Override
    public boolean onBackPressed() {
        presenter.onBackPressed();
        return true;
    }
}