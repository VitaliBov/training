package com.bov.vitali.training.presentation.main.view;

import android.content.Intent;

import com.bov.vitali.training.data.model.Image;
import com.bov.vitali.training.presentation.base.view.BaseView;

import java.util.List;

public interface ImagesContract {

    interface View extends BaseView {
        void showRequestStoragePermission();

        void showRequestCameraPermission();

        void setImages(List<Image> bitmaps);
    }

    interface Presenter {
        void onGalleryResult(Intent data);

        void onCameraResult(Intent data);

        List<Image> getImages();

        void saveImagesToStorage();

        void onBackPressed();
    }
}