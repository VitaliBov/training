package com.bov.vitali.scheduler.core;

import android.support.annotation.NonNull;

import java.util.concurrent.Callable;

public interface PriorityCallable<T> extends Callable<T>, Comparable<PriorityCallable<T>> {
    int getPriority();

    @Override
    default int compareTo(@NonNull final PriorityCallable<T> o) {
        return Integer.valueOf(getPriority()).compareTo(o.getPriority());
    }
}