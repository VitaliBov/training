package com.bov.vitali.training.presentation.main.bottom;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

import com.arellomobile.mvp.presenter.InjectPresenter;
import com.arellomobile.mvp.presenter.ProvidePresenter;
import com.ashokvarma.bottomnavigation.BottomNavigationBar;
import com.ashokvarma.bottomnavigation.BottomNavigationItem;
import com.bov.vitali.training.R;
import com.bov.vitali.training.TrainingApplication;
import com.bov.vitali.training.common.navigation.BackButtonListener;
import com.bov.vitali.training.common.navigation.RouterProvider;
import com.bov.vitali.training.common.navigation.Screens;
import com.bov.vitali.training.common.utils.AndroidUtils;
import com.bov.vitali.training.presentation.base.activity.BaseActivity;
import com.bov.vitali.training.presentation.main.fragment.PublicationsFragment;
import com.bov.vitali.training.presentation.main.fragment.PublicationsFragment_;
import com.bov.vitali.training.presentation.main.fragment.UserFragment;
import com.bov.vitali.training.presentation.main.fragment.UserFragment_;

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
    public static final String TAG_USER = "User";
    public static final String TAG_PUBLICATIONS = "Publications";
    private UserFragment userFragment;
    private PublicationsFragment publicationsFragment;

    @InjectPresenter BottomNavigationPresenter presenter;
    @ViewById(R.id.bottom_navigation_bar)
    BottomNavigationBar bottomNavigationBar;

    @ProvidePresenter
    public BottomNavigationPresenter createBottomNavigationPresenter() {
        return new BottomNavigationPresenter(TrainingApplication.INSTANCE.getRouter());
    }

    @Override
    protected void onResumeFragments() {
        super.onResumeFragments();
        TrainingApplication.INSTANCE.getNavigatorHolder().setNavigator(navigator);
    }

    @AfterViews
    public void afterViews() {
        initViews();
        initContainers();
        selectStartTab();
    }

    public void initViews() {
        bottomNavigationBar
                .addItem(new BottomNavigationItem(R.mipmap.ic_launcher, R.string.tab_user))
                .addItem(new BottomNavigationItem(R.mipmap.ic_launcher, R.string.tab_publications))
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

    @AfterViews
    public void initContainers() {
        FragmentManager fm = getSupportFragmentManager();
        userFragment = (UserFragment) fm.findFragmentByTag(TAG_USER);
        if (userFragment == null) {
            userFragment = UserFragment_.builder().actionBarTitle(TAG_USER).build();
            fm.beginTransaction()
                    .add(R.id.bottom_container, userFragment, TAG_USER)
                    .detach(userFragment)
                    .commitNow();
        }

        publicationsFragment = (PublicationsFragment) fm.findFragmentByTag(TAG_PUBLICATIONS);
        if (publicationsFragment == null) {
            publicationsFragment = PublicationsFragment_.builder().actionBarTitle(TAG_PUBLICATIONS).build();
            fm.beginTransaction()
                    .add(R.id.bottom_container, publicationsFragment, TAG_PUBLICATIONS)
                    .detach(publicationsFragment)
                    .commitNow();
        }
    }

    public void selectStartTab() {
        bottomNavigationBar.selectTab(USER_TAB_POSITION, true);
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
    public void highlightTab(int position) {
        bottomNavigationBar.selectTab(position, false);
    }

    @Override
    public Router getRouter() {
        return TrainingApplication.INSTANCE.getRouter();
    }
}