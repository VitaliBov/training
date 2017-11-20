package com.bov.vitali.training;

import android.app.Application;
import android.arch.persistence.room.Room;
import android.content.Context;

import com.bov.vitali.scheduler.Scheduler;
import com.bov.vitali.training.data.database.UsersDatabase;
import com.bov.vitali.training.data.net.FilmsApi;
import com.bov.vitali.training.data.net.ServiceGenerator;
import com.bov.vitali.training.data.net.TrainingApi;
import com.bov.vitali.training.di.AppComponent;
import com.bov.vitali.training.di.DaggerAppComponent;

public class App extends Application {
    public static App INSTANCE;
    private AppComponent appComponent;
    private static Context appContext;
    private static UsersDatabase usersDatabase;

    @Override
    public void onCreate() {
        super.onCreate();
        INSTANCE = this;

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

    public static TrainingApi getTrainingApi() {
        return ServiceGenerator.getInstance().getTrainingService();
    }

    public static FilmsApi getFilmsApi() {
        return ServiceGenerator.getInstance().getFilmsService();
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