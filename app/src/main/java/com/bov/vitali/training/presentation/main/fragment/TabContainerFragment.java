package com.bov.vitali.training.presentation.main.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;

import com.bov.vitali.training.App;
import com.bov.vitali.training.R;
import com.bov.vitali.training.common.navigation.BackButtonListener;
import com.bov.vitali.training.common.navigation.LocalCiceroneHolder;
import com.bov.vitali.training.common.navigation.Screens;
import com.bov.vitali.training.presentation.base.fragment.BaseNavigationFragment;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;

import ru.terrakok.cicerone.Cicerone;
import ru.terrakok.cicerone.Navigator;
import ru.terrakok.cicerone.Router;
import ru.terrakok.cicerone.android.SupportFragmentNavigator;

@EFragment(R.layout.fragment_tab_container)
public class TabContainerFragment extends BaseNavigationFragment implements BackButtonListener {
    private Navigator navigator;
    private LocalCiceroneHolder ciceroneHolder = new LocalCiceroneHolder();
    @ViewById TabLayout tabLayout;

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (getChildFragmentManager().findFragmentById(R.id.fragment_tab_container) == null) {
            getCicerone().getRouter().replaceScreen(Screens.SCHEDULER_FRAGMENT);
        }
    }

    @AfterViews
    public void afterView() {
        setupTabLayout();
        bindTabs();
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

    private void setupTabLayout() {
        tabLayout.addTab(tabLayout.newTab().setText(Screens.SCHEDULER_FRAGMENT));
        tabLayout.addTab(tabLayout.newTab().setText(Screens.DATABASE_FRAGMENT));
    }

    private void bindTabs() {
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                setCurrentTabFragment(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

    private void setCurrentTabFragment(int tabPosition)
    {
        switch (tabPosition)
        {
            case 0 :
                App.INSTANCE.getRouter().navigateTo(Screens.SCHEDULER_FRAGMENT);
                break;
            case 1 :
                App.INSTANCE.getRouter().navigateTo(Screens.DATABASE_FRAGMENT);
                break;
        }
    }

    private Cicerone<Router> getCicerone() {
        return ciceroneHolder.getCicerone(Screens.TAB_CONTAINER_FRAGMENT);
    }

    private Navigator getNavigator() {
        if (navigator == null) {
            navigator = new SupportFragmentNavigator(getChildFragmentManager(), R.id.fragment_tab_container) {
                @Override
                protected Fragment createFragment(String screenKey, Object data) {
                    switch (screenKey) {
                        case Screens.SCHEDULER_FRAGMENT:
                            return SchedulerFragment_.builder().build();
                        case Screens.DATABASE_FRAGMENT:
                            return SchedulerFragment_.builder().build();
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

    @Override
    public boolean onBackPressed() {
        Fragment fragment = getChildFragmentManager().findFragmentById(R.id.fragment_tab_container);
        if (fragment != null
                && fragment instanceof BackButtonListener
                && ((BackButtonListener) fragment).onBackPressed()) {
            return true;
        } else {
            getActivity().finishAffinity();
            return true;
        }
    }
}