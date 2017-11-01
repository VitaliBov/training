package com.bov.vitali.scheduler;

import android.os.Process;

import com.bov.vitali.scheduler.executor.MainThreadExecutor;
import com.bov.vitali.scheduler.executor.PriorityThreadPoolExecutor;
import com.bov.vitali.scheduler.factory.PriorityThreadFactory;

import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class Scheduler {
    private static final int NUMBER_OF_CORES = Runtime.getRuntime().availableProcessors();
    private static final int MAXIMUM_POOL_BACKGROUND_SIZE = 4;
    private static final int MAXIMUM_POOL_LOADING_SIZE = 4;
    private static final int MAXIMUM_POOL_INPUT_OUTPUT_SIZE = 2;
    private static final int MAXIMUM_POOL_SHOWING_RESULT_SIZE = 4;
    private static Scheduler instance;
    private final PriorityThreadPoolExecutor backgroundPriorityExecutor;
    private final ThreadPoolExecutor backgroundExecutor;
    private final ThreadPoolExecutor loadingExecutor;
    private final ThreadPoolExecutor inputOutputExecutor;
    private final ExecutorService showingResultExecutor;
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
        backgroundPriorityExecutor = new PriorityThreadPoolExecutor(
                NUMBER_OF_CORES * 2,
                NUMBER_OF_CORES * 2,
                60L,
                TimeUnit.SECONDS,
                backgroundPriorityThreadFactory
        );
        backgroundExecutor = new ThreadPoolExecutor(
                MAXIMUM_POOL_BACKGROUND_SIZE,
                MAXIMUM_POOL_BACKGROUND_SIZE,
                60L,
                TimeUnit.SECONDS,
                new LinkedBlockingQueue<>(),
                backgroundPriorityThreadFactory
        );
        loadingExecutor = new ThreadPoolExecutor(
                MAXIMUM_POOL_LOADING_SIZE,
                MAXIMUM_POOL_LOADING_SIZE,
                60L,
                TimeUnit.SECONDS,
                new LinkedBlockingQueue<>(),
                backgroundPriorityThreadFactory
        );
        inputOutputExecutor = new ThreadPoolExecutor(
                MAXIMUM_POOL_INPUT_OUTPUT_SIZE,
                MAXIMUM_POOL_INPUT_OUTPUT_SIZE,
                60L,
                TimeUnit.SECONDS,
                new LinkedBlockingQueue<>(),
                backgroundPriorityThreadFactory
        );
        showingResultExecutor = Executors.newFixedThreadPool(MAXIMUM_POOL_SHOWING_RESULT_SIZE);
        mainThreadExecutor = new MainThreadExecutor();
    }

    public ThreadPoolExecutor backgroundPriorityTasks() {
        return backgroundPriorityExecutor;
    }

    public ThreadPoolExecutor backgroundTasks() {
        return backgroundExecutor;
    }

    public ThreadPoolExecutor loadingTasks() {
        return loadingExecutor;
    }

    public ThreadPoolExecutor inputOutputTasks() {
        return inputOutputExecutor;
    }

    public ExecutorService showingResultThreadTasks() {
        return showingResultExecutor;
    }

    public Executor mainThreadTasks() {
        return mainThreadExecutor;
    }
}