package com.bov.vitali.scheduler.executor;

import android.support.annotation.NonNull;

import com.bov.vitali.scheduler.common.Priority;
import com.bov.vitali.scheduler.runnable.PriorityRunnable;

import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;
import java.util.concurrent.PriorityBlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class PriorityThreadPoolExecutor extends ThreadPoolExecutor {

    public PriorityThreadPoolExecutor(int corePoolSize,
                                      int maximumPoolSize,
                                      long keepAliveTime,
                                      TimeUnit unit,
                                      ThreadFactory threadFactory) {
        super(corePoolSize, maximumPoolSize, keepAliveTime, unit, new PriorityBlockingQueue<>(), threadFactory);
    }

    @NonNull
    @Override
    public Future<?> submit(Runnable task) {
        PriorityFutureTask futureTask = new PriorityFutureTask((PriorityRunnable) task);
        execute(futureTask);
        return futureTask;
    }

    private static final class PriorityFutureTask extends FutureTask<PriorityRunnable>
            implements Comparable<PriorityFutureTask> {
        private final PriorityRunnable priorityRunnable;

        PriorityFutureTask(PriorityRunnable priorityRunnable) {
            super(priorityRunnable, null);
            this.priorityRunnable = priorityRunnable;
        }

        @Override
        public int compareTo(@NonNull PriorityFutureTask other) {
            Priority p1 = priorityRunnable.getPriority();
            Priority p2 = other.priorityRunnable.getPriority();
            return p2.ordinal() - p1.ordinal();
        }
    }
}