package com.bov.vitali.scheduler.tasks;

import java.util.concurrent.Callable;

public class CallableTask implements Callable {

    @Override
    public Object call() throws Exception {
        //imitation of work
        return 100*2;
    }
}