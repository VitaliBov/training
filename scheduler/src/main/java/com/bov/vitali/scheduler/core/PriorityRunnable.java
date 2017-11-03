package com.bov.vitali.scheduler.core;

import android.support.annotation.NonNull;

import com.bov.vitali.scheduler.common.Priority;

public interface PriorityRunnable extends Runnable, Comparable<PriorityRunnable> {
    Priority getPriority();

    @Override
    default int compareTo(@NonNull PriorityRunnable o) {
        return this.getPriority().compareTo(o.getPriority());
    }
}