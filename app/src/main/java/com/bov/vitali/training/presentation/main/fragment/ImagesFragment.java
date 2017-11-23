package com.bov.vitali.training.presentation.main.fragment;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.ImageView;

import com.arellomobile.mvp.presenter.InjectPresenter;
import com.arellomobile.mvp.presenter.ProvidePresenter;
import com.bov.vitali.training.R;
import com.bov.vitali.training.common.utils.IntentUtils;
import com.bov.vitali.training.presentation.base.fragment.BasePermissionsFragment;
import com.bov.vitali.training.presentation.main.adapter.ImagesAdapter;
import com.bov.vitali.training.presentation.main.presenter.ImagesPresenter;
import com.bov.vitali.training.presentation.main.view.ImagesContract;
import com.bov.vitali.training.presentation.navigation.BackButtonListener;
import com.bov.vitali.training.presentation.navigation.RouterProvider;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;

import java.util.List;

@EFragment(R.layout.fragment_images)
public class ImagesFragment extends BasePermissionsFragment<ImagesPresenter, ImagesContract.View>
        implements ImagesContract.View, BackButtonListener {
    private static final int REQUEST_CODE_GALLERY = 1;
    private static final int REQUEST_CODE_CAMERA = 2;
    @InjectPresenter ImagesPresenter presenter;
    @ViewById ImageView ivImage;
    @ViewById RecyclerView rvImages;
    private ImagesAdapter adapter;

    @ProvidePresenter
    ImagesPresenter provideImagesPresenter() {
        return new ImagesPresenter(((RouterProvider) getParentFragment()).getRouter());
    }

    @AfterViews
    public void afterViews() {
        setupRecyclerView();
    }

    private void setupRecyclerView() {
        GridLayoutManager layoutManager = new GridLayoutManager(getActivity(), 3);
        rvImages.setHasFixedSize(true);
        rvImages.setLayoutManager(layoutManager);
    }

    private void checkAdapter() {
        if (adapter == null) {
            initAdapter();
        } else {
            rvImages.setAdapter(adapter);
        }
    }

    private void initAdapter() {
        adapter = new ImagesAdapter(presenter.getImages());
        rvImages.setAdapter(adapter);
    }

    public void setImages(List<Bitmap> bitmaps) {
        checkAdapter();
        adapter.setBitmaps(bitmaps);
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

    @Override
    public void showRequestStoragePermission() {
        ImagesFragment.super.requestAppPermissions(
                new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE},
                R.string.runtime_permissions_txt,
                REQUEST_STORAGE_PERMISSIONS);
    }

    @Override
    public void showRequestCameraPermission() {
        ImagesFragment.super.requestAppPermissions(
                new String[]{Manifest.permission.CAMERA},
                R.string.runtime_permissions_txt,
                REQUEST_CAMERA_PERMISSIONS);
    }

    @Override
    public void onPermissionsGranted(int requestCode) {
        switch (requestCode) {
            case REQUEST_STORAGE_PERMISSIONS:
                chooseImageFromGallery();
                break;
            case REQUEST_CAMERA_PERMISSIONS:
                takePhotoFromCamera();
                break;
            default:
                break;
        }
    }

    public void chooseImageFromGallery() {
        startActivityForResult(Intent.createChooser(IntentUtils.getGalleryIntent(), "Select File"), REQUEST_CODE_GALLERY);
    }

    private void takePhotoFromCamera() {
        startActivityForResult(IntentUtils.getCameraIntent(), REQUEST_CODE_CAMERA);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            switch (requestCode) {
                case REQUEST_CODE_GALLERY:
                    presenter.onGalleryResult(data);
                    break;
                case REQUEST_CODE_CAMERA:
                    presenter.onCameraResult(data);
                    break;
                default:
                    break;
            }
        } else {
            return;
        }
    }

    @Click(R.id.btnSaveImagesToStorage)
    public void saveImagesToStorage() {
        //presenter.saveImagesToStorage();
    }

    @Override
    public boolean onBackPressed() {
        presenter.onBackPressed();
        return true;
    }
}