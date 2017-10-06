package com.bov.vitali.training.presentation.main.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.arellomobile.mvp.presenter.InjectPresenter;
import com.bov.vitali.training.App;
import com.bov.vitali.training.R;
import com.bov.vitali.training.common.navigation.BackButtonListener;
import com.bov.vitali.training.common.utils.AndroidUtils;
import com.bov.vitali.training.data.model.Publication;
import com.bov.vitali.training.presentation.base.fragment.BaseFragment;
import com.bov.vitali.training.presentation.main.adapter.PublicationsAdapter;
import com.bov.vitali.training.presentation.main.presenter.PublicationsPresenter;
import com.bov.vitali.training.presentation.main.view.PublicationsView;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.FragmentArg;
import org.androidannotations.annotations.ViewById;

@EFragment(R.layout.fragment_publications)
public class PublicationsFragment extends BaseFragment implements PublicationsView, BackButtonListener,
        PublicationsAdapter.PublicationClickListener {
    @FragmentArg String actionBarTitle;
    @InjectPresenter PublicationsPresenter presenter;
    @ViewById(R.id.rwList) RecyclerView rwPublications;
    private PublicationsAdapter adapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter.getPublications("1cc23eed206a170b0a9299323424396f664708c7561bb7d5f4a07dc097f5a53eb");
        setRetainInstance(true);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setActionBarTitle();
    }

    @AfterViews
    public void afterViews() {
        setupRecyclerView();
        checkAdapter(this);
    }

    private void setupRecyclerView() {
        rwPublications.setHasFixedSize(true);
        final LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        rwPublications.setLayoutManager(layoutManager);
    }

    private void checkAdapter(PublicationsAdapter.PublicationClickListener listener) {
        if (adapter == null) {
            initAdapter(listener);
        } else {
            rwPublications.setAdapter(adapter);
        }
    }

    private void initAdapter(PublicationsAdapter.PublicationClickListener listener) {
        adapter = new PublicationsAdapter(listener);
        rwPublications.setAdapter(adapter);
    }

    @Override
    public void setPublications(Publication publications) {
        adapter.setPublications(publications);
    }

    private void setActionBarTitle() {
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle(actionBarTitle);
    }

    @Override
    public void showError() {
        AndroidUtils.toast(App.appContext(), App.appContext().getResources().getString(R.string.error));
    }

    @Override
    public void onClick(Publication.PublicationData publication) {
        navigateToPublicationContributorFragment();
    }

    private void navigateToPublicationContributorFragment() {

    }

    @Override
    public boolean onBackPressed() {
        Fragment fragment = getChildFragmentManager().findFragmentById(R.id.publications_container);
        if (fragment != null
                && fragment instanceof BackButtonListener
                && ((BackButtonListener) fragment).onBackPressed()) {
            return true;
        } else {
            getActivity().finishAffinity();
            return true;
        }
    }
}