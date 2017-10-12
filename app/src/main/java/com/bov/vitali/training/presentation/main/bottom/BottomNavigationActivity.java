package com.bov.vitali.training.presentation.main.bottom;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

import com.arellomobile.mvp.presenter.InjectPresenter;
import com.ashokvarma.bottomnavigation.BottomNavigationBar;
import com.ashokvarma.bottomnavigation.BottomNavigationItem;
import com.bov.vitali.training.App;
import com.bov.vitali.training.R;
import com.bov.vitali.training.common.navigation.BackButtonListener;
import com.bov.vitali.training.common.navigation.RouterProvider;
import com.bov.vitali.training.common.navigation.Screens;
import com.bov.vitali.training.common.utils.AndroidUtils;
import com.bov.vitali.training.presentation.base.activity.BaseActivity;

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
public class BottomNavigationActivity extends BaseActivity implements BottomNavigationView, RouterProvider {
    @InjectPresenter BottomNavigationPresenter presenter;
    @ViewById(R.id.bottom_navigation_bar)
    BottomNavigationBar bottomNavigationBar;
    private TabContainerFragment userFragment;
    private TabContainerFragment publicationsFragment;

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

    public void initBottomNavigationBar() {
        bottomNavigationBar.setAutoHideEnabled(true);
        bottomNavigationBar
                .addItem(new BottomNavigationItem(R.mipmap.ic_launcher, R.string.bottom_bar_tab_user))
                .addItem(new BottomNavigationItem(R.mipmap.ic_launcher, R.string.bottom_bar_tab_publications))
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
    }

    public void selectStartTab() {
        if (!userFragment.isAdded() && !publicationsFragment.isAdded()) {
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
                                .attach(userFragment)
                                .commitNow();
                        break;
                    case Screens.PUBLICATIONS_FRAGMENT:
                        fm.beginTransaction()
                                .detach(userFragment)
                                .attach(publicationsFragment)
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