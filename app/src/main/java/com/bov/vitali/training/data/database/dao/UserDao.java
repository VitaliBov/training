package com.bov.vitali.training.data.database.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.bov.vitali.training.data.database.entity.User;

import java.util.List;

@Dao
public interface UserDao {

    @Query("SELECT * FROM Users")
    List<User> getUsers();

    @Query("SELECT * FROM Users WHERE username LIKE :username")
    User getByUsername(String username);

    @Insert
    void insert(User user);

    @Insert
    void insertUsers(User... users);

    @Delete
    void delete(User user);

    @Update
    void update(User user);
}