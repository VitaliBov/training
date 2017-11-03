package com.bov.vitali.scheduler.core;

import android.support.annotation.NonNull;

import com.bov.vitali.scheduler.common.Priority;

public abstract class PriorityRunnable implements Runnable, Comparable<PriorityRunnable> {
    private Priority priority = Priority.NORMAL;

    protected PriorityRunnable(Priority priority) {
        this.priority = priority;
    }

    @Override
    public int compareTo(@NonNull PriorityRunnable o) {
        return priority.compareTo(o.priority);
    }
}