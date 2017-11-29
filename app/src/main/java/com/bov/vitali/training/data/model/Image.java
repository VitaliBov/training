package com.bov.vitali.training.data.model;

import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable;

public class Image implements Parcelable {

    private Uri originalUri;
    private Uri changedUri;
    private String text;

    public Uri getOriginalUri() {
        return originalUri;
    }

    public void setOriginalUri(Uri originalUri) {
        this.originalUri = originalUri;
    }

    public Uri getChangedUri() {
        return changedUri;
    }

    public void setChangedUri(Uri changedUri) {
        this.changedUri = changedUri;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(this.originalUri, flags);
        dest.writeParcelable(this.changedUri, flags);
        dest.writeString(this.text);
    }

    public Image() {
    }

    protected Image(Parcel in) {
        this.originalUri = in.readParcelable(Uri.class.getClassLoader());
        this.changedUri = in.readParcelable(Uri.class.getClassLoader());
        this.text = in.readString();
    }

    public static final Parcelable.Creator<Image> CREATOR = new Parcelable.Creator<Image>() {
        @Override
        public Image createFromParcel(Parcel source) {
            return new Image(source);
        }

        @Override
        public Image[] newArray(int size) {
            return new Image[size];
        }
    };
}