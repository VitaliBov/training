package com.bov.vitali.scheduler;

import android.os.Process;

import com.bov.vitali.scheduler.executor.MainThreadExecutor;
import com.bov.vitali.scheduler.executor.PriorityThreadPoolExecutor;
import com.bov.vitali.scheduler.factory.PriorityThreadFactory;

import java.util.concurrent.Executor;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class Scheduler {
    private static final int NUMBER_OF_CORES = Runtime.getRuntime().availableProcessors();
    private static final int MAXIMUM_POOL_BACKGROUND_SIZE = 4;
    private static final int MAXIMUM_POOL_LOADING_SIZE = 4;
    private static final int MAXIMUM_POOL_INPUT_OUTPUT_SIZE = 2;
    private static Scheduler instance;
    private final PriorityThreadPoolExecutor backgroundPriorityTasks;
    private final ThreadPoolExecutor backgroundTasks;
    private final ThreadPoolExecutor loadingTasks;
    private final ThreadPoolExecutor inputOutputTasks;
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
        backgroundPriorityTasks = new PriorityThreadPoolExecutor(
                NUMBER_OF_CORES * 2,
                NUMBER_OF_CORES * 2,
                60L,
                TimeUnit.SECONDS,
                backgroundPriorityThreadFactory
        );
        backgroundTasks = new ThreadPoolExecutor(
                MAXIMUM_POOL_BACKGROUND_SIZE,
                MAXIMUM_POOL_BACKGROUND_SIZE,
                60L,
                TimeUnit.SECONDS,
                new LinkedBlockingQueue<>(),
                backgroundPriorityThreadFactory
        );
        loadingTasks = new ThreadPoolExecutor(
                MAXIMUM_POOL_LOADING_SIZE,
                MAXIMUM_POOL_LOADING_SIZE,
                60L,
                TimeUnit.SECONDS,
                new LinkedBlockingQueue<>(),
                backgroundPriorityThreadFactory
        );
        inputOutputTasks = new ThreadPoolExecutor(
                MAXIMUM_POOL_INPUT_OUTPUT_SIZE,
                MAXIMUM_POOL_INPUT_OUTPUT_SIZE,
                60L,
                TimeUnit.SECONDS,
                new LinkedBlockingQueue<>(),
                backgroundPriorityThreadFactory
        );
        mainThreadExecutor = new MainThreadExecutor();
    }

    public ThreadPoolExecutor forPriorityBackgroundTasks() {
        return backgroundPriorityTasks;
    }

    public ThreadPoolExecutor forBackgroundTasks() {
        return backgroundTasks;
    }

    public ThreadPoolExecutor forLoadingTasks() {
        return loadingTasks;
    }

    public ThreadPoolExecutor forInputOutputTasks() {
        return inputOutputTasks;
    }

    public Executor forMainThreadTasks() {
        return mainThreadExecutor;
    }

    public void stopThread() {

    }
}