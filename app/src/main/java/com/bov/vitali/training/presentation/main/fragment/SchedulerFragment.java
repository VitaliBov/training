package com.bov.vitali.training.presentation.main.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.arellomobile.mvp.presenter.InjectPresenter;
import com.bov.vitali.scheduler.Scheduler;
import com.bov.vitali.scheduler.common.Priority;
import com.bov.vitali.scheduler.tasks.CallableTask;
import com.bov.vitali.scheduler.tasks.RunnableTask;
import com.bov.vitali.training.R;
import com.bov.vitali.training.common.navigation.BackButtonListener;
import com.bov.vitali.training.common.utils.AndroidUtils;
import com.bov.vitali.training.presentation.base.fragment.BaseFragment;
import com.bov.vitali.training.presentation.main.presenter.SchedulerPresenter;
import com.bov.vitali.training.presentation.main.view.SchedulerContract;

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
    private Message message;
    private Future future;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        handler = new Handler(new IncomingHandlerCallback());
    }

    @Click({R.id.btnStartScheduler})
    void onClick() {
        doSomething();
    }

    private void doSomething() {
        Scheduler.getInstance().backgroundPriorityTasks().submit(new RunnableTask(Priority.LOW) {
            @Override
            public void run() {
                threadExecution();
                Log.i("MyTag", "run: 1 LOW");
            }
        });
        //stop task execution
        Scheduler.getInstance().backgroundPriorityTasks().submit(new RunnableTask(Priority.LOW) {
            @Override
            public void run() {
                Thread.currentThread().interrupt();
                if (Thread.currentThread().isInterrupted()) {
                    return;
                }
                threadExecution();
                Log.i("MyTag", "run: 2 LOW");
            }
        });
        //data transfer to the UI thread
        Scheduler.getInstance().backgroundPriorityTasks().submit(new RunnableTask(Priority.IMMEDIATE) {
            @Override
            public void run() {
                threadExecution();
                int x = 100 + 5;
                message = handler.obtainMessage(STATUS_SUCCESS, x, 6);
                handler.sendMessage(message);
                Log.i("MyTag", "run: 3 IMMEDIATE");
            }
        });
        //return result
        future = Scheduler.getInstance().showingResultThreadTasks().submit(new CallableTask() {
            @Override
            public Object call() throws Exception {
                Log.i("MyTag", "call: 4");
                return super.call();
            }
        }
        );
        Scheduler.getInstance().backgroundPriorityTasks().submit(new RunnableTask(Priority.NORMAL) {
            @Override
            public void run() {
                threadExecution();
                Log.i("MyTag", "run: 5 NORMAL");
            }
        });
    }

    private void threadExecution() {
        try {
            Thread.sleep(1000);
        } catch (Throwable t) {
            t.printStackTrace();
        }
    }

    private class IncomingHandlerCallback implements Handler.Callback {
        @Override
        public boolean handleMessage(Message message) {
            switch (message.what) {
                case STATUS_SUCCESS:
                    AndroidUtils.toast(getActivity(), "Success " + message.arg1);
                    break;
                case STATUS_FAILURE:
                    AndroidUtils.toast(getActivity(), "Failure " + message.arg1);
                    break;
            }
            return true;
        }
    }
}