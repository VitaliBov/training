package com.bov.vitali.scheduler.core;

import android.support.annotation.NonNull;

import com.bov.vitali.scheduler.common.Priority;

import java.util.concurrent.Callable;

public abstract class PriorityCallable<T> implements Callable<T>, Comparable<PriorityCallable<T>> {
    private Priority priority = Priority.NORMAL;

    protected PriorityCallable(Priority priority) {
        this.priority = priority;
    }

    @Override
    public int compareTo(@NonNull PriorityCallable<T> o) {
        return priority.compareTo(o.priority);
    }
}