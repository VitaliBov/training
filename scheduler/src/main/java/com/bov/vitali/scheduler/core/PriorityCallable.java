package com.bov.vitali.scheduler.core;

import android.support.annotation.NonNull;

import com.bov.vitali.scheduler.common.Priority;

import java.util.concurrent.Callable;

public interface PriorityCallable<T> extends Callable<T>, Comparable<PriorityCallable<T>> {
    Priority getPriority();

    @Override
    default int compareTo(@NonNull final PriorityCallable<T> o) {
        return this.getPriority().compareTo(o.getPriority());
    }
}