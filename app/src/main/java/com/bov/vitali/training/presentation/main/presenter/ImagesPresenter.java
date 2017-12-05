package com.bov.vitali.training.presentation.main.presenter;

import android.net.Uri;

import com.arellomobile.mvp.InjectViewState;
import com.bov.vitali.training.App;
import com.bov.vitali.training.common.utils.FileManager;
import com.bov.vitali.training.data.model.Image;
import com.bov.vitali.training.presentation.base.presenter.BasePresenter;
import com.bov.vitali.training.presentation.main.view.ImagesContract;

import java.util.LinkedList;
import java.util.List;

import javax.inject.Inject;

import ru.terrakok.cicerone.Router;

@InjectViewState
public class ImagesPresenter extends BasePresenter<ImagesContract.View> implements ImagesContract.Presenter {
    private static final String IMAGES_FOLDER = "Training";
    @Inject FileManager fileManager;
    private Router router;
    private LinkedList<Image> images = new LinkedList<>();

    public ImagesPresenter(Router router) {
        App.instance.getAppComponent().inject(this);
        this.router = router;
    }

    @Override
    public List<Image> getImages() {
        return images;
    }

    @Override
    public void addImage(Image image) {
        image.setSaved(true);
        images.addFirst(image);
        getViewState().setImages(images);
    }

    public void removeImages(List<Image> images) {
        while (!images.isEmpty()) {
            if (images.size() == 1) {
                removeImage(images.get(0));
                images.remove(0);
            } else {
                int count = 1;
                while (images.size() > count && images.get(count).equals(images.get(count - 1))) {
                    ++count;
                }
                if (count == 1) {
                    removeImage(images.get(0));
                } else {
                    removeRange(images);
                }
                for (int i = 0; i < count; ++i) {
                    images.remove(0);
                }
            }
        }
    }

    private void removeImage(Image image) {
        images.remove(image);
        getViewState().setImages(images);
    }

    private void removeRange(List<Image> selectedImages) {
        images.remove(selectedImages);
        getViewState().setImages(images);
    }

    @Override
    public void saveImagesToStorage() {
        if (images.size() != 0) {
            for (int i = 0; i < images.size(); i++) {
                saveImage(images.get(i));
            }
        }
    }

    private void saveImage(Image image) {
        if (image.getChangedUri() != null & !image.isSaved()) {
            String fileName = String.valueOf(System.currentTimeMillis());
            Uri uri = image.getChangedUri();
            fileManager.saveToInternalStorage(App.appContext(), uri, IMAGES_FOLDER, fileName);
        }
    }

    @Override
    public void onBackPressed() {
        router.exit();
    }
}