package com.bov.vitali.training.presentation.main.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.arellomobile.mvp.presenter.InjectPresenter;
import com.arellomobile.mvp.presenter.ProvidePresenter;
import com.bov.vitali.scheduler.common.Priority;
import com.bov.vitali.scheduler.core.PriorityCallable;
import com.bov.vitali.scheduler.core.PriorityRunnable;
import com.bov.vitali.training.App;
import com.bov.vitali.training.R;
import com.bov.vitali.training.presentation.navigation.BackButtonListener;
import com.bov.vitali.training.common.utils.AndroidUtils;
import com.bov.vitali.training.presentation.base.fragment.BaseFragment;
import com.bov.vitali.training.presentation.main.presenter.SchedulerPresenter;
import com.bov.vitali.training.presentation.main.view.SchedulerContract;
import com.bov.vitali.training.presentation.navigation.RouterProvider;

import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EFragment;

import java.util.concurrent.Future;

@EFragment(R.layout.fragment_scheduler)
public class SchedulerFragment extends BaseFragment<SchedulerPresenter, SchedulerContract.View>
        implements SchedulerContract.View, BackButtonListener {
    private static final int STATUS_SUCCESS = 0;
    private static final int STATUS_FAILURE = 1;
    @InjectPresenter SchedulerPresenter presenter;
    private Handler handler;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        handler = new Handler(new IncomingHandlerCallback());
    }

    @ProvidePresenter
    SchedulerPresenter provideSchedulerPresenter() {
        return new SchedulerPresenter(((RouterProvider) getParentFragment()).getRouter());
    }

    @Click({R.id.btnStartScheduler})
    void onClick() {
        doSomething();
    }

    private void doSomething() {
        //using Runnable
        App.getScheduler().backgroundPriorityTasks().submit(priorityRunnable0);
        App.getScheduler().backgroundPriorityTasks().submit(priorityRunnable1);
        App.getScheduler().backgroundPriorityTasks().submit(stopRunnable);
        App.getScheduler().backgroundPriorityTasks().submit(toUiRunnable);
        App.getScheduler().backgroundTasks().submit(runnable);
        //return result by Callable
        final Future<Integer> future1 = App.getScheduler().showingResultTasks().submit(priorityCallable0);
        final Future<Integer> future2 = App.getScheduler().showingResultTasks().submit(priorityCallable1);
    }

    PriorityRunnable priorityRunnable0 = new PriorityRunnable(Priority.HIGH) {
        @Override
        public void run() {
            threadExecution();
            Log.i("MyTag", "run: HIGH");
        }
    };
    PriorityRunnable priorityRunnable1 = new PriorityRunnable(Priority.LOW) {
        @Override
        public void run() {
            threadExecution();
            Log.i("MyTag", "run: LOW");
        }
    };

    //stop task execution
    PriorityRunnable stopRunnable = new PriorityRunnable(Priority.LOW) {
        @Override
        public void run() {
            Thread.currentThread().interrupt();
            if (Thread.currentThread().isInterrupted()) {
                return;
            }
            threadExecution();
            Log.i("MyTag", "run: stop");
        }
    };

    //data transfer to the UI thread
    PriorityRunnable toUiRunnable = new PriorityRunnable(Priority.NORMAL) {
        @Override
        public void run() {
            threadExecution();
            int x = 100 + 5;
            Message message = handler.obtainMessage(STATUS_SUCCESS, x, 6);
            handler.sendMessage(message);
            Log.i("MyTag", "run: to the UI");
        }
    };

    //without priority
    Runnable runnable = () -> {
        threadExecution();
        Log.i("MyTag", "run: without getPriority");
    };

    PriorityCallable<Integer> priorityCallable0 = new PriorityCallable<Integer>(Priority.IMMEDIATE) {
        @Override
        public Integer call() throws Exception {
            threadExecution();
            Log.i("MyTag", "call: IMMEDIATE");
            return 0;
        }
    };
    PriorityCallable<Integer> priorityCallable1 = new PriorityCallable<Integer>(Priority.NORMAL) {
        @Override
        public Integer call() throws Exception {
            threadExecution();
            Log.i("MyTag", "call: NORMAL");
            return 1;
        }
    };

    private void threadExecution() {
        try {
            Thread.sleep(1000);
        } catch (Throwable t) {
            t.printStackTrace();
        }
    }

    @Override
    public boolean onBackPressed() {
        presenter.onBackPressed();
        return true;
    }

    private class IncomingHandlerCallback implements Handler.Callback {
        @Override
        public boolean handleMessage(Message message) {
            switch (message.what) {
                case STATUS_SUCCESS:
                    toast("Success " + message.arg1);
                    break;
                case STATUS_FAILURE:
                    toast("Failure " + message.arg1);
                    break;
            }
            return true;
        }
    }
}