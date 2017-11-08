package com.bov.vitali.training.presentation.main.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;

import com.bov.vitali.training.R;
import com.bov.vitali.training.common.navigation.BackButtonListener;
import com.bov.vitali.training.common.navigation.LocalCiceroneHolder;
import com.bov.vitali.training.common.navigation.RouterProvider;
import com.bov.vitali.training.common.navigation.Screens;
import com.bov.vitali.training.presentation.base.fragment.BaseNavigationFragment;

import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.FragmentArg;

import ru.terrakok.cicerone.Cicerone;
import ru.terrakok.cicerone.Navigator;
import ru.terrakok.cicerone.Router;
import ru.terrakok.cicerone.android.SupportFragmentNavigator;

@EFragment(R.layout.fragment_bottom_container)
public class BottomContainerFragment extends BaseNavigationFragment implements BackButtonListener, RouterProvider {
    @FragmentArg String screen;
    private Navigator navigator;
    private LocalCiceroneHolder ciceroneHolder = new LocalCiceroneHolder();

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setActionBarTitle();
        if (getChildFragmentManager().findFragmentById(R.id.fragment_bottom_container) == null) {
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
            navigator = new SupportFragmentNavigator(getChildFragmentManager(), R.id.fragment_bottom_container) {
                @Override
                protected Fragment createFragment(String screenKey, Object data) {
                    switch (screenKey) {
                        case Screens.USER_FRAGMENT:
                            return UserFragment_.builder().build();
                        case Screens.PUBLICATIONS_FRAGMENT:
                            return PublicationsFragment_.builder().build();
                        case Screens.PAGINATION_FRAGMENT:
                            return PaginationFragment_.builder().build();
                        case Screens.TAB_CONTAINER_FRAGMENT:
                            return TabContainerFragment_.builder().build();
                        default:
                            throw new RuntimeException(getResources().getString(R.string.navigation_error_unknown_screen));
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
        Fragment fragment = getChildFragmentManager().findFragmentById(R.id.fragment_bottom_container);
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