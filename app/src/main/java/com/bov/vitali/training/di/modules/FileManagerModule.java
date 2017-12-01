package com.bov.vitali.training.di.modules;

import com.bov.vitali.training.data.FileManager;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class FileManagerModule {

    @Provides
    @Singleton
    FileManager provideFileManager() {
        return new FileManager();
    }
}