package com.bov.vitali.training.presentation.main.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.arellomobile.mvp.presenter.InjectPresenter;
import com.bov.vitali.scheduler.Scheduler;
import com.bov.vitali.scheduler.common.Priority;
import com.bov.vitali.scheduler.runnable.PriorityRunnable;
import com.bov.vitali.training.R;
import com.bov.vitali.training.common.navigation.BackButtonListener;
import com.bov.vitali.training.common.utils.AndroidUtils;
import com.bov.vitali.training.presentation.base.fragment.BaseFragment;
import com.bov.vitali.training.presentation.main.presenter.SchedulerPresenter;
import com.bov.vitali.training.presentation.main.view.SchedulerContract;

import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EFragment;

@EFragment(R.layout.fragment_scheduler)
public class SchedulerFragment extends BaseFragment<SchedulerPresenter, SchedulerContract.View>
        implements SchedulerContract.View, BackButtonListener {
    @InjectPresenter SchedulerPresenter presenter;
    private Handler handler;
    private Message message;
    private static final int STATUS_SUCCESS = 0; // success
    private static final int STATUS_FAILURE = 1; // failure

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
        Scheduler.getInstance().forPriorityBackgroundTasks().submit(new PriorityRunnable(Priority.LOW) {
            @Override
            public void run() {
                add();
                Log.i("MyTag", "run: 1 LOW");
            }
        });
        Scheduler.getInstance().forPriorityBackgroundTasks().submit(new PriorityRunnable(Priority.LOW) {
            @Override
            public void run() {
                add();
                Log.i("MyTag", "run: 2 LOW");
            }
        });
        Scheduler.getInstance().forPriorityBackgroundTasks().submit(new PriorityRunnable(Priority.IMMEDIATE) {
            @Override
            public void run() {
                Thread.currentThread().interrupt();
                if (Thread.currentThread().isInterrupted()) {
                    return;
                }

                add();
                int x = 100 + 5;
                message = handler.obtainMessage(STATUS_SUCCESS, x, 6);
                handler.sendMessage(message);
                Log.i("MyTag", "run: 3 IMMEDIATE");
            }
        });

        Scheduler.getInstance().forPriorityBackgroundTasks().submit(new PriorityRunnable(Priority.HIGH) {
            @Override
            public void run() {
                add();
                Log.i("MyTag", "run: 4 HIGH");
            }
        });
        Scheduler.getInstance().forPriorityBackgroundTasks().submit(new PriorityRunnable(Priority.NORMAL) {
            @Override
            public void run() {
                add();
                Log.i("MyTag", "run: 5 NORMAL");
            }
        });
        Scheduler.getInstance().forPriorityBackgroundTasks().submit(new PriorityRunnable(Priority.IMMEDIATE) {
            @Override
            public void run() {
                add();
                Log.i("MyTag", "run: 6 IMMEDIATE");
            }
        });
        Scheduler.getInstance().forPriorityBackgroundTasks().submit(new PriorityRunnable(Priority.LOW) {
            @Override
            public void run() {
                add();
                Log.i("MyTag", "run: 7 LOW");
            }
        });
        Scheduler.getInstance().forPriorityBackgroundTasks().submit(new PriorityRunnable(Priority.LOW) {
            @Override
            public void run() {
                add();
                Log.i("MyTag", "run: 8 LOW");
            }
        });
        Scheduler.getInstance().forPriorityBackgroundTasks().submit(new PriorityRunnable(Priority.IMMEDIATE) {
            @Override
            public void run() {
                add();
                Log.i("MyTag", "run: 9 IMMEDIATE");
            }
        });
        Scheduler.getInstance().forPriorityBackgroundTasks().submit(new PriorityRunnable(Priority.HIGH) {
            @Override
            public void run() {
                add();
                Log.i("MyTag", "run: 10 HIGH");
            }
        });
        Scheduler.getInstance().forPriorityBackgroundTasks().submit(new PriorityRunnable(Priority.NORMAL) {
            @Override
            public void run() {
                add();
                Log.i("MyTag", "run: 11 NORMAL");
            }
        });
        Scheduler.getInstance().forPriorityBackgroundTasks().submit(new PriorityRunnable(Priority.IMMEDIATE) {
            @Override
            public void run() {
                add();
                Log.i("MyTag", "run: 12 IMMEDIATE");
            }
        });
    }

    private void add() {
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