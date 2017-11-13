package com.bov.vitali.training.presentation.main.fragment;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.arellomobile.mvp.presenter.InjectPresenter;
import com.bov.vitali.training.App;
import com.bov.vitali.training.R;
import com.bov.vitali.training.presentation.navigation.BackButtonListener;
import com.bov.vitali.training.common.preferences.Preferences;
import com.bov.vitali.training.data.model.Publication;
import com.bov.vitali.training.presentation.base.fragment.BaseFragment;
import com.bov.vitali.training.presentation.main.adapter.PublicationsAdapter;
import com.bov.vitali.training.presentation.main.presenter.PublicationsPresenter;
import com.bov.vitali.training.presentation.main.view.PublicationsContract;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;

import java.util.List;

@EFragment(R.layout.fragment_publications)
public class PublicationsFragment extends BaseFragment<PublicationsPresenter, PublicationsContract.View>
        implements PublicationsContract.View, BackButtonListener {
    @InjectPresenter PublicationsPresenter presenter;
    @ViewById(R.id.swipeRefreshPublications) SwipeRefreshLayout swipeRefreshLayout;
    @ViewById(R.id.rvPublications) RecyclerView rvPublications;
    @ViewById ViewGroup emptyView;
    @ViewById TextView emptyViewText;
    private PublicationsAdapter adapter;
    private String userId = Preferences.getUserId(App.appContext());

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter.getPublications(userId);
        setRetainInstance(true);
    }

    @AfterViews
    public void afterViews() {
        hideResponseError();
        showUpdatingSpinner();
        setupRecyclerView();
        setupSwipeToRefresh();
    }

    private void setupRecyclerView() {
        rvPublications.setHasFixedSize(true);
        rvPublications.setLayoutManager(new LinearLayoutManager(getActivity()));
    }

    private void setupSwipeToRefresh() {
        swipeRefreshLayout.setOnRefreshListener(() -> presenter.getPublications(userId));
        swipeRefreshLayout.setColorSchemeResources(R.color.colorPrimary);
    }

    @Override
    public void setPublications(List<Publication> publications) {
        checkAdapter();
        adapter.setPublications(publications);
        hideUpdatingSpinner();
    }

    private void checkAdapter() {
        if (adapter == null) {
            initAdapter();
        } else {
            rvPublications.setAdapter(adapter);
        }
    }

    private void initAdapter() {
        adapter = new PublicationsAdapter();
        rvPublications.setAdapter(adapter);
    }

    @Override
    public void showResponseError() {
        hideUpdatingSpinner();
        emptyView.setVisibility(View.VISIBLE);
        rvPublications.setVisibility(View.GONE);
        emptyViewText.setText(getResources().getString(R.string.publications_error_response));
    }

    @Override
    public void hideResponseError() {
        emptyView.setVisibility(View.GONE);
        rvPublications.setVisibility(View.VISIBLE);
    }

    @Override
    public void showUpdatingSpinner() {
        if (adapter == null) swipeRefreshLayout.setRefreshing(true);
    }

    @Override
    public void hideUpdatingSpinner() {
        swipeRefreshLayout.setRefreshing(false);
    }
}