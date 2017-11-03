package com.bov.vitali.scheduler;

import android.os.Process;

import com.bov.vitali.scheduler.executor.PriorityThreadPoolExecutor;
import com.bov.vitali.scheduler.factory.PriorityThreadFactory;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.PriorityBlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class Scheduler {
    private static final int NUMBER_OF_CORES = Runtime.getRuntime().availableProcessors();
    private static final int POOL_PRIORITY_BACKGROUND_SIZE = NUMBER_OF_CORES * 2;
    private static final int POOL_BACKGROUND_SIZE = 4;
    private static final int POOL_LOADING_SIZE = 2;
    private static final int POOL_INPUT_OUTPUT_SIZE = 2;
    private static final int POOL_SHOWING_RESULT_SIZE = 4;
    private final PriorityThreadPoolExecutor backgroundPriorityExecutor;
    private final ThreadPoolExecutor backgroundExecutor;
    private final ThreadPoolExecutor loadingExecutor;
    private final ThreadPoolExecutor inputOutputExecutor;
    private final ThreadPoolExecutor showingResultExecutor;

    public static Scheduler getInstance() {
        return SingletonHolder.INSTANCE;
    }

    private Scheduler() {
        ThreadFactory backgroundPriorityThreadFactory = new PriorityThreadFactory(Process.THREAD_PRIORITY_BACKGROUND);
        backgroundPriorityExecutor = new PriorityThreadPoolExecutor(
                POOL_PRIORITY_BACKGROUND_SIZE,
                POOL_PRIORITY_BACKGROUND_SIZE,
                60L,
                TimeUnit.SECONDS,
                new PriorityBlockingQueue<>(),
                backgroundPriorityThreadFactory
        );
        backgroundExecutor = new ThreadPoolExecutor(
                POOL_BACKGROUND_SIZE,
                POOL_BACKGROUND_SIZE,
                60L,
                TimeUnit.SECONDS,
                new LinkedBlockingQueue<>(),
                backgroundPriorityThreadFactory
        );
        loadingExecutor = new ThreadPoolExecutor(
                POOL_LOADING_SIZE,
                POOL_LOADING_SIZE,
                60L,
                TimeUnit.SECONDS,
                new LinkedBlockingQueue<>(),
                backgroundPriorityThreadFactory
        );
        inputOutputExecutor = new ThreadPoolExecutor(
                POOL_INPUT_OUTPUT_SIZE,
                POOL_INPUT_OUTPUT_SIZE,
                60L,
                TimeUnit.SECONDS,
                new LinkedBlockingQueue<>(),
                backgroundPriorityThreadFactory
        );
        showingResultExecutor = new PriorityThreadPoolExecutor(
                POOL_SHOWING_RESULT_SIZE,
                POOL_SHOWING_RESULT_SIZE,
                60L,
                TimeUnit.SECONDS,
                new PriorityBlockingQueue<>()
        );
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

    public ThreadPoolExecutor showingResultTasks() {
        return showingResultExecutor;
    }

    private static final class SingletonHolder {
        private static final Scheduler INSTANCE = new Scheduler();

        private SingletonHolder() {
        }
    }
}