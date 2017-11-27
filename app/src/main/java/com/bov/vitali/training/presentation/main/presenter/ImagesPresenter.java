package com.bov.vitali.training.presentation.main.presenter;

import android.content.Intent;
import android.graphics.Bitmap;

import com.arellomobile.mvp.InjectViewState;
import com.bov.vitali.training.App;
import com.bov.vitali.training.common.utils.BitmapUtils;
import com.bov.vitali.training.presentation.base.presenter.BasePresenter;
import com.bov.vitali.training.presentation.main.view.ImagesContract;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import ru.terrakok.cicerone.Router;

@InjectViewState
public class ImagesPresenter extends BasePresenter<ImagesContract.View> implements ImagesContract.Presenter {
    public static final int IMAGES_COUNT = 10;
    private Router router;
    private LinkedList<Bitmap> images = new LinkedList<>();

    public ImagesPresenter(Router router) {
        this.router = router;
    }

    @Override
    public List<Bitmap> getImages() {
        return images;
    }

    @Override
    public void onGalleryResult(Intent data) {
        Bitmap bitmap = BitmapUtils.decodeSampledBitmapFromResource(App.appContext(), data.getData(), 500, 500);
        addBitmap(bitmap);
    }

    @Override
    public void onCameraResult(Intent data) {
        Bitmap bitmap = (Bitmap) data.getExtras().get("data");
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 90, bytes);
        File destination = new File(App.appContext().getCacheDir(), System.currentTimeMillis() + ".jpg");
        FileOutputStream fo;
        try {
            destination.createNewFile();
            fo = new FileOutputStream(destination);
            fo.write(bytes.toByteArray());
            fo.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        addBitmap(bitmap);
    }

    private void addBitmap(Bitmap bitmap) {
        images.addFirst(bitmap);
        getViewState().setImages(images);
    }

    public void removeBitmaps(List<Integer> positions) {
        Collections.sort(positions, (var1, var2) -> var2 - var1);
        while (!positions.isEmpty()) {
            if (positions.size() == 1) {
                removeBitmap(positions.get(0));
                positions.remove(0);
            } else {
                int count = 1;
                while (positions.size() > count && positions.get(count).equals(positions.get(count - 1) - 1)) {
                    ++count;
                }
                if (count == 1) {
                    removeBitmap(positions.get(0));
                } else {
                    removeRange(positions.get(count - 1), count);
                }
                for (int i = 0; i < count; ++i) {
                    positions.remove(0);
                }
            }
        }
    }

    private void removeBitmap(int position) {
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
        for (int i = 0; i < IMAGES_COUNT; i++) {
            saveImage(images.get(i));
        }
    }

    private void saveImage(Bitmap bitmap) {

    }

    @Override
    public void onBackPressed() {
        router.exit();
    }
}