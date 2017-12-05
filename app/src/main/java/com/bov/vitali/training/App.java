package com.bov.vitali.training;

import android.app.Application;
import android.arch.persistence.room.Room;
import android.content.Context;

import com.bov.vitali.scheduler.Scheduler;
import com.bov.vitali.training.data.database.UsersDatabase;
import com.bov.vitali.training.di.AppComponent;
import com.bov.vitali.training.di.DaggerAppComponent;

public class App extends Application {
    public static App instance;
    private AppComponent appComponent;
    private static Context appContext;
    private static UsersDatabase usersDatabase;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        appContext = getApplicationContext();
        initDatabase();
    }

    public AppComponent getAppComponent() {
        if (appComponent == null) {
            appComponent = DaggerAppComponent.builder().build();
        }
        return appComponent;
    }

    public static Context appContext() {
        return appContext;
    }

    public static Scheduler getScheduler() {
        return Scheduler.getInstance();
    }

    public static UsersDatabase getUserDatabase() {
        return usersDatabase;
    }

    private void initDatabase() {
        usersDatabase = Room.databaseBuilder(this, UsersDatabase.class, "user").build();
    }
}