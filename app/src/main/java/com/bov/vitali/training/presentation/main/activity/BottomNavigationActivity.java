package com.bov.vitali.training.presentation.main.activity;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.ContextCompat;

import com.arellomobile.mvp.presenter.InjectPresenter;
import com.ashokvarma.bottomnavigation.BottomNavigationBar;
import com.ashokvarma.bottomnavigation.BottomNavigationItem;
import com.bov.vitali.training.App;
import com.bov.vitali.training.R;
import com.bov.vitali.training.common.navigation.BackButtonListener;
import com.bov.vitali.training.common.navigation.RouterProvider;
import com.bov.vitali.training.common.navigation.Screens;
import com.bov.vitali.training.common.utils.AndroidUtils;
import com.bov.vitali.training.presentation.base.activity.BaseNavigationActivity;
import com.bov.vitali.training.presentation.main.fragment.TabContainerFragment;
import com.bov.vitali.training.presentation.main.fragment.TabContainerFragment_;
import com.bov.vitali.training.presentation.main.presenter.BottomNavigationPresenter;
import com.bov.vitali.training.presentation.main.view.BottomNavigationContract;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

import ru.terrakok.cicerone.Navigator;
import ru.terrakok.cicerone.Router;
import ru.terrakok.cicerone.commands.Back;
import ru.terrakok.cicerone.commands.Command;
import ru.terrakok.cicerone.commands.Replace;
import ru.terrakok.cicerone.commands.SystemMessage;

@EActivity(R.layout.activity_bottom)
public class BottomNavigationActivity extends BaseNavigationActivity<BottomNavigationPresenter, BottomNavigationContract.View>
        implements BottomNavigationContract.View, RouterProvider {
    @InjectPresenter BottomNavigationPresenter presenter;
    @ViewById(R.id.bottom_navigation_bar)
    BottomNavigationBar bottomNavigationBar;
    private TabContainerFragment userFragment;
    private TabContainerFragment publicationsFragment;
    private TabContainerFragment paginationFragment;
    private TabContainerFragment schedulerFragment;

    @Override
    protected void onResumeFragments() {
        super.onResumeFragments();
        App.INSTANCE.getNavigatorHolder().setNavigator(navigator);
    }

    @AfterViews
    public void afterViews() {
        initBottomNavigationBar();
        initContainers();
        selectStartTab();
    }

    @Override
    public void initBottomNavigationBar() {
        bottomNavigationBar.setAutoHideEnabled(true);
        bottomNavigationBar.setActiveColor(R.color.colorPrimary);
        bottomNavigationBar
                .addItem(new BottomNavigationItem(R.drawable.ic_perm_identity_black_24px, R.string.bottom_bar_tab_user))
                .addItem(new BottomNavigationItem(R.drawable.ic_people_outline_black_24px, R.string.bottom_bar_tab_publications))
                .addItem(new BottomNavigationItem(R.drawable.ic_list_black_24px, R.string.bottom_bar_tab_pagination))
                .addItem(new BottomNavigationItem(R.drawable.ic_more_horiz_black_24px, R.string.bottom_bar_tab_other))
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
                    case SCHEDULER_TAB_POSITION:
                        presenter.onTabSchedulerClick();
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
        userFragment = (TabContainerFragment) fm.findFragmentByTag(Screens.USER_FRAGMENT);
        if (userFragment == null) {
            userFragment = TabContainerFragment_.builder().screen(Screens.USER_FRAGMENT).build();
            fm.beginTransaction()
                    .add(R.id.bottom_container, userFragment, Screens.USER_FRAGMENT)
                    .detach(userFragment)
                    .commitNow();
        }

        publicationsFragment = (TabContainerFragment) fm.findFragmentByTag(Screens.PUBLICATIONS_FRAGMENT);
        if (publicationsFragment == null) {
            publicationsFragment = TabContainerFragment_.builder().screen(Screens.PUBLICATIONS_FRAGMENT).build();
            fm.beginTransaction()
                    .add(R.id.bottom_container, publicationsFragment, Screens.PUBLICATIONS_FRAGMENT)
                    .detach(publicationsFragment)
                    .commitNow();
        }

        paginationFragment = (TabContainerFragment) fm.findFragmentByTag(Screens.PAGINATION_FRAGMENT);
        if (paginationFragment == null) {
            paginationFragment = TabContainerFragment_.builder().screen(Screens.PAGINATION_FRAGMENT).build();
            fm.beginTransaction()
                    .add(R.id.bottom_container, paginationFragment, Screens.PAGINATION_FRAGMENT)
                    .detach(paginationFragment)
                    .commitNow();
        }

        schedulerFragment = (TabContainerFragment) fm.findFragmentByTag(Screens.SCHEDULER_FRAGMENT);
        if (schedulerFragment == null) {
            schedulerFragment = TabContainerFragment_.builder().screen(Screens.SCHEDULER_FRAGMENT).build();
            fm.beginTransaction()
                    .add(R.id.bottom_container, schedulerFragment, Screens.SCHEDULER_FRAGMENT)
                    .detach(schedulerFragment)
                    .commitNow();
        }
    }

    public void selectStartTab() {
        if (!userFragment.isAdded() && !publicationsFragment.isAdded() && !paginationFragment.isAdded() && !schedulerFragment.isAdded()) {
            bottomNavigationBar.selectTab(USER_TAB_POSITION, true);
        }
    }

    private Navigator navigator = new Navigator() {
        @Override
        public void applyCommand(Command command) {
            if (command instanceof Back) {
                finish();
            } else if (command instanceof SystemMessage) {
                AndroidUtils.toast(BottomNavigationActivity.this, ((SystemMessage) command).getMessage());
            } else if (command instanceof Replace) {
                FragmentManager fm = getSupportFragmentManager();
                switch (((Replace) command).getScreenKey()) {
                    case Screens.USER_FRAGMENT:
                        fm.beginTransaction()
                                .detach(publicationsFragment)
                                .detach(paginationFragment)
                                .detach(schedulerFragment)
                                .attach(userFragment)
                                .commitNow();
                        break;
                    case Screens.PUBLICATIONS_FRAGMENT:
                        fm.beginTransaction()
                                .detach(userFragment)
                                .detach(paginationFragment)
                                .detach(schedulerFragment)
                                .attach(publicationsFragment)
                                .commitNow();
                        break;
                    case Screens.PAGINATION_FRAGMENT:
                        fm.beginTransaction()
                                .detach(userFragment)
                                .detach(publicationsFragment)
                                .detach(schedulerFragment)
                                .attach(paginationFragment)
                                .commitNow();
                        break;
                    case Screens.SCHEDULER_FRAGMENT:
                        fm.beginTransaction()
                                .detach(userFragment)
                                .detach(publicationsFragment)
                                .detach(paginationFragment)
                                .attach(schedulerFragment)
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
        Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.bottom_container);
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
        return App.INSTANCE.getRouter();
    }
}