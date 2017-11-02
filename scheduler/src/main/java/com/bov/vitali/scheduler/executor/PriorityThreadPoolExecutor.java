package com.bov.vitali.scheduler.executor;

import android.support.annotation.NonNull;

import com.bov.vitali.scheduler.core.PriorityCallable;
import com.bov.vitali.scheduler.core.PriorityCallableFutureTask;
import com.bov.vitali.scheduler.core.PriorityRunnable;
import com.bov.vitali.scheduler.core.PriorityRunnableFutureTask;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Callable;
import java.util.concurrent.Future;
import java.util.concurrent.RunnableFuture;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class PriorityThreadPoolExecutor extends ThreadPoolExecutor {

    public PriorityThreadPoolExecutor(int corePoolSize,
                                      int maximumPoolSize,
                                      long keepAliveTime,
                                      TimeUnit unit,
                                      BlockingQueue<Runnable> queue,
                                      ThreadFactory threadFactory) {
        super(corePoolSize, maximumPoolSize, keepAliveTime, unit, queue, threadFactory);
    }

    public PriorityThreadPoolExecutor(int corePoolSize,
                                      int maximumPoolSize,
                                      long keepAliveTime,
                                      TimeUnit unit,
                                      BlockingQueue<Runnable> queue) {
        super(corePoolSize, maximumPoolSize, keepAliveTime, unit, queue);
    }

    @NonNull
    @Override
    public Future<?> submit(Runnable task) {
        if (task instanceof PriorityRunnable) {
            RunnableFuture<?> futureTask = newTaskFor(task, null);
            execute(futureTask);
            return futureTask;
        } else {
            return super.submit(task);
        }
    }

    @NonNull
    @Override
    public <T> Future<T> submit(Runnable task, T result) {
        if (task instanceof PriorityRunnable) {
            RunnableFuture<T> futureTask = newTaskFor(task, result);
            execute(futureTask);
            return futureTask;
        } else {
            return super.submit(task, result);
        }
    }

    @NonNull
    @Override
    public <T> Future<T> submit(Callable<T> task) {
        if (task instanceof PriorityCallable) {
            RunnableFuture<T> futureTask = newTaskFor(task);
            execute(futureTask);
            return futureTask;
        } else {
            return super.submit(task);
        }
    }

    @Override
    protected <T> RunnableFuture<T> newTaskFor(Runnable runnable, T value) {
        return new PriorityRunnableFutureTask<>((PriorityRunnable) runnable, value);
    }

    @Override
    protected <T> RunnableFuture<T> newTaskFor(Callable<T> callable) {
        return new PriorityCallableFutureTask<>((PriorityCallable<T>) callable);
    }
}