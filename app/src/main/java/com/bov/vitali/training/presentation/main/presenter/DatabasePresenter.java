package com.bov.vitali.training.presentation.main.presenter;

import android.os.AsyncTask;

import com.arellomobile.mvp.InjectViewState;
import com.bov.vitali.training.App;
import com.bov.vitali.training.data.database.dao.UserDao;
import com.bov.vitali.training.data.database.entity.Address;
import com.bov.vitali.training.data.database.entity.User;
import com.bov.vitali.training.presentation.base.presenter.BasePresenter;
import com.bov.vitali.training.presentation.main.view.DatabaseContract;

import ru.terrakok.cicerone.Router;

@InjectViewState
public class DatabasePresenter extends BasePresenter<DatabaseContract.View> implements DatabaseContract .Presenter {
    private UserDao userDao = App.getUserDatabase().userDao();
    private String username, city, newUsername;
    private Router router;

    public DatabasePresenter(Router router) {
        this.router = router;
    }

    @Override
    public void save(String username, String city) {
        this.username = username;
        this.city = city;
        new SaveAsyncTask().execute();
    }

    @Override
    public void update(String oldUsername, String newUsername) {
        this.username = oldUsername;
        this.newUsername = newUsername;
        new UpdateAsyncTask().execute();
    }

    @Override
    public void delete(String username) {
        this.username = username;
        new DeleteAsyncTask().execute();
    }

    @Override
    public void search(String username) {
        this.username = username;
        new SearchAsyncTask().execute();
    }

    private class SaveAsyncTask extends AsyncTask<Void, Void, Void> {
        @Override
        protected Void doInBackground(Void... voids) {
            User user = new User();
            Address address = new Address();
            user.setUsername(username);
            address.setCity(city);
            user.setAddress(address);
            userDao.insert(user);
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            getViewState().clearSaveFields();
            getViewState().showSaveMessage(username);
        }
    }

    private class UpdateAsyncTask extends AsyncTask<Void, Void, Void> {
        @Override
        protected Void doInBackground(Void... voids) {
            User user = userDao.getByUsername(username);
            user.setUsername(newUsername);
            userDao.update(user);
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            getViewState().clearUpdateFields();
            getViewState().showUpdateMessage(newUsername);
        }
    }

    private class DeleteAsyncTask extends AsyncTask<Void, Void, Void> {
        @Override
        protected Void doInBackground(Void... voids) {
            User user = userDao.getByUsername(username);
            userDao.delete(user);
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            getViewState().clearDeleteFields();
            getViewState().showSearchResult(username);
        }
    }

    private class SearchAsyncTask extends AsyncTask<Void, Void, Void> {
        @Override
        protected Void doInBackground(Void... voids) {
            User user = userDao.getByUsername(username);
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            getViewState().clearSearchFields();
            getViewState().showSearchResult(username);
        }
    }

    @Override
    public void onBackPressed() {
        router.exit();
    }
}