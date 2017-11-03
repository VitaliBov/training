package com.bov.vitali.scheduler.executor;

import com.bov.vitali.scheduler.core.PriorityCallable;
import com.bov.vitali.scheduler.core.PriorityCallableFutureTask;
import com.bov.vitali.scheduler.core.PriorityRunnable;
import com.bov.vitali.scheduler.core.PriorityRunnableFutureTask;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Callable;
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

    @Override
    protected <T> RunnableFuture<T> newTaskFor(Runnable runnable, T value) {
        if (runnable instanceof PriorityRunnable) {
            return new PriorityRunnableFutureTask<>((PriorityRunnable) runnable, value);
        } else {
            return super.newTaskFor(runnable, value);
        }
    }

    @Override
    protected <T> RunnableFuture<T> newTaskFor(Callable<T> callable) {
        if (callable instanceof PriorityCallable) {
            return new PriorityCallableFutureTask<>((PriorityCallable<T>) callable);
        } else {
            return super.newTaskFor(callable);
        }
    }
}