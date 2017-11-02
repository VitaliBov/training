package com.bov.vitali.scheduler.core;

import android.support.annotation.NonNull;

public interface PriorityRunnable extends Runnable, Comparable<PriorityRunnable> {
    int getPriority();

    @Override
    default int compareTo(@NonNull PriorityRunnable o) {
        return Integer.valueOf(getPriority()).compareTo(o.getPriority());
    }
}