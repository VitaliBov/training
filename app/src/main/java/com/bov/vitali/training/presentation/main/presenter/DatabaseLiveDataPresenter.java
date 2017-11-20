package com.bov.vitali.training.presentation.main.presenter;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MediatorLiveData;

import com.bov.vitali.training.App;
import com.bov.vitali.training.data.database.UsersRepository;
import com.bov.vitali.training.data.database.dao.UserDao;
import com.bov.vitali.training.data.database.entity.User;
import com.bov.vitali.training.presentation.base.presenter.BasePresenter;
import com.bov.vitali.training.presentation.main.view.DatabaseLiveDataContract;

import java.util.List;

import javax.inject.Inject;

import ru.terrakok.cicerone.Router;

public class DatabaseLiveDataPresenter extends BasePresenter<DatabaseLiveDataContract.View>
        implements DatabaseLiveDataContract.Presenter {
    private MediatorLiveData<List<User>> observableUsers;
    private Router router;

    public DatabaseLiveDataPresenter(Router router) {
        this.router = router;
    }

    public void initUserRepository(UserDao userDao) {
        observableUsers = new MediatorLiveData<>();
        observableUsers.setValue(null);
        LiveData<List<User>> users = UsersRepository.getInstance(userDao).getUsers();
        observableUsers.addSource(users, observableUsers::setValue);
    }

    public LiveData<List<User>> getUsers() {
        return observableUsers;
    }

    @Override
    public void onBackPressed() {
        router.exit();
    }
}