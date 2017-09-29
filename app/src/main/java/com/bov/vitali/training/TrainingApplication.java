package com.bov.vitali.training;

import android.app.Application;

import com.bov.vitali.training.data.network.ServiceGenerator;
import com.bov.vitali.training.data.network.TrainingApi;

import ru.terrakok.cicerone.Cicerone;
import ru.terrakok.cicerone.NavigatorHolder;
import ru.terrakok.cicerone.Router;

public class TrainingApplication extends Application {

    public static TrainingApplication INSTANCE;
    private Cicerone<Router> cicerone;

    @Override
    public void onCreate() {
        super.onCreate();
        INSTANCE = this;
        initCicerone();
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
}