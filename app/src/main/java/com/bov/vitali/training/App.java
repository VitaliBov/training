package com.bov.vitali.training;

import android.app.Application;
import android.content.Context;

import com.bov.vitali.scheduler.Scheduler;
import com.bov.vitali.training.data.net.FilmsApi;
import com.bov.vitali.training.data.net.ServiceGenerator;
import com.bov.vitali.training.data.net.TrainingApi;

import ru.terrakok.cicerone.Cicerone;
import ru.terrakok.cicerone.NavigatorHolder;
import ru.terrakok.cicerone.Router;

public class App extends Application {

    public static App INSTANCE;
    private static Context appContext;
    private Cicerone<Router> cicerone;

    @Override
    public void onCreate() {
        super.onCreate();
        INSTANCE = this;
        appContext = getApplicationContext();
        initCicerone();
    }

    public static Context appContext() {
        return appContext;
    }

    private void initCicerone() {
        cicerone = Cicerone.create();
    }

    public NavigatorHolder getNavigatorHolder() {
        return cicerone.getNavigatorHolder();
    }

    public Router getRouter() {
        return cicerone.getRouter();
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
}