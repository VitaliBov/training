package com.bov.vitali.training.presentation.main.view;

import com.bov.vitali.training.data.model.Image;
import com.bov.vitali.training.presentation.base.view.BaseView;

import java.util.List;

public interface ImagesContract {

    interface View extends BaseView {
        void setImages(List<Image> bitmaps);
    }

    interface Presenter {
        List<Image> getImages();

        void addImage(Image image);

        void saveImagesToStorage();

        void onBackPressed();
    }
}