package com.bov.vitali.scheduler.core;

import android.support.annotation.NonNull;

import java.util.concurrent.FutureTask;

public class PriorityCallableFutureTask<V> extends FutureTask<V> implements Comparable<PriorityCallableFutureTask<V>> {
    private final PriorityCallable<V> callable;

    public PriorityCallableFutureTask(final PriorityCallable<V> callable) {
        super(callable);
        this.callable = callable;
    }

    @Override
    public int compareTo(@NonNull final PriorityCallableFutureTask<V> o) {
        return callable.compareTo(o.callable);
    }
}