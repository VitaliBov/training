package com.bov.vitali.training.data;

import android.content.Context;
import android.content.ContextWrapper;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Environment;

import com.bov.vitali.training.common.utils.BitmapUtils;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

public class FileManager {
    private Uri uri;

    public void saveToCache(Context context, Bitmap bitmap, String name) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 90, bytes);
        File file = new File(context.getCacheDir(), name + ".jpg");
        OutputStream stream;
        try {
            file.createNewFile();
            stream = new FileOutputStream(file);
            stream.write(bytes.toByteArray());
            stream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        uri = Uri.fromFile(file);
    }

    public void saveToExternalStorage(Context context, Uri uri, String folder, String name) {
        String path = Environment.getExternalStorageDirectory().toString();
        File directory = new File(path + folder);
        directory.mkdirs();
        File file = new File(directory, name + ".jpg");
        Bitmap bitmap = BitmapUtils.decodeSampledBitmapFromResource(context, uri);
        OutputStream stream;
        try {
            stream = new FileOutputStream(file);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
            stream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        this.uri = Uri.fromFile(file);
    }

    public void saveToInternalStorage(Context context, Uri uri, String folder, String name) {
        ContextWrapper wrapper = new ContextWrapper(context);
        File directory = wrapper.getDir(folder, Context.MODE_PRIVATE);
        File file = new File(directory, name + ".jpg");
        Bitmap bitmap = BitmapUtils.decodeSampledBitmapFromResource(context, uri);
        OutputStream stream;
        try {
            stream = new FileOutputStream(file);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
            stream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        this.uri = Uri.fromFile(file);
    }

    public void saveToInternalStorage(Context context, Bitmap bitmap, String folder, String name) {
        ContextWrapper wrapper = new ContextWrapper(context);
        File directory = wrapper.getDir(folder, Context.MODE_PRIVATE);
        File file = new File(directory, name + ".jpg");
        OutputStream stream;
        try {
            stream = new FileOutputStream(file);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
            stream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        this.uri = Uri.fromFile(file);
    }

    public void savePhotoToInternalStorage(Context context, Bitmap bitmap, String folder, String name) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        ContextWrapper wrapper = new ContextWrapper(context);
        File directory = wrapper.getDir(folder, Context.MODE_PRIVATE);
        File file = new File(directory, name + ".jpg");
        OutputStream stream;
        try {
            stream = new FileOutputStream(file);
            stream.write(bytes.toByteArray());
            stream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        this.uri = Uri.fromFile(file);
    }

    public Uri getUri() {
        return uri;
    }

    public void cleanInternalCache(Context context) {
        deleteFilesByDirectory(context.getCacheDir());
    }

    private void deleteFilesByDirectory(File directory) {
        if (directory != null && directory.exists() && directory.isDirectory()) {
            for (File item : directory.listFiles()) {
                item.delete();
            }
        }
    }
}