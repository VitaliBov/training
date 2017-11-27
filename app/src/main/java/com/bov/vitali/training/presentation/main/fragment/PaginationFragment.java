package com.bov.vitali.training.presentation.main.fragment;

import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.arellomobile.mvp.presenter.InjectPresenter;
import com.bov.vitali.training.R;
import com.bov.vitali.training.presentation.navigation.BackButtonListener;
import com.bov.vitali.training.data.model.Film;
import com.bov.vitali.training.presentation.base.fragment.BaseFragment;
import com.bov.vitali.training.presentation.main.adapter.PaginationAdapter;
import com.bov.vitali.training.presentation.main.common.PaginationScrollListener;
import com.bov.vitali.training.presentation.main.presenter.PaginationPresenter;
import com.bov.vitali.training.presentation.main.view.PaginationContract;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;

import java.util.List;

@EFragment(R.layout.fragment_pagination)
public class PaginationFragment extends BaseFragment<PaginationPresenter, PaginationContract.View>
        implements PaginationContract.View, BackButtonListener {
    private boolean isLastPage = false;
    private int TOTAL_PAGES = 10;
    private boolean isLoading = false;
    private PaginationAdapter adapter;
    private LinearLayoutManager linearLayoutManager;
    @InjectPresenter PaginationPresenter presenter;
    @ViewById RecyclerView rvPagination;
    @ViewById ProgressBar pbPagination;
    @ViewById ViewGroup emptyView;
    @ViewById TextView emptyViewText;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        boolean isRestore = savedInstanceState != null;
        if (!isRestore) presenter.loadMoreFilms();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        getActivity().getMenuInflater().inflate(R.menu.pagination_menu, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_refresh:
                presenter.resetAndRetrieve();
                break;
            default:
                break;
        }
        return true;
    }

    @AfterViews
    public void afterViews() {
        setHasOptionsMenu(true);
        clearView();
        hideResponseError();
        initLayoutManager();
        setupRecyclerView();
    }

    private void setupRecyclerView() {
        if (adapter != null) rvPagination.setAdapter(adapter);
        rvPagination.setHasFixedSize(true);
        rvPagination.setLayoutManager(linearLayoutManager);
        rvPagination.setItemAnimator(new DefaultItemAnimator());
        rvPagination.addOnScrollListener(new PaginationScrollListener(linearLayoutManager) {
            @Override
            protected void loadMoreItems() {
                isLoading = true;
                presenter.loadMoreFilms();
            }

            @Override
            public int getTotalPageCount() {
                return TOTAL_PAGES;
            }

            @Override
            public boolean isLastPage() {
                return isLastPage;
            }

            @Override
            public boolean isLoading() {
                return isLoading;
            }
        });
    }

    @Override
    public void onStop() {
        super.onStop();
        setHasOptionsMenu(false);
    }

    @Override
    public void renderFilms(List<Film> films) {
        pbPagination.setVisibility(View.GONE);
        isLoading = false;
        if (adapter == null) initAdapter();
        adapter.addAll(films);
    }

    @Override
    public void clearView() {
        adapter = null;
    }

    private void initLayoutManager() {
        linearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
    }

    private void initAdapter() {
        adapter = new PaginationAdapter();
        rvPagination.setAdapter(adapter);
    }

    @Override
    public void showResponseError() {
        emptyView.setVisibility(View.VISIBLE);
        rvPagination.setVisibility(View.GONE);
        emptyViewText.setText(getResources().getString(R.string.pagination_error_response));
    }

    @Override
    public void hideResponseError() {
        emptyView.setVisibility(View.GONE);
        rvPagination.setVisibility(View.VISIBLE);
    }

    @Override
    public boolean onBackPressed() {
        return false;
    }
}