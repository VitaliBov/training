package com.bov.vitali.training.presentation.main.fragment;

import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.arellomobile.mvp.presenter.InjectPresenter;
import com.bov.vitali.training.App;
import com.bov.vitali.training.R;
import com.bov.vitali.training.data.database.dao.UserDao;
import com.bov.vitali.training.data.database.entity.Address;
import com.bov.vitali.training.data.database.entity.User;
import com.bov.vitali.training.presentation.base.fragment.BaseFragment;
import com.bov.vitali.training.presentation.main.adapter.DatabaseListAdapter;
import com.bov.vitali.training.presentation.main.presenter.DatabaseLiveDataPresenter;
import com.bov.vitali.training.presentation.main.view.DatabaseLiveDataContract;
import com.bov.vitali.training.presentation.navigation.BackButtonListener;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;

import java.util.List;
import java.util.UUID;

@EFragment(R.layout.fragment_database_live_data)
public class DatabaseLiveDataFragment extends BaseFragment<DatabaseLiveDataPresenter, DatabaseLiveDataContract.View>
        implements DatabaseLiveDataContract.View, BackButtonListener {
    @InjectPresenter DatabaseLiveDataPresenter presenter;
    @ViewById(R.id.swipeRefreshDatabaseLiveData) SwipeRefreshLayout swipeRefreshLayout;
    @ViewById(R.id.rvDatabaseLiveData) RecyclerView rvUsers;
    @ViewById(R.id.fabDatabaseLiveData) FloatingActionButton fabAddUser;
    @ViewById ViewGroup emptyView;
    @ViewById TextView emptyViewText;
    private UserDao userDao = App.getUserDatabase().userDao();
    private DatabaseListAdapter adapter;

    @AfterViews
    public void afterViews() {
        presenter.initUserRepository(userDao);
        hideResponseError();
        showUpdatingSpinner();
        setupRecyclerView();
        setupSwipeToRefresh();
        getUsers();
    }

    private void getUsers() {
        presenter.getUsers().observe(this, users -> {
            if (users != null) {
                setUsers(users);
            } else {
                showUpdatingSpinner();
            }
        });
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
        swipeRefreshLayout.setOnRefreshListener(this::getUsers);
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

    @Click(R.id.fabDatabaseLiveData)
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
    }
}