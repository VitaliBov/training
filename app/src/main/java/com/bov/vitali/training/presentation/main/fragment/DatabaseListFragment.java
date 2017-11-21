package com.bov.vitali.training.presentation.main.fragment;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.arellomobile.mvp.presenter.InjectPresenter;
import com.arellomobile.mvp.presenter.ProvidePresenter;
import com.bov.vitali.training.App;
import com.bov.vitali.training.R;
import com.bov.vitali.training.data.database.dao.UserDao;
import com.bov.vitali.training.data.database.entity.Address;
import com.bov.vitali.training.data.database.entity.User;
import com.bov.vitali.training.presentation.base.fragment.BaseFragment;
import com.bov.vitali.training.presentation.main.adapter.DatabaseListAdapter;
import com.bov.vitali.training.presentation.main.common.DatabaseLoader;
import com.bov.vitali.training.presentation.main.presenter.DatabaseListPresenter;
import com.bov.vitali.training.presentation.main.view.DatabaseListContract;
import com.bov.vitali.training.presentation.navigation.BackButtonListener;
import com.bov.vitali.training.presentation.navigation.RouterProvider;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.UiThread;
import org.androidannotations.annotations.ViewById;

import java.util.List;
import java.util.UUID;

@EFragment(R.layout.fragment_database_list)
public class DatabaseListFragment extends BaseFragment<DatabaseListPresenter, DatabaseListContract.View>
        implements DatabaseListContract.View, BackButtonListener {
    private static final int USERS_LOADER_ID = 101;
    @InjectPresenter DatabaseListPresenter presenter;
    @ViewById(R.id.swipeRefreshDatabaseList) SwipeRefreshLayout swipeRefreshLayout;
    @ViewById(R.id.rvDatabaseList)RecyclerView rvUsers;
    @ViewById(R.id.fabDatabaseList)FloatingActionButton fabAddUser;
    @ViewById ViewGroup emptyView;
    @ViewById TextView emptyViewText;
    private UserDao userDao = App.getUserDatabase().userDao();
    private DatabaseListAdapter adapter;
    private boolean isFirstStart = true;

    @AfterViews
    public void afterViews() {
        getDatabaseLoader();
        hideResponseError();
        showUpdatingSpinner();
        setupRecyclerView();
        setupSwipeToRefresh();
    }

    @ProvidePresenter
    DatabaseListPresenter provideDatabaseListPresenter() {
        return new DatabaseListPresenter(((RouterProvider) getParentFragment()).getRouter());
    }

    @Override
    public void getDatabaseLoader() {
        if (isFirstStart) {
            initLoader();
            isFirstStart = false;
        } else  {
            restartLoader();
        }
    }

    @Override
    public void setUsers(List<User> users) {
        checkAdapter();
        adapter.setUsers(users);
        hideUpdatingSpinner();
    }

    @Override
    public void showResponseError() {
        hideUpdatingSpinner();
        emptyView.setVisibility(View.VISIBLE);
        rvUsers.setVisibility(View.GONE);
        fabAddUser.setVisibility(View.GONE);
        emptyViewText.setText(getResources().getString(R.string.database_list_error_response));
    }

    @Override
    public void hideResponseError() {
        emptyView.setVisibility(View.GONE);
        rvUsers.setVisibility(View.VISIBLE);
        fabAddUser.setVisibility(View.VISIBLE);
    }

    @Override
    public void showUpdatingSpinner() {
        if (adapter == null) swipeRefreshLayout.setRefreshing(true);
    }

    @Override
    public void hideUpdatingSpinner() {
        swipeRefreshLayout.setRefreshing(false);
    }

    private void setupRecyclerView() {
        rvUsers.setHasFixedSize(true);
        rvUsers.setLayoutManager(new LinearLayoutManager(getActivity()));
    }

    private void setupSwipeToRefresh() {
        swipeRefreshLayout.setOnRefreshListener(this::restartLoader);
        swipeRefreshLayout.setColorSchemeResources(R.color.colorPrimary);
    }

    private void checkAdapter() {
        if (adapter == null) {
            initAdapter();
        } else {
            rvUsers.setAdapter(adapter);
        }
    }

    private void initAdapter() {
        adapter = new DatabaseListAdapter();
        rvUsers.setAdapter(adapter);
    }

    private void initLoader() {
        getActivity().getSupportLoaderManager().initLoader(USERS_LOADER_ID, null, new UsersLoaderCallback()).forceLoad();
    }

    private void restartLoader() {
        getActivity().getSupportLoaderManager().restartLoader(USERS_LOADER_ID, null, new UsersLoaderCallback()).forceLoad();
    }

    @Click(R.id.fabDatabaseList)
    public void fabClick() {
        addDataToDatabase();
    }

    @Background
    public void addDataToDatabase() {
        User user = new User();
        Address address = new Address();
        user.setUsername(UUID.randomUUID().toString());
        address.setCity(UUID.randomUUID().toString());
        user.setAddress(address);
        userDao.insert(user);
        updateUI();
    }

    @UiThread
    public void updateUI() {
        restartLoader();
    }

    @Override
    public boolean onBackPressed() {
        presenter.onBackPressed();
        return true;
    }

    private class UsersLoaderCallback implements LoaderManager.LoaderCallbacks<List<User>> {

        @Override
        public Loader<List<User>> onCreateLoader(int id, Bundle args) {
            return new DatabaseLoader(App.appContext());
        }

        @Override
        public void onLoadFinished(Loader<List<User>> loader, List<User> data) {
            if (data != null) {
                setUsers(data);
            }
        }

        @Override
        public void onLoaderReset(Loader<List<User>> loader) {

        }
    }
}