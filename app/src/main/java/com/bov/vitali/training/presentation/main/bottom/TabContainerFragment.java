package com.bov.vitali.training.presentation.main.bottom;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;

import com.bov.vitali.training.R;
import com.bov.vitali.training.common.navigation.BackButtonListener;
import com.bov.vitali.training.common.navigation.LocalCiceroneHolder;
import com.bov.vitali.training.common.navigation.RouterProvider;
import com.bov.vitali.training.common.navigation.Screens;
import com.bov.vitali.training.presentation.main.fragment.PublicationsFragment_;
import com.bov.vitali.training.presentation.main.fragment.UserFragment_;

import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.FragmentArg;

import ru.terrakok.cicerone.Cicerone;
import ru.terrakok.cicerone.Navigator;
import ru.terrakok.cicerone.Router;
import ru.terrakok.cicerone.android.SupportFragmentNavigator;

@EFragment(R.layout.fragment_tab_container)
public class TabContainerFragment extends Fragment implements BackButtonListener, RouterProvider {
    @FragmentArg String screen;
    private Navigator navigator;
    LocalCiceroneHolder ciceroneHolder = new LocalCiceroneHolder();

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setActionBarTitle();
        if (getChildFragmentManager().findFragmentById(R.id.ftc_container) == null) {
            getCicerone().getRouter().replaceScreen(screen);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        getCicerone().getNavigatorHolder().setNavigator(getNavigator());
    }

    @Override
    public void onPause() {
        getCicerone().getNavigatorHolder().removeNavigator();
        super.onPause();
    }

    private Cicerone<Router> getCicerone() {
        return ciceroneHolder.getCicerone(screen);
    }

    private Navigator getNavigator() {
        if (navigator == null) {
            navigator = new SupportFragmentNavigator(getChildFragmentManager(), R.id.ftc_container) {
                @Override
                protected Fragment createFragment(String screenKey, Object data) {
                    switch (screenKey) {
                        case Screens.USER_FRAGMENT:
                            return UserFragment_.builder().build();
                        case Screens.PUBLICATIONS_FRAGMENT:
                            return PublicationsFragment_.builder().build();
                        default:
                            throw new RuntimeException(getResources().getString(R.string.error_unknown_screen));
                    }
                }

                @Override
                protected void showSystemMessage(String message) {

                }

                @Override
                protected void exit() {
                    getActivity().finishAffinity();
                }
            };
        }
        return navigator;
    }

    private void setActionBarTitle() {
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle(screen);
    }

    @Override
    public boolean onBackPressed() {
        Fragment fragment = getChildFragmentManager().findFragmentById(R.id.ftc_container);
        if (fragment != null
                && fragment instanceof BackButtonListener
                && ((BackButtonListener) fragment).onBackPressed()) {
            return true;
        } else {
            getActivity().finishAffinity();
            return true;
        }
    }

    @Override
    public Router getRouter() {
        return getCicerone().getRouter();
    }
}