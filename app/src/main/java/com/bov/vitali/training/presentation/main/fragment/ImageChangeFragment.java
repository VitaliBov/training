package com.bov.vitali.training.presentation.main.fragment;

import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageView;

import com.arellomobile.mvp.presenter.InjectPresenter;
import com.arellomobile.mvp.presenter.ProvidePresenter;
import com.bov.vitali.training.App;
import com.bov.vitali.training.R;
import com.bov.vitali.training.data.FileManager;
import com.bov.vitali.training.common.utils.BitmapUtils;
import com.bov.vitali.training.data.model.Image;
import com.bov.vitali.training.presentation.base.fragment.BaseFragment;
import com.bov.vitali.training.presentation.main.presenter.ImageChangePresenter;
import com.bov.vitali.training.presentation.main.view.ImageChangeContract;
import com.bov.vitali.training.presentation.navigation.BackButtonListener;
import com.bov.vitali.training.presentation.navigation.RouterProvider;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.FragmentArg;
import org.androidannotations.annotations.ViewById;

import java.util.Objects;

import javax.inject.Inject;

@EFragment(R.layout.fragment_image_change)
public class ImageChangeFragment extends BaseFragment<ImageChangePresenter, ImageChangeContract.View>
        implements ImageChangeContract.View, BackButtonListener {
    @InjectPresenter ImageChangePresenter presenter;
    @Inject FileManager fileManager;
    @FragmentArg Image image;
    @ViewById ImageView ivTextToBitmap;
    @ViewById EditText etTextToBitmap;
    private Bitmap bitmap;
    private Bitmap oldBitmap;
    private String oldText;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        App.instance.getAppComponent().inject(this);
        super.onCreate(savedInstanceState);
    }

    @ProvidePresenter
    ImageChangePresenter provideImageChangePresenter() {
        return new ImageChangePresenter(((RouterProvider) getParentFragment()).getRouter());
    }

    @AfterViews
    public void afterViews() {
        scaleBitmap();
        onBind();
    }

    @Click(R.id.btnAddTextToBitmap)
    public void onClick() {
        String text = etTextToBitmap.getText().toString();
        image.setText(text);
        bitmap = drawText(text);
        Glide.with(getContext())
                .asBitmap()
                .load(bitmap)
                .into(new SimpleTarget<Bitmap>() {
                    @Override
                    public void onResourceReady(Bitmap resource, Transition<? super Bitmap> transition) {
                        ivTextToBitmap.setImageBitmap(resource);
                    }
                });
    }

    private void scaleBitmap() {
        bitmap = BitmapUtils.decodeSampledBitmapFromResource(getContext(), image.getOriginalUri());
        bitmap = BitmapUtils.scaleBitmap(getContext(), bitmap);
    }

    private void onBind() {
        checkText();
        Glide.with(getContext())
                .asBitmap()
                .load(oldBitmap)
                .into(new SimpleTarget<Bitmap>() {
                    @Override
                    public void onResourceReady(Bitmap resource, Transition<? super Bitmap> transition) {
                        ivTextToBitmap.setImageBitmap(resource);
                    }
                });
    }

    private void checkText() {
        oldText = image.getText();
        oldBitmap = bitmap;
        if (image.getText() != null) {
            oldBitmap = drawText(oldText);
            etTextToBitmap.setText(oldText);
        }
    }

    private Bitmap drawText(String text) {
        return BitmapUtils.drawTextToBitmap(getContext(), bitmap, text);
    }

    private void saveNewBitmap() {
        Bitmap newBitmap = bitmap;
        String fileName = String.valueOf(System.currentTimeMillis());
        fileManager.saveToInternalStorage(getContext(), newBitmap, "Training", fileName);
        Uri uri = fileManager.getUri();
        image.setChangedUri(uri);
        image.setSaved(false);
    }

    @Override
    public boolean onBackPressed() {
        if (isTextValid()) {
            saveNewBitmap();
        }
        presenter.onBackPressed();
        return true;
    }

    private boolean isTextValid() {
        return !Objects.equals(oldText, image.getText()) & !Objects.equals(image.getText(), "");
    }
}