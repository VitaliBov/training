package com.bov.vitali.training.presentation.main.fragment;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.arellomobile.mvp.presenter.InjectPresenter;
import com.bov.vitali.training.R;
import com.bov.vitali.training.data.database.entity.User;
import com.bov.vitali.training.presentation.base.fragment.BaseFragment;
import com.bov.vitali.training.presentation.main.adapter.DatabaseListAdapter;
import com.bov.vitali.training.presentation.main.presenter.DatabaseListPresenter;
import com.bov.vitali.training.presentation.main.view.DatabaseListContract;
import com.bov.vitali.training.presentation.navigation.BackButtonListener;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;

import java.util.List;

@EFragment(R.layout.fragment_database_list)
public class DatabaseListFragment extends BaseFragment<DatabaseListPresenter, DatabaseListContract.View>
        implements DatabaseListContract.View, BackButtonListener {
    @InjectPresenter DatabaseListPresenter presenter;
    @ViewById(R.id.swipeRefreshDatabaseList) SwipeRefreshLayout swipeRefreshLayout;
    @ViewById(R.id.rvDatabaseList)RecyclerView rvUsers;
    @ViewById ViewGroup emptyView;
    @ViewById TextView emptyViewText;
    private DatabaseListAdapter adapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter.getUsers();
    }

    @AfterViews
    public void afterViews() {
        hideResponseError();
        showUpdatingSpinner();
        setupRecyclerView();
        setupSwipeToRefresh();
    }

    @Override
    public void initLoader() {
        getActivity().getSupportLoaderManager().initLoader(101, null, presenter).forceLoad();
    }

    @Override
    public void restartLoader() {
        getActivity().getSupportLoaderManager().restartLoader(101, null, presenter).forceLoad();
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
        emptyViewText.setText(getResources().getString(R.string.database_list_error_response));
    }

    @Override
    public void hideResponseError() {
        emptyView.setVisibility(View.GONE);
        rvUsers.setVisibility(View.VISIBLE);
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
        swipeRefreshLayout.setOnRefreshListener(() -> presenter.getUsers());
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
}