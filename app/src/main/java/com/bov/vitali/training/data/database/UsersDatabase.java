package com.bov.vitali.training.data.database;


import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

import com.bov.vitali.training.data.database.dao.UserDao;
import com.bov.vitali.training.data.database.entity.User;

@Database(entities = {User.class}, version = 1)
public abstract class UsersDatabase extends RoomDatabase {

    public abstract UserDao userDao();
}