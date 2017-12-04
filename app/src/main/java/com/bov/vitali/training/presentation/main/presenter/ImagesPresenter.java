package com.bov.vitali.training.presentation.main.presenter;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;

import com.arellomobile.mvp.InjectViewState;
import com.bov.vitali.training.App;
import com.bov.vitali.training.data.FileManager;
import com.bov.vitali.training.data.model.Image;
import com.bov.vitali.training.presentation.base.presenter.BasePresenter;
import com.bov.vitali.training.presentation.main.view.ImagesContract;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import javax.inject.Inject;

import ru.terrakok.cicerone.Router;

@InjectViewState
public class ImagesPresenter extends BasePresenter<ImagesContract.View> implements ImagesContract.Presenter {
    public static final int IMAGES_COUNT = 10;
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
    public void onGalleryResult(Intent data) {
        Image image = new Image();
        Uri uri = data.getData();
        image.setOriginalUri(uri);
        addImage(image);
    }

    @Override
    public void onCameraResult(Intent data) {
        Image image = new Image();
        Bitmap bitmap = (Bitmap) data.getExtras().get("data");
        String name = String.valueOf(System.currentTimeMillis());
        fileManager.savePhotoToInternalStorage(App.appContext(), bitmap, "Training", name);
        Uri uri = fileManager.getUri();
        image.setOriginalUri(uri);
        addImage(image);
    }

    private void addImage(Image image) {
        image.setSaved(true);
        images.addFirst(image);
        getViewState().setImages(images);
    }

    public void removeImages(List<Integer> positions) {
        Collections.sort(positions, (var1, var2) -> var2 - var1);
        while (!positions.isEmpty()) {
            if (positions.size() == 1) {
                removeImage(positions.get(0));
                positions.remove(0);
            } else {
                int count = 1;
                while (positions.size() > count && positions.get(count).equals(positions.get(count - 1) - 1)) {
                    ++count;
                }
                if (count == 1) {
                    removeImage(positions.get(0));
                } else {
                    removeRange(positions.get(count - 1), count);
                }
                for (int i = 0; i < count; ++i) {
                    positions.remove(0);
                }
            }
        }
    }

    private void removeImage(int position) {
        images.remove(position);
        getViewState().setImages(images);
    }

    private void removeRange(int positionStart, int itemCount) {
        for (int i = 0; i < itemCount; ++i) {
            images.remove(positionStart);
        }
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