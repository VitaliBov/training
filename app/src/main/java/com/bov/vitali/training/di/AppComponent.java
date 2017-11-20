package com.bov.vitali.training.di;

import com.bov.vitali.training.di.modules.ContextModule;
import com.bov.vitali.training.di.modules.LocalNavigationModule;
import com.bov.vitali.training.di.modules.NavigationModule;
import com.bov.vitali.training.presentation.login.activity.LoginActivity;
import com.bov.vitali.training.presentation.login.presenter.LoginPresenter;
import com.bov.vitali.training.presentation.login.presenter.LoginWebViewPresenter;
import com.bov.vitali.training.presentation.login.presenter.SplashPresenter;
import com.bov.vitali.training.presentation.main.activity.MainActivity;
import com.bov.vitali.training.presentation.main.fragment.ContainerFragment;
import com.bov.vitali.training.presentation.main.presenter.DatabaseListPresenter;
import com.bov.vitali.training.presentation.main.presenter.DatabaseLiveDataPresenter;
import com.bov.vitali.training.presentation.main.presenter.DatabasePresenter;
import com.bov.vitali.training.presentation.main.presenter.MainPresenter;
import com.bov.vitali.training.presentation.main.presenter.OtherPresenter;
import com.bov.vitali.training.presentation.main.presenter.PaginationPresenter;
import com.bov.vitali.training.presentation.main.presenter.PublicationsPresenter;
import com.bov.vitali.training.presentation.main.presenter.SchedulerPresenter;
import com.bov.vitali.training.presentation.main.presenter.UserPresenter;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {
        ContextModule.class,
        NavigationModule.class,
        LocalNavigationModule.class
})
public interface AppComponent {

    void inject(LoginActivity loginActivity);

    void inject(LoginPresenter loginPresenter);

    void inject(LoginWebViewPresenter loginWebViewPresenter);

    void inject(SplashPresenter splashPresenter);

    void inject(MainActivity mainActivity);

    void inject(MainPresenter mainPresenter);

    void inject(ContainerFragment containerFragment);

    void inject(UserPresenter userPresenter);

    void inject(PublicationsPresenter publicationsPresenter);

    void inject(PaginationPresenter paginationPresenter);

    void inject(OtherPresenter otherPresenter);

    void inject(SchedulerPresenter schedulerPresenter);

    void inject(DatabasePresenter databasePresenter);

    void inject(DatabaseListPresenter databaseListPresenter);

    void inject(DatabaseLiveDataPresenter databaseLiveDataPresenter);
}