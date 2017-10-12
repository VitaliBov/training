package com.bov.vitali.training.presentation.main.fragment;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.arellomobile.mvp.presenter.InjectPresenter;
import com.bov.vitali.training.App;
import com.bov.vitali.training.R;
import com.bov.vitali.training.common.navigation.BackButtonListener;
import com.bov.vitali.training.common.preferences.Preferences;
import com.bov.vitali.training.data.model.Publication;
import com.bov.vitali.training.presentation.base.fragment.BaseFragment;
import com.bov.vitali.training.presentation.main.adapter.PublicationsAdapter;
import com.bov.vitali.training.presentation.main.presenter.PublicationsPresenter;
import com.bov.vitali.training.presentation.main.view.PublicationsView;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;

@EFragment(R.layout.fragment_publications)
public class PublicationsFragment extends BaseFragment implements PublicationsView, BackButtonListener {
    @InjectPresenter PublicationsPresenter presenter;
    @ViewById(R.id.swipeRefreshLayout) SwipeRefreshLayout swipeRefreshLayout;
    @ViewById(R.id.rwList) RecyclerView rwPublications;
//    @ViewById(R.id.ivResponseErrorPublications) ImageView errorImage;
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
        showUpdatingSpinner();
        setupRecyclerView();
        setupSwipeToRefresh();
    }

    @Override
    public void setPublications(Publication publications) {
        checkAdapter();
        adapter.setPublications(publications);
    }

    private void setupRecyclerView() {
        rwPublications.setHasFixedSize(true);
        rwPublications.setLayoutManager(new LinearLayoutManager(getActivity()));
    }

    private void setupSwipeToRefresh() {
        swipeRefreshLayout.setOnRefreshListener(() -> {
            hideUpdatingSpinner();
            presenter.getPublications(userId);
        });
        swipeRefreshLayout.setColorSchemeResources(R.color.colorPrimary);
    }

    private void checkAdapter() {
        if (adapter == null) {
            initAdapter();
        } else {
            rwPublications.setAdapter(adapter);
        }
    }

    private void initAdapter() {
        adapter = new PublicationsAdapter();
        rwPublications.setAdapter(adapter);
        hideUpdatingSpinner();
    }

    @Override
    public void showResponseError() {
        hideUpdatingSpinner();


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