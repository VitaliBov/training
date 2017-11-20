package com.bov.vitali.training.di.modules;

import com.bov.vitali.training.data.net.FilmsApi;
import com.bov.vitali.training.data.net.ServiceGenerator;
import com.bov.vitali.training.data.net.TrainingApi;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class ApiModule {

    @Provides
    @Singleton
    TrainingApi provideTrainingApi() {
        return ServiceGenerator.getInstance().getTrainingService();
    }

    @Provides
    @Singleton
    FilmsApi provideFilmsApi() {
        return ServiceGenerator.getInstance().getFilmsService();
    }
}