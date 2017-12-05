package com.bov.vitali.training.presentation.login.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;

import com.bov.vitali.training.App;
import com.bov.vitali.training.R;
import com.bov.vitali.training.presentation.main.activity.MainActivity_;
import com.bov.vitali.training.presentation.navigation.BackButtonListener;
import com.bov.vitali.training.presentation.navigation.Screens;
import com.bov.vitali.training.presentation.base.activity.BaseNavigationActivity;
import com.bov.vitali.training.presentation.login.fragment.LoginFragment_;
import com.bov.vitali.training.presentation.login.fragment.LoginWebViewFragment_;
import com.bov.vitali.training.presentation.login.fragment.SplashFragment_;

import org.androidannotations.annotations.EActivity;

import javax.inject.Inject;

import ru.terrakok.cicerone.Navigator;
import ru.terrakok.cicerone.NavigatorHolder;
import ru.terrakok.cicerone.Router;
import ru.terrakok.cicerone.android.SupportAppNavigator;

@EActivity(R.layout.activity_login)
public class LoginActivity extends BaseNavigationActivity {
    @Inject Router router;
    @Inject NavigatorHolder navigatorHolder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        App.instance.getAppComponent().inject(this);
        super.onCreate(savedInstanceState);
        if (savedInstanceState == null) {
            router.replaceScreen(Screens.SPLASH_FRAGMENT);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        navigatorHolder.setNavigator(navigator);
    }

    @Override
    protected void onPause() {
        super.onPause();
        navigatorHolder.removeNavigator();
    }

    private Navigator navigator = new SupportAppNavigator(this, getSupportFragmentManager(), R.id.login_container) {
        @Override
        protected Intent createActivityIntent(String screenKey, Object data) {
            if (screenKey.equals(Screens.MAIN_ACTIVITY)) {
                return MainActivity_.intent(App.appContext()).get();
            }
            return null;
        }

        @Override
        protected Fragment createFragment(String screenKey, Object data) {
            switch (screenKey) {
                case Screens.SPLASH_FRAGMENT:
                    return SplashFragment_.builder().build();
                case Screens.LOGIN_FRAGMENT:
                    return LoginFragment_.builder().build();
                case Screens.LOGIN_WEB_VIEW_FRAGMENT:
                    return LoginWebViewFragment_.builder().build();
                default:
                    throw new RuntimeException(getString(R.string.navigation_error_unknown_screen));
            }
        }

        @Override
        protected void exit() {
            finish();
        }
    };

    @Override
    public void onBackPressed() {
        Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.login_container);
        if (fragment != null
                && fragment instanceof BackButtonListener
                && ((BackButtonListener) fragment).onBackPressed()) {
            return;
        } else {
            router.exit();
        }
    }
}