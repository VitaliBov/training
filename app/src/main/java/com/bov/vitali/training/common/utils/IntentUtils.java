package com.bov.vitali.training.common.utils;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.provider.MediaStore;

public class IntentUtils {

    private IntentUtils() {
    }

    public static Intent getGalleryIntent() {
        try {
            Intent intent = new Intent();
            intent.setType("image/*");
            intent.setAction(Intent.ACTION_PICK);
            return intent;
        } catch (ActivityNotFoundException e) {
            return null;
        }
    }

    public static Intent getCameraIntent() {
        return new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
    }
}