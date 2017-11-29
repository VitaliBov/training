package com.bov.vitali.training.presentation.main.presenter;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;

import com.arellomobile.mvp.InjectViewState;
import com.bov.vitali.training.App;
import com.bov.vitali.training.common.utils.BitmapUtils;
import com.bov.vitali.training.data.model.Image;
import com.bov.vitali.training.presentation.base.presenter.BasePresenter;
import com.bov.vitali.training.presentation.main.view.ImagesContract;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import ru.terrakok.cicerone.Router;

@InjectViewState
public class ImagesPresenter extends BasePresenter<ImagesContract.View> implements ImagesContract.Presenter {
    public static final int IMAGES_COUNT = 10;
    private Router router;
    private LinkedList<Image> images = new LinkedList<>();

    public ImagesPresenter(Router router) {
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
        Uri uri = Uri.fromFile(destination);
        image.setOriginalUri(uri);
        addImage(image);
    }

    private void addImage(Image image) {
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
        if (image.getChangedUri() != null) {
            try {
                String path = Environment.getExternalStorageDirectory().toString();
                long filename = System.currentTimeMillis();
                File destination = new File(path, "/DCIM/Training/");
                destination.mkdirs();
                File file = new File(destination, filename + ".jpg");
                OutputStream fOut;
                fOut = new FileOutputStream(file);
                Bitmap bitmap = BitmapUtils.decodeSampledBitmapFromResource(App.appContext(), image.getChangedUri());
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fOut);
                fOut.close();
                MediaStore.Images.Media.insertImage(App.appContext().getContentResolver(), file.getAbsolutePath(), file.getName(), file.getName());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onBackPressed() {
        router.exit();
    }
}