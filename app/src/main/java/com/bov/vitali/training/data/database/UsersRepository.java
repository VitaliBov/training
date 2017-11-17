package com.bov.vitali.training.data.database;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MediatorLiveData;

import com.bov.vitali.training.data.database.dao.UserDao;
import com.bov.vitali.training.data.database.entity.User;

import java.util.List;

public class UsersRepository {
    private static UsersRepository instance;
    private MediatorLiveData<List<User>> observableUsers;

    private UsersRepository(UserDao userDao) {
        observableUsers = new MediatorLiveData<>();
        observableUsers.addSource(userDao.getUsersLiveData(), usersEntities -> observableUsers.postValue(usersEntities));
    }

    public static UsersRepository getInstance(final UserDao userDao) {
        if (instance == null) {
            synchronized (UsersRepository.class) {
                if (instance == null) {
                    instance = new UsersRepository(userDao);
                }
            }
        }
        return instance;
    }

    public LiveData<List<User>> getUsers() {
        return observableUsers;
    }
}