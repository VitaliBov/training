package com.bov.vitali.training.presentation.main.fragment;

import android.os.AsyncTask;
import android.support.design.widget.TextInputLayout;
import android.util.Log;
import android.widget.Button;

import com.arellomobile.mvp.presenter.InjectPresenter;
import com.bov.vitali.training.App;
import com.bov.vitali.training.R;
import com.bov.vitali.training.data.database.dao.UserDao;
import com.bov.vitali.training.data.database.entity.Address;
import com.bov.vitali.training.data.database.entity.User;
import com.bov.vitali.training.presentation.base.fragment.BaseFragment;
import com.bov.vitali.training.presentation.main.common.NameTextWatcher;
import com.bov.vitali.training.presentation.main.presenter.DatabasePresenter;
import com.bov.vitali.training.presentation.main.view.DatabaseContract;
import com.bov.vitali.training.presentation.navigation.BackButtonListener;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;

@EFragment(R.layout.fragment_database)
public class DatabaseFragment extends BaseFragment<DatabasePresenter, DatabaseContract.View> implements DatabaseContract.View, BackButtonListener {
    @InjectPresenter
    DatabasePresenter presenter;
    @ViewById
    Button btnDatabaseSave, btnDatabaseUpdate, btnDatabaseDelete, btnDatabaseSearch;
    @ViewById
    TextInputLayout etDatabaseSaveUsername, etDatabaseSaveCity, etDatabaseUpdateUsername,
            etDatabaseUpdateNewUsername, etDatabaseDeleteUsername, etDatabaseSearchUsername;
    private UserDao userDao = App.getUserDatabase().userDao();

    @AfterViews
    public void afterViews() {
        disableButtons();
        addTextListener();
    }

    @Click(R.id.btnDatabaseSave)
    public void save() {
        SaveAsyncTask saveAsyncTask = new SaveAsyncTask();
        saveAsyncTask.execute();
    }

    @Click(R.id.btnDatabaseUpdate)
    public void update() {
        UpdateAsyncTask updateAsyncTask = new UpdateAsyncTask();
        updateAsyncTask.execute();
    }

    @Click(R.id.btnDatabaseDelete)
    public void delete() {
        DeleteAsyncTask deleteAsyncTask = new DeleteAsyncTask();
        deleteAsyncTask.execute();
    }

    @Click(R.id.btnDatabaseSearch)
    public void search() {
        SearchAsyncTask searchAsyncTask = new SearchAsyncTask();
        searchAsyncTask.execute();
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

    private class SaveAsyncTask extends AsyncTask<Void, Void, Void> {
        @Override
        protected Void doInBackground(Void... voids) {
            User user = new User();
            Address address = new Address();
            user.setUsername(etDatabaseSaveUsername.getEditText().getText().toString());
            address.setCity(etDatabaseSaveCity.getEditText().getText().toString());
            user.setAddress(address);
            userDao.insert(user);
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            etDatabaseSaveUsername.getEditText().setText("");
            etDatabaseSaveCity.getEditText().setText("");
        }
    }

    private class UpdateAsyncTask extends AsyncTask<Void, Void, Void> {
        @Override
        protected Void doInBackground(Void... voids) {
            User user = userDao.findByUsername(etDatabaseUpdateUsername.getEditText().getText().toString());
            user.setUsername(etDatabaseUpdateNewUsername.getEditText().getText().toString());
            userDao.update(user);
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            etDatabaseUpdateUsername.getEditText().setText("");
            etDatabaseUpdateNewUsername.getEditText().setText("");
        }
    }

    private class DeleteAsyncTask extends AsyncTask<Void, Void, Void> {
        @Override
        protected Void doInBackground(Void... voids) {
            User user = userDao.findByUsername(etDatabaseDeleteUsername.getEditText().getText().toString());
            userDao.delete(user);
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            etDatabaseDeleteUsername.getEditText().setText("");
        }
    }

    private class SearchAsyncTask extends AsyncTask<Void, Void, Void> {
        private String result;

        @Override
        protected Void doInBackground(Void... voids) {
            User user = userDao.findByUsername(etDatabaseSearchUsername.getEditText().getText().toString());
            result = user.getUsername() + ", " + user.getAddress().getCity();
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            etDatabaseSearchUsername.getEditText().setText("");
            toast(result);
        }
    }
}