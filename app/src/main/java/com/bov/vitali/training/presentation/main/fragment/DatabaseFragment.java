package com.bov.vitali.training.presentation.main.fragment;

import android.support.design.widget.TextInputLayout;
import android.widget.Button;

import com.arellomobile.mvp.presenter.InjectPresenter;
import com.arellomobile.mvp.presenter.ProvidePresenter;
import com.bov.vitali.training.R;
import com.bov.vitali.training.presentation.base.fragment.BaseFragment;
import com.bov.vitali.training.presentation.main.common.NameTextWatcher;
import com.bov.vitali.training.presentation.main.presenter.DatabasePresenter;
import com.bov.vitali.training.presentation.main.view.DatabaseContract;
import com.bov.vitali.training.presentation.navigation.BackButtonListener;
import com.bov.vitali.training.presentation.navigation.RouterProvider;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;

@EFragment(R.layout.fragment_database)
public class DatabaseFragment extends BaseFragment<DatabasePresenter, DatabaseContract.View> implements DatabaseContract.View, BackButtonListener {
    @InjectPresenter DatabasePresenter presenter;
    @ViewById Button btnDatabaseSave, btnDatabaseUpdate, btnDatabaseDelete, btnDatabaseSearch;
    @ViewById TextInputLayout etDatabaseSaveUsername, etDatabaseSaveCity, etDatabaseUpdateUsername,
            etDatabaseUpdateNewUsername, etDatabaseDeleteUsername, etDatabaseSearchUsername;
    private String username, city, newUsername;

    @AfterViews
    public void afterViews() {
        disableButtons();
        addTextListener();
    }

    @ProvidePresenter
    DatabasePresenter provideDatabasePresenter() {
        return new DatabasePresenter(((RouterProvider) getParentFragment()).getRouter());
    }

    @Click(R.id.btnDatabaseSave)
    public void save() {
        username = etDatabaseSaveUsername.getEditText().getText().toString();
        city = etDatabaseSaveCity.getEditText().getText().toString();
        presenter.save(username, city);
    }

    @Click(R.id.btnDatabaseUpdate)
    public void update() {
        username = etDatabaseUpdateUsername.getEditText().getText().toString();
        newUsername = etDatabaseUpdateNewUsername.getEditText().getText().toString();
        presenter.update(username, newUsername);
    }

    @Click(R.id.btnDatabaseDelete)
    public void delete() {
        this.username = etDatabaseDeleteUsername.getEditText().getText().toString();
        presenter.delete(username);
    }

    @Click(R.id.btnDatabaseSearch)
    public void search() {
        this.username = etDatabaseSearchUsername.getEditText().getText().toString();
        presenter.search(username);
    }

    @Override
    public void clearSaveFields() {
        etDatabaseSaveUsername.getEditText().setText("");
        etDatabaseSaveCity.getEditText().setText("");
    }

    @Override
    public void clearUpdateFields() {
        etDatabaseUpdateUsername.getEditText().setText("");
        etDatabaseUpdateNewUsername.getEditText().setText("");
    }

    @Override
    public void clearDeleteFields() {
        etDatabaseDeleteUsername.getEditText().setText("");
    }

    @Override
    public void clearSearchFields() {
        etDatabaseSearchUsername.getEditText().setText("");
    }

    @Override
    public void showSaveMessage(String result) {
        toast(getString(R.string.database_message_user) + " " + result + " " + getString(R.string.database_message_save));
    }

    @Override
    public void showUpdateMessage(String result) {
        toast(getString(R.string.database_message_user) + " " + result + " " + getString(R.string.database_message_update));
    }

    @Override
    public void showDeleteMessage(String result) {
        toast(getString(R.string.database_message_user) + " " + result + " " + getString(R.string.database_message_delete));
    }

    @Override
    public void showSearchResult(String result) {
        toast(getString(R.string.database_message_user) + " " + result + " " + getString(R.string.database_message_search));
    }

    private void disableButtons() {
        btnDatabaseSave.setEnabled(false);
        btnDatabaseUpdate.setEnabled(false);
        btnDatabaseDelete.setEnabled(false);
        btnDatabaseSearch.setEnabled(false);
    }

    private void addTextListener() {
        etDatabaseSaveUsername.getEditText().addTextChangedListener(new NameTextWatcher(etDatabaseSaveUsername, btnDatabaseSave));
        etDatabaseUpdateUsername.getEditText().addTextChangedListener(new NameTextWatcher(etDatabaseUpdateUsername, btnDatabaseUpdate));
        etDatabaseDeleteUsername.getEditText().addTextChangedListener(new NameTextWatcher(etDatabaseDeleteUsername, btnDatabaseDelete));
        etDatabaseSearchUsername.getEditText().addTextChangedListener(new NameTextWatcher(etDatabaseSearchUsername, btnDatabaseSearch));
    }

    @Override
    public boolean onBackPressed() {
        presenter.onBackPressed();
        return true;
    }
}