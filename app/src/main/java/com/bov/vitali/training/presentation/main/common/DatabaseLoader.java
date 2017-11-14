package com.bov.vitali.training.presentation.main.common;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v4.content.AsyncTaskLoader;

import com.bov.vitali.training.App;
import com.bov.vitali.training.data.database.entity.User;

import java.util.List;

public class DatabaseLoader extends AsyncTaskLoader<List<User>> {

    public DatabaseLoader(Context context) {
        super(context);
    }

    @Override
    protected void onStartLoading() {
        super.onStartLoading();
        forceLoad();
    }

    @Nullable
    @Override
    public List<User> loadInBackground() {
        return App.getUserDatabase().userDao().getUsers();
    }
}