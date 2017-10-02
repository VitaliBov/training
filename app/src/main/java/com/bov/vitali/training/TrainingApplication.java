package com.bov.vitali.training;

import android.app.Application;
import android.content.Context;

import com.bov.vitali.training.data.network.ServiceGenerator;
import com.bov.vitali.training.data.network.TrainingApi;

import ru.terrakok.cicerone.Cicerone;
import ru.terrakok.cicerone.NavigatorHolder;
import ru.terrakok.cicerone.Router;

public class TrainingApplication extends Application {

    public static TrainingApplication INSTANCE;
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

    public static TrainingApi getApi() {
        return ServiceGenerator.getInstance().getTrainingService();
    }
}