package com.bov.vitali.training.presentation.main.fragment;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AlertDialog;
import android.widget.ImageView;

import com.arellomobile.mvp.presenter.InjectPresenter;
import com.arellomobile.mvp.presenter.ProvidePresenter;
import com.bov.vitali.training.R;
import com.bov.vitali.training.common.utils.BitmapUtils;
import com.bov.vitali.training.presentation.base.fragment.BasePermissionsFragment;
import com.bov.vitali.training.presentation.main.presenter.ImagesPresenter;
import com.bov.vitali.training.presentation.main.view.ImagesContract;
import com.bov.vitali.training.presentation.navigation.BackButtonListener;
import com.bov.vitali.training.presentation.navigation.RouterProvider;

import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@EFragment(R.layout.fragment_images)
public class ImagesFragment extends BasePermissionsFragment<ImagesPresenter, ImagesContract.View>
        implements ImagesContract.View, BackButtonListener {
    @InjectPresenter ImagesPresenter presenter;
    @ViewById ImageView ivImage;
    private static final int GALLERY = 1;
    private static final int CAMERA = 2;
    private static final int IMAGES_COUNT = 12;
    List<Bitmap> images = new ArrayList<>();

    @ProvidePresenter
    ImagesPresenter provideImagesPresenter() {
        return new ImagesPresenter(((RouterProvider) getParentFragment()).getRouter());
    }

    @Click(R.id.btnAddImage)
    public void addImage() {
        showImageSelectDialog();
    }

    private void showImageSelectDialog() {
        String[] pictureDialogItems = {"Select photo from gallery", "Capture photo from camera", "Cancel"};
        AlertDialog.Builder dialog = new AlertDialog.Builder(getContext());
        dialog.setTitle("Select Action");
        dialog.setItems(pictureDialogItems,
                (dialog1, which) -> {
                    switch (which) {
                        case 0:
                            showRequestStoragePermission();
                            break;
                        case 1:
                            showRequestCameraPermission();
                            break;
                        case 2:
                            dialog1.dismiss();
                            break;
                        default:
                            break;
                    }
                });
        dialog.show();
    }

    public void choosePhotoFromGallery() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select File"), GALLERY);
    }

    private void takePhotoFromCamera() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, CAMERA);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            switch (requestCode) {
                case GALLERY:
                    onSelectFromGalleryResult(data);
                    break;
                case CAMERA:
                    onCaptureImageResult(data);
                    break;
                default:
                    break;
            }
        } else {
            return;
        }
    }

    private void onSelectFromGalleryResult(Intent data) {
        Bitmap bitmap = BitmapUtils.decodeSampledBitmapFromResource(getContext(), data.getData(), 1000, 1000);
        ivImage.setImageBitmap(bitmap);
    }

    private void onCaptureImageResult(Intent data) {
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
        ivImage.setImageBitmap(bitmap);
    }

    public String saveImage(Bitmap myBitmap) {

        return "";
    }

    @Click(R.id.btnSaveImagesToStorage)
    public void saveImagesToStorage() {
        for (int i = 0; i < IMAGES_COUNT; i++) {
            saveImage(images.get(i));
        }
        toast("Images saved");
    }

    @Override
    public void onPermissionsGranted(int requestCode) {
        switch (requestCode) {
            case REQUEST_STORAGE_PERMISSIONS:
                choosePhotoFromGallery();
                break;
            case REQUEST_CAMERA_PERMISSIONS:
                takePhotoFromCamera();
                break;
            default:
                break;
        }
    }

    @Override
    public boolean onBackPressed() {
        presenter.onBackPressed();
        return true;
    }

    private void showRequestStoragePermission() {
        ImagesFragment.super.requestAppPermissions(
                new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE},
                R.string.runtime_permissions_txt,
                REQUEST_STORAGE_PERMISSIONS);
    }

    private void showRequestCameraPermission() {
        ImagesFragment.super.requestAppPermissions(
                new String[]{Manifest.permission.CAMERA},
                R.string.runtime_permissions_txt,
                REQUEST_CAMERA_PERMISSIONS);
    }
}