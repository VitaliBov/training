package com.bov.vitali.training.presentation.main.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.Toolbar;

import com.arellomobile.mvp.presenter.InjectPresenter;
import com.ashokvarma.bottomnavigation.BottomNavigationBar;
import com.ashokvarma.bottomnavigation.BottomNavigationItem;
import com.bov.vitali.training.App;
import com.bov.vitali.training.R;
import com.bov.vitali.training.common.utils.AndroidUtils;
import com.bov.vitali.training.common.utils.PasswordUtils;
import com.bov.vitali.training.presentation.base.activity.BaseNavigationActivity;
import com.bov.vitali.training.presentation.main.fragment.ContainerFragment;
import com.bov.vitali.training.presentation.main.fragment.ContainerFragment_;
import com.bov.vitali.training.presentation.main.presenter.MainPresenter;
import com.bov.vitali.training.presentation.main.view.MainContract;
import com.bov.vitali.training.presentation.navigation.BackButtonListener;
import com.bov.vitali.training.presentation.navigation.RouterProvider;
import com.bov.vitali.training.presentation.navigation.Screens;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

import javax.inject.Inject;

import ru.terrakok.cicerone.Navigator;
import ru.terrakok.cicerone.NavigatorHolder;
import ru.terrakok.cicerone.Router;
import ru.terrakok.cicerone.commands.Back;
import ru.terrakok.cicerone.commands.Command;
import ru.terrakok.cicerone.commands.Replace;
import ru.terrakok.cicerone.commands.SystemMessage;

