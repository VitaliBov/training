package com.bov.vitali.training.presentation.main.presenter;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Environment;

import com.arellomobile.mvp.InjectViewState;
import com.bov.vitali.training.App;
import com.bov.vitali.training.common.utils.BitmapUtils;
import com.bov.vitali.training.presentation.base.presenter.BasePresenter;
import com.bov.vitali.training.presentation.main.view.ImagesContract;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import ru.terrakok.cicerone.Router;

@InjectViewState
public class ImagesPresenter extends BasePresenter<ImagesContract.View> implements ImagesContract.Presenter {
    private static final int IMAGES_COUNT = 12;
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
        File destination = new File(Environment.getExternalStorageDirectory(), System.currentTimeMillis() + ".jpg");
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