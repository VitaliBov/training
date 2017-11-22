package com.bov.vitali.training.common.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;

public class BitmapUtils {

    public static Bitmap decodeSampledBitmapFromResource(Context context, Uri uri, int reqWidth, int reqHeight) {
        try {
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inJustDecodeBounds = true;
            BitmapFactory.decodeStream(context.getContentResolver().openInputStream(uri), null, options);
            options.inSampleSize = generateInSampleSize(options, reqWidth, reqHeight);
            options.inJustDecodeBounds = false;
            options.inPreferQualityOverSpeed = true;
            return BitmapFactory.decodeStream(context.getContentResolver().openInputStream(uri), null, options);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private static int generateInSampleSize(BitmapFactory.Options options, int reqWidth, int reqHeight) {
        int height = options.outHeight;
        int width = options.outWidth;
        int size = 1;
        if (height > reqHeight || width > reqWidth) {
            if (width > height) {
                size = Math.round((float) height / (float) reqHeight);
            } else {
                size = Math.round((float) width / (float) reqWidth);
            }
        }
        return size;
    }
}