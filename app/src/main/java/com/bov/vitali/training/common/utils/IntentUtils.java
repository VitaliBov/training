package com.bov.vitali.training.common.utils;

import android.content.Intent;
import android.provider.MediaStore;

public class IntentUtils {

    public static Intent getGalleryIntent() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_PICK);
        return intent;
    }

    public static Intent getCameraIntent() {
        return new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
    }
}