package com.bov.vitali.training.di.modules;

import android.content.Context;

import com.bov.vitali.training.App;

import dagger.Module;
import dagger.Provides;

@Module
public class ContextModule {

    @Provides
    Context provideContext(App app) {
        return app.getApplicationContext();
    }
}