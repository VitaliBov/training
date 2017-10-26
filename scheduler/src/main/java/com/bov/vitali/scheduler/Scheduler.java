package com.bov.vitali.scheduler;

import com.bov.vitali.scheduler.core.MainThreadExecutor;
import com.bov.vitali.scheduler.core.PriorityThreadFactory;
import com.bov.vitali.scheduler.core.PriorityThreadPoolExecutor;

import android.os.Process;

import java.util.concurrent.Executor;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class Scheduler {
    private static final int NUMBER_OF_CORES = Runtime.getRuntime().availableProcessors();
    private final ThreadPoolExecutor forBackgroundTasks;
    private final ThreadPoolExecutor forLightWeightBackgroundTasks;
    private final PriorityThreadPoolExecutor forBackgroundTasksPriority;
    private final Executor mainThreadExecutor;
    private static Scheduler instance;

    public static Scheduler getInstance() {
        if (instance == null) {
            synchronized (Scheduler.class) {
                instance = new Scheduler();
            }
        }
        return instance;
    }

    private Scheduler() {
        ThreadFactory backgroundPriorityThreadFactory = new
                PriorityThreadFactory(Process.THREAD_PRIORITY_BACKGROUND);
        forBackgroundTasks = new ThreadPoolExecutor(
                NUMBER_OF_CORES * 2,
                NUMBER_OF_CORES * 2,
                60L,
                TimeUnit.SECONDS,
                new LinkedBlockingQueue<>(),
                backgroundPriorityThreadFactory
        );
        forLightWeightBackgroundTasks = new ThreadPoolExecutor(
                NUMBER_OF_CORES * 2,
                NUMBER_OF_CORES * 2,
                60L,
                TimeUnit.SECONDS,
                new LinkedBlockingQueue<>(),
                backgroundPriorityThreadFactory
        );
        forBackgroundTasksPriority = new PriorityThreadPoolExecutor(
                NUMBER_OF_CORES * 2,
                NUMBER_OF_CORES * 2,
                60L,
                TimeUnit.SECONDS,
                backgroundPriorityThreadFactory
        );
        mainThreadExecutor = new MainThreadExecutor();
    }

    public ThreadPoolExecutor forBackgroundTasks() {
        return forBackgroundTasks;
    }

    public ThreadPoolExecutor forLightWeightBackgroundTasks() {
        return forLightWeightBackgroundTasks;
    }

    public Executor forMainThreadTasks() {
        return mainThreadExecutor;
    }
}