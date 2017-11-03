package com.bov.vitali.scheduler.core;

import android.support.annotation.NonNull;

import java.util.concurrent.FutureTask;

public class PriorityRunnableFutureTask<V> extends FutureTask<V> implements Comparable<PriorityRunnableFutureTask<V>> {
    private final PriorityRunnable runnable;

    public PriorityRunnableFutureTask(final PriorityRunnable runnable, V result) {
        super(runnable, result);
        this.runnable = runnable;
    }

    @Override
    public int compareTo(@NonNull PriorityRunnableFutureTask<V> o) {
        return runnable.compareTo(o.runnable);
    }
}