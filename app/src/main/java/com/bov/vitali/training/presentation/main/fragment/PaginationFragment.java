package com.bov.vitali.training.presentation.main.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;

import com.arellomobile.mvp.presenter.InjectPresenter;
import com.bov.vitali.training.BuildConfig;
import com.bov.vitali.training.R;
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
        implements PaginationContract.View {
    private static final int PAGE_START = 1;
    private static final String LANGUAGE = "en_US";
    private boolean isLoading = false;
    private boolean isLastPage = false;
    private int TOTAL_PAGES = 10;
    private int currentPage = PAGE_START;
    private PaginationAdapter adapter;
    private LinearLayoutManager linearLayoutManager;
    @InjectPresenter PaginationPresenter presenter;
    @ViewById RecyclerView rvPagination;
    @ViewById ProgressBar pbPagination;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        linearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        loadFirstPage();
        setRetainInstance(true);
    }

    @AfterViews
    public void afterViews() {
        setupRecyclerView();
    }

    private void setupRecyclerView() {
        rvPagination.setHasFixedSize(true);
        rvPagination.setLayoutManager(new LinearLayoutManager(getActivity()));
        rvPagination.setItemAnimator(new DefaultItemAnimator());
        rvPagination.addOnScrollListener(new PaginationScrollListener(linearLayoutManager) {
            @Override
            protected void loadMoreItems() {
                isLoading = true;
                currentPage += 1;
                new Handler().postDelayed(() -> loadNextPage(), 1000);
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
    public void setFirstPageFilms(List<Film> results) {
        checkAdapter();
        pbPagination.setVisibility(View.GONE);
        adapter.addAll(results);
        if (currentPage <= TOTAL_PAGES) adapter.addLoadingFooter();
        else isLastPage = true;
    }

    @Override
    public void setNextPageFilms(List<Film> results) {
        adapter.removeLoadingFooter();
        isLoading = false;
        adapter.addAll(results);
        if (currentPage != TOTAL_PAGES) adapter.addLoadingFooter();
        else isLastPage = true;
    }

    private void checkAdapter() {
        if (adapter == null) {
            initAdapter();
        } else {
            rvPagination.setAdapter(adapter);
        }
    }

    private void initAdapter() {
        adapter = new PaginationAdapter();
        rvPagination.setAdapter(adapter);
    }

    private void loadFirstPage() {
        presenter.getFilms(BuildConfig.THE_MOVIE_DB_IP_KEY, LANGUAGE, currentPage, true);
    }

    private void loadNextPage() {
        presenter.getFilms(BuildConfig.THE_MOVIE_DB_IP_KEY, LANGUAGE, currentPage, false);
    }

    @Override
    public void showResponseError() {

    }
}