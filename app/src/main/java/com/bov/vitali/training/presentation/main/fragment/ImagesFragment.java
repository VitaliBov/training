package com.bov.vitali.training.presentation.main.fragment;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.view.ActionMode;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;

import com.arellomobile.mvp.presenter.InjectPresenter;
import com.arellomobile.mvp.presenter.ProvidePresenter;
import com.bov.vitali.training.App;
import com.bov.vitali.training.R;
import com.bov.vitali.training.common.utils.FileManager;
import com.bov.vitali.training.common.utils.IntentUtils;
import com.bov.vitali.training.common.utils.PixelUtils;
import com.bov.vitali.training.data.model.Image;
import com.bov.vitali.training.presentation.base.fragment.BasePermissionsFragment;
import com.bov.vitali.training.presentation.main.adapter.ImagesAdapter;
import com.bov.vitali.training.presentation.main.common.GridSpacingItemDecoration;
import com.bov.vitali.training.presentation.main.presenter.ImagesPresenter;
import com.bov.vitali.training.presentation.main.view.ImagesContract;
import com.bov.vitali.training.presentation.navigation.BackButtonListener;
import com.bov.vitali.training.presentation.navigation.RouterProvider;
import com.bov.vitali.training.presentation.navigation.Screens;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;

import java.util.List;

import javax.inject.Inject;

@EFragment(R.layout.fragment_images)
public class ImagesFragment extends BasePermissionsFragment<ImagesPresenter, ImagesContract.View>
        implements ImagesContract.View, BackButtonListener, ImagesAdapter.ImagesClickListener {
    private static final int REQUEST_CODE_GALLERY = 1;
    private static final int REQUEST_CODE_CAMERA = 2;
    private static final String PHOTO_DIRECTORY = "Training";
    @InjectPresenter ImagesPresenter presenter;
    @Inject FileManager fileManager;
    @ViewById ImageView ivImage;
    @ViewById RecyclerView rvImages;
    private ImagesAdapter adapter;
    private ActionModeCallback actionModeCallback = new ActionModeCallback();
    private ActionMode actionMode;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        App.instance.getAppComponent().inject(this);
        super.onCreate(savedInstanceState);
    }

    @ProvidePresenter
    ImagesPresenter provideImagesPresenter() {
        return new ImagesPresenter(((RouterProvider) getParentFragment()).getRouter());
    }

    @AfterViews
    public void afterViews() {
        setupRecyclerView();
        checkAdapter(this);
    }

    private void setupRecyclerView() {
        int spanCount;
        if (getContext().getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
            spanCount = 3;
        } else {
            spanCount = 4;
        }
        int spacing = PixelUtils.dp2px(getContext(), 2);
        GridLayoutManager layoutManager = new GridLayoutManager(getActivity(), spanCount);
        rvImages.setHasFixedSize(true);
        rvImages.addItemDecoration(new GridSpacingItemDecoration(spanCount, spacing));
        rvImages.setLayoutManager(layoutManager);
    }

    private void checkAdapter(ImagesAdapter.ImagesClickListener listener) {
        if (adapter == null) {
            initAdapter(listener);
        } else {
            rvImages.setAdapter(adapter);
        }
    }

    private void initAdapter(ImagesAdapter.ImagesClickListener listener) {
        adapter = new ImagesAdapter(getContext(), presenter.getImages(), listener);
        rvImages.setAdapter(adapter);
    }

    @Override
    public void setImages(List<Image> images) {
        checkAdapter(this);
        adapter.setImages(images);
    }

    private void showImageSelectDialog() {
        String[] pictureDialogItems = {getString(R.string.images_select_from_gallery),
                getString(R.string.images_select_from_camera), getString(R.string.images_cancel)};
        AlertDialog.Builder dialog = new AlertDialog.Builder(getContext());
        dialog.setTitle(R.string.images_dialog_title);
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
        startActivityForResult(Intent.createChooser(
                IntentUtils.getGalleryIntent(), getString(R.string.images_gallery_choose_title)), REQUEST_CODE_GALLERY);
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
                    onGalleryResult(data);
                    break;
                case REQUEST_CODE_CAMERA:
                    onCameraResult(data);
                    break;
                default:
                    break;
            }
        }
    }

    private void onGalleryResult(Intent data) {
        Image image = new Image();
        Uri uri = data.getData();
        image.setOriginalUri(uri);
        presenter.addImage(image);
    }

    private void onCameraResult(Intent data) {
        Image image = new Image();
        Bitmap bitmap = (Bitmap) data.getExtras().get("data");
        String name = String.valueOf(System.currentTimeMillis());
        Uri uri = fileManager.savePhotoToInternalStorage(App.appContext(), bitmap, PHOTO_DIRECTORY, name);
        image.setOriginalUri(uri);
        presenter.addImage(image);
    }

    @Override
    public void onImageClick(Image image) {
        if (actionMode != null) {
            toggleSelection(image);
        } else {
            navigateToImageChangeFragment(image);
        }
    }

    @Override
    public void onLongImageClick(Image image) {
        if (actionMode == null) {
            actionMode = ((AppCompatActivity) getActivity()).startSupportActionMode(actionModeCallback);
        }
        toggleSelection(image);
    }

    @Override
    public void onAddImageClick() {
        showImageSelectDialog();
    }

    private void toggleSelection(Image image) {
        adapter.toggleSelection(image);
        int count = adapter.getSelectedItemCount();
        if (count == 0) {
            actionMode.finish();
        } else {
            actionMode.setTitle(String.valueOf(count));
            actionMode.invalidate();
        }
    }

    @Click(R.id.btnSaveImagesToStorage)
    public void saveImagesToStorage() {
        presenter.saveImagesToStorage();
    }

    private void navigateToImageChangeFragment(Image image) {
        getRouter().navigateTo(Screens.IMAGE_CHANGE_FRAGMENT, image);
    }

    @Override
    public boolean onBackPressed() {
        presenter.onBackPressed();
        return true;
    }

    private class ActionModeCallback implements ActionMode.Callback {

        @Override
        public boolean onCreateActionMode(ActionMode mode, Menu menu) {
            mode.getMenuInflater().inflate(R.menu.images_menu, menu);
            return true;
        }

        @Override
        public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
            return false;
        }

        @Override
        public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
            switch (item.getItemId()) {
                case R.id.menu_remove:
                    presenter.removeImages(adapter.getSelectedItems());
                    mode.finish();
                    return true;
                default:
                    return false;
            }
        }

        @Override
        public void onDestroyActionMode(ActionMode mode) {
            adapter.clearSelection();
            actionMode = null;
        }
    }
}