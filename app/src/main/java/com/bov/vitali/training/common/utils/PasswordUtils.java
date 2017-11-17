package com.bov.vitali.training.common.utils;

import com.bov.vitali.training.App;
import com.bov.vitali.training.presentation.main.activity.PasswordActivity_;

import java.util.Date;
import java.util.concurrent.TimeUnit;

public class PasswordUtils {
    private static final int PAUSE_TIME_IN_SECONDS = 5;
    private static Date appPauseTime;

    public static void lockAppStoreTime() {
        appPauseTime = new Date();
    }

    public static void lockAppCheck() {
        boolean isLock = false;

        if (appPauseTime == null) {
            isLock = true;
        } else {
            Date currentTime = new Date();
            long differenceInMilliseconds = currentTime.getTime() - appPauseTime.getTime();
            long differenceTime = TimeUnit.MILLISECONDS.toSeconds(differenceInMilliseconds);

            if (differenceTime > PAUSE_TIME_IN_SECONDS) {
                isLock = true;
            }
        }

        if (isLock) {
            PasswordActivity_.intent(App.appContext()).start();
        }
    }
}