@EActivity(R.layout.activity_main)
public class MainActivity extends BaseNavigationActivity<MainPresenter, MainContract.View>
        implements MainContract.View, RouterProvider {
    @InjectPresenter MainPresenter presenter;
    @Inject Router router;
    @Inject NavigatorHolder navigatorHolder;
    @ViewById(R.id.bottom_navigation_bar) BottomNavigationBar bottomNavigationBar;
    @ViewById(R.id.activity_main_toolbar) Toolbar toolbar;
    private ContainerFragment userFragment;
    private ContainerFragment publicationsFragment;
    private ContainerFragment paginationFragment;
    private ContainerFragment tabContainerFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        App.instance.getAppComponent().inject(this);
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void onResumeFragments() {
        super.onResumeFragments();
        navigatorHolder.setNavigator(navigator);
    }

    @Override
    protected void onResume() {
        super.onResume();
        PasswordUtils.lockAppCheck();
    }

    @Override
    protected void onPause() {
        super.onPause();
        navigatorHolder.removeNavigator();
    }

    @Override
    protected void onStop() {
        super.onStop();
        PasswordUtils.lockAppStoreTime();
    }

    @AfterViews
    public void afterViews() {
        setSupportActionBar(toolbar);
        initBottomNavigationBar();
        initContainers();
        selectStartTab();
    }

    @Override
    public void initBottomNavigationBar() {
        bottomNavigationBar.setAutoHideEnabled(false);
        bottomNavigationBar.setActiveColor(R.color.colorPrimary);
        bottomNavigationBar
                .addItem(new BottomNavigationItem(R.drawable.ic_perm_identity_black_24px, R.string.bottom_bar_tab_user))
                .addItem(new BottomNavigationItem(R.drawable.ic_people_outline_black_24px, R.string.bottom_bar_tab_publications))
                .addItem(new BottomNavigationItem(R.drawable.ic_list_black_24px, R.string.bottom_bar_tab_pagination))
                .addItem(new BottomNavigationItem(R.drawable.ic_more_horiz_black_24px, R.string.bottom_bar_tab_view_pager))
                .initialise();
        bottomNavigationBar.setTabSelectedListener(new BottomNavigationBar.OnTabSelectedListener() {
            @Override
            public void onTabSelected(int position) {
                switch (position) {
                    case USER_TAB_POSITION:
                        presenter.onTabUserClick();
                        break;
                    case PUBLICATION_TAB_POSITION:
                        presenter.onTabPublicationsClick();
                        break;
                    case PAGINATION_TAB_POSITION:
                        presenter.onTabPaginationClick();
                        break;
                    case OTHER_TAB_POSITION:
                        presenter.onTabOtherClick();
                        break;
                }
            }

            @Override
            public void onTabUnselected(int position) {

            }

            @Override
            public void onTabReselected(int position) {
                onTabSelected(position);
            }
        });
    }

    @Override
    public void initContainers() {
        FragmentManager fm = getSupportFragmentManager();
        userFragment = (ContainerFragment) fm.findFragmentByTag(Screens.USER_FRAGMENT);
        if (userFragment == null) {
            userFragment = ContainerFragment_.builder().screen(Screens.USER_FRAGMENT).build();
            fm.beginTransaction()
                    .add(R.id.main_container, userFragment, Screens.USER_FRAGMENT)
                    .detach(userFragment)
                    .commitNow();
        }

        publicationsFragment = (ContainerFragment) fm.findFragmentByTag(Screens.PUBLICATIONS_FRAGMENT);
        if (publicationsFragment == null) {
            publicationsFragment = ContainerFragment_.builder().screen(Screens.PUBLICATIONS_FRAGMENT).build();
            fm.beginTransaction()
                    .add(R.id.main_container, publicationsFragment, Screens.PUBLICATIONS_FRAGMENT)
                    .detach(publicationsFragment)
                    .commitNow();
        }

        paginationFragment = (ContainerFragment) fm.findFragmentByTag(Screens.PAGINATION_FRAGMENT);
        if (paginationFragment == null) {
            paginationFragment = ContainerFragment_.builder().screen(Screens.PAGINATION_FRAGMENT).build();
            fm.beginTransaction()
                    .add(R.id.main_container, paginationFragment, Screens.PAGINATION_FRAGMENT)
                    .detach(paginationFragment)
                    .commitNow();
        }

        tabContainerFragment = (ContainerFragment) fm.findFragmentByTag(Screens.OTHER_FRAGMENT);
        if (tabContainerFragment == null) {
            tabContainerFragment = ContainerFragment_.builder().screen(Screens.OTHER_FRAGMENT).build();
            fm.beginTransaction()
                    .add(R.id.main_container, tabContainerFragment, Screens.OTHER_FRAGMENT)
                    .detach(tabContainerFragment)
                    .commitNow();
        }
    }

    public void selectStartTab() {
        if (!userFragment.isAdded() && !publicationsFragment.isAdded() && !paginationFragment.isAdded() && !tabContainerFragment.isAdded()) {
            bottomNavigationBar.selectTab(USER_TAB_POSITION, true);
        }
    }

    private Navigator navigator = new Navigator() {
        @Override
        public void applyCommand(Command command) {
            if (command instanceof Back) {
                finish();
            } else if (command instanceof SystemMessage) {
                AndroidUtils.toast(MainActivity.this, ((SystemMessage) command).getMessage());
            } else if (command instanceof Replace) {
                FragmentManager fm = getSupportFragmentManager();
                switch (((Replace) command).getScreenKey()) {
                    case Screens.USER_FRAGMENT:
                        fm.beginTransaction()
                                .detach(publicationsFragment)
                                .detach(paginationFragment)
                                .detach(tabContainerFragment)
                                .attach(userFragment)
                                .commitNow();
                        break;
                    case Screens.PUBLICATIONS_FRAGMENT:
                        fm.beginTransaction()
                                .detach(userFragment)
                                .detach(paginationFragment)
                                .detach(tabContainerFragment)
                                .attach(publicationsFragment)
                                .commitNow();
                        break;
                    case Screens.PAGINATION_FRAGMENT:
                        fm.beginTransaction()
                                .detach(userFragment)
                                .detach(publicationsFragment)
                                .detach(tabContainerFragment)
                                .attach(paginationFragment)
                                .commitNow();
                        break;
                    case Screens.OTHER_FRAGMENT:
                        fm.beginTransaction()
                                .detach(userFragment)
                                .detach(publicationsFragment)
                                .detach(paginationFragment)
                                .attach(tabContainerFragment)
                                .commitNow();
                        break;
                }
            }
        }
    };

    @Override
    public void highlightTab(int position) {
        bottomNavigationBar.selectTab(position, false);
    }

    @Override
    public void onBackPressed() {
        Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.main_container);
        if (fragment != null
                && fragment instanceof BackButtonListener
                && ((BackButtonListener) fragment).onBackPressed()) {
            return;
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public Router getRouter() {
        return router;
    }
}