package com.bov.vitali.scheduler.tasks;

import com.bov.vitali.scheduler.common.Priority;

public abstract class RunnableTask implements Runnable {
    private final Priority priority;

    protected RunnableTask(Priority priority) {
        this.priority = priority;
    }

    public Priority getPriority() {
        return priority;
    }
}