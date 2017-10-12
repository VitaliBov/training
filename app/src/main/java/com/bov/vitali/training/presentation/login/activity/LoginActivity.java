package com.bov.vitali.training.presentation.login.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;

import com.bov.vitali.training.App;
import com.bov.vitali.training.R;
import com.bov.vitali.training.common.navigation.BackButtonListener;
import com.bov.vitali.training.common.navigation.Screens;
import com.bov.vitali.training.presentation.base.activity.BaseActivity;
import com.bov.vitali.training.presentation.login.fragment.LoginFragment_;
import com.bov.vitali.training.presentation.login.fragment.LoginWebViewFragment_;
import com.bov.vitali.training.presentation.login.fragment.SplashFragment_;
import com.bov.vitali.training.presentation.main.bottom.BottomNavigationActivity_;

import org.androidannotations.annotations.EActivity;

import ru.terrakok.cicerone.Navigator;
import ru.terrakok.cicerone.android.SupportAppNavigator;

@EActivity(R.layout.activity_login)
public class LoginActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState == null) {
            App.INSTANCE.getRouter().replaceScreen(Screens.SPLASH_FRAGMENT);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        App.INSTANCE.getNavigatorHolder().setNavigator(navigator);
    }

    private Navigator navigator = new SupportAppNavigator(this, getSupportFragmentManager(), R.id.login_container) {
        @Override
        protected Intent createActivityIntent(String screenKey, Object data) {
            if (screenKey.equals(Screens.BOTTOM_NAVIGATION_ACTIVITY)) {
                return new Intent(getApplication(), BottomNavigationActivity_.class);
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
                    throw new RuntimeException(getString(R.string.error_unknown_screen));
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
            super.onBackPressed();
        }
    }
}