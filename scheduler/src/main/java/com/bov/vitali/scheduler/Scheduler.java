package com.bov.vitali.scheduler;

import android.os.Process;

import com.bov.vitali.scheduler.core.MainThreadExecutor;
import com.bov.vitali.scheduler.core.PriorityThreadFactory;
import com.bov.vitali.scheduler.core.PriorityThreadPoolExecutor;

import java.util.concurrent.Executor;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;

public class Scheduler {
    private static final int NUMBER_OF_CORES = Runtime.getRuntime().availableProcessors();
    private static Scheduler instance;
    private final PriorityThreadPoolExecutor forBackgroundTasksPriority;
    private final Executor mainThreadExecutor;

    public static Scheduler getInstance() {
        if (instance == null) {
            synchronized (Scheduler.class) {
                instance = new Scheduler();
            }
        }
        return instance;
    }

    private Scheduler() {
        ThreadFactory backgroundPriorityThreadFactory = new PriorityThreadFactory(Process.THREAD_PRIORITY_BACKGROUND);
        forBackgroundTasksPriority = new PriorityThreadPoolExecutor(
                NUMBER_OF_CORES * 2,
                NUMBER_OF_CORES * 2,
                60L,
                TimeUnit.SECONDS,
                backgroundPriorityThreadFactory
        );
        mainThreadExecutor = new MainThreadExecutor();
    }

    public PriorityThreadPoolExecutor forPriorityBackgroundTasks() {
        return forBackgroundTasksPriority;
    }

    public Executor forMainThreadTasks() {
        return mainThreadExecutor;
    }
}