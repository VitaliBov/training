package com.bov.vitali.training.common.utils;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.util.DisplayMetrics;

import java.io.FileNotFoundException;

public class BitmapUtils {

    private BitmapUtils() {
    }

    public static Bitmap decodeSampledBitmapFromResource(@NonNull Context context, @NonNull Uri uri) {
        try {
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inJustDecodeBounds = true;
            BitmapFactory.decodeStream(context.getContentResolver().openInputStream(uri), null, options);
            options.inJustDecodeBounds = false;
            options.inPreferQualityOverSpeed = true;
            return BitmapFactory.decodeStream(context.getContentResolver().openInputStream(uri), null, options);
        } catch (FileNotFoundException e) {
            return null;
        }
    }

    public static Bitmap decodeSampledBitmapFromResource(@NonNull Context context, @NonNull Uri uri, int reqWidth, int reqHeight) {
        try {
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inJustDecodeBounds = true;
            BitmapFactory.decodeStream(context.getContentResolver().openInputStream(uri), null, options);
            options.inSampleSize = generateInSampleSize(options, reqWidth, reqHeight);
            options.inJustDecodeBounds = false;
            options.inPreferQualityOverSpeed = true;
            return BitmapFactory.decodeStream(context.getContentResolver().openInputStream(uri), null, options);
        } catch (FileNotFoundException e) {
            return null;
        }
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

    public static Bitmap scaleBitmap(@NonNull Context context, @NonNull Bitmap bitmap) {
        DisplayMetrics metrics = context.getResources().getDisplayMetrics();
        int displayHeight = metrics.heightPixels;
        int displayWidth = metrics.widthPixels;
        float ratioHeight = displayHeight / (float) bitmap.getHeight();
        float ratioWidth = displayWidth / (float) bitmap.getWidth();
        if (ratioHeight > ratioWidth) {
            return scaleToFitHeight(bitmap, displayHeight);
        } else {
            return scaleToFitWidth(bitmap, displayWidth);
        }
    }

    private static Bitmap scaleToFitHeight(Bitmap bitmap, int height) {
        float factor = height / (float) bitmap.getHeight();
        return Bitmap.createScaledBitmap(bitmap, (int) (bitmap.getWidth() * factor), height, true);
    }

    private static Bitmap scaleToFitWidth(Bitmap bitmap, int width) {
        float factor = width / (float) bitmap.getWidth();
        return Bitmap.createScaledBitmap(bitmap, width, (int) (bitmap.getHeight() * factor), true);
    }

    public static Bitmap drawTextToBitmap(@NonNull Context context, @NonNull Bitmap bitmap, String text) {
        Resources resources = context.getResources();
        float scale = resources.getDisplayMetrics().density;
        Bitmap.Config bitmapConfig = bitmap.getConfig();
        if (bitmapConfig == null) {
            bitmapConfig = Bitmap.Config.ARGB_8888;
        }
        bitmap = bitmap.copy(bitmapConfig, true);
        Canvas canvas = new Canvas(bitmap);
        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setColor(Color.WHITE);
        paint.setTextSize((int) (80 * scale));
        Rect bounds = new Rect();
        paint.getTextBounds(text, 0, text.length(), bounds);
        int x = (bitmap.getWidth() - bounds.width()) - PixelUtils.dp2px(context, 15);
        int y = (bitmap.getHeight() - PixelUtils.dp2px(context, 65 ));
        canvas.drawText(text, x, y, paint);
        return bitmap;
    }
}