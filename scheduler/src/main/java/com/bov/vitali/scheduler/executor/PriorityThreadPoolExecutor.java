package com.bov.vitali.scheduler.executor;

import android.support.annotation.NonNull;

import com.bov.vitali.scheduler.common.Priority;
import com.bov.vitali.scheduler.tasks.RunnableTask;

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
        if (task instanceof RunnableTask) {
            RunnableFutureTask futureTask = new RunnableFutureTask((RunnableTask) task);
            execute(futureTask);
            return futureTask;
        } else {
            return null;
        }
    }

    private static final class RunnableFutureTask extends FutureTask<RunnableTask>
            implements Comparable<RunnableFutureTask> {
        private final RunnableTask priorityRunnable;

        RunnableFutureTask(RunnableTask task) {
            super(task, null);
            this.priorityRunnable = task;
        }

        @Override
        public int compareTo(@NonNull RunnableFutureTask other) {
            Priority p1 = priorityRunnable.getPriority();
            Priority p2 = other.priorityRunnable.getPriority();
            return p2.ordinal() - p1.ordinal();
        }
    }
}