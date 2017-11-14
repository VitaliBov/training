package com.bov.vitali.training.presentation.main.presenter;

import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;

import com.arellomobile.mvp.InjectViewState;
import com.bov.vitali.training.App;
import com.bov.vitali.training.data.database.entity.User;
import com.bov.vitali.training.presentation.base.presenter.BasePresenter;
import com.bov.vitali.training.presentation.main.common.DatabaseLoader;
import com.bov.vitali.training.presentation.main.view.DatabaseListContract;

import java.util.List;

@InjectViewState
public class DatabaseListPresenter extends BasePresenter<DatabaseListContract.View>
        implements DatabaseListContract.Presenter, LoaderManager.LoaderCallbacks<List<User>> {
    private boolean isFirstStart = true;

    @Override
    public void getUsers() {
        if (isFirstStart) {
            getViewState().initLoader();
            isFirstStart = false;
        } else {
            getViewState().restartLoader();
        }
    }

    @Override
    public Loader<List<User>> onCreateLoader(int id, Bundle args) {
        return new DatabaseLoader(App.appContext());
    }

    @Override
    public void onLoadFinished(Loader<List<User>> loader, List<User> data) {
        if (data != null) {
            getViewState().setUsers(data);
        }
    }

    @Override
    public void onLoaderReset(Loader<List<User>> loader) {

    }
}