package com.bov.vitali.training.presentation.login.fragment;

import android.annotation.TargetApi;
import android.net.Uri;
import android.os.Build;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.arellomobile.mvp.presenter.InjectPresenter;
import com.bov.vitali.training.R;
import com.bov.vitali.training.App;
import com.bov.vitali.training.common.navigation.Screens;
import com.bov.vitali.training.presentation.base.fragment.BaseFragment;
import com.bov.vitali.training.presentation.login.presenter.LoginWebViewPresenter;
import com.bov.vitali.training.presentation.login.view.LoginWebView;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;

@EFragment(R.layout.fragment_login_web_view)
public class LoginWebViewFragment extends BaseFragment implements LoginWebView {

    private static final String HOST = "bitbucket.org";
    private static final String CODE = "code";
    @InjectPresenter LoginWebViewPresenter presenter;
    @ViewById WebView webViewLogin;

    @AfterViews
    void initWebView() {
        webViewLogin.setWebViewClient(new WebViewClient());
        webViewLogin.getSettings().setJavaScriptEnabled(true);
        webViewLogin.loadUrl(presenter.getUrl());
        webViewLogin.setWebViewClient(new WebViewClient() {
            @SuppressWarnings("deprecation")
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                Uri uri;
                try {
                    uri = Uri.parse(url);
                } catch (NullPointerException e) {
                    return true;
                }
                String host = uri.getHost();
                if (host != null && host.equals(HOST)) {
                    String code = uri.getQueryParameter(CODE);
                    presenter.getToken(code);
                    App.INSTANCE.getRouter().navigateTo(Screens.BOTTOM_NAVIGATION_ACTIVITY);
                    return true;
                } else {
                    return false;
                }
            }

            @TargetApi(Build.VERSION_CODES.N)
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
                final Uri uri = request.getUrl();
                return handleUri(uri);
            }

            private boolean handleUri(final Uri uri) {
                final String host = uri.getHost();
                if (host != null && host.equals(HOST)) {
                    String code = uri.getQueryParameter(CODE);
                    presenter.getToken(code);
                    App.INSTANCE.getRouter().navigateTo(Screens.BOTTOM_NAVIGATION_ACTIVITY);
                    return true;
                } else {
                    return false;
                }
            }
        });
    }

    @Override
    public boolean onBackPressed() {
        if(webViewLogin.canGoBack()) {
            webViewLogin.goBack();
        } else {
            presenter.onBackPressed();
        }
        return true;
    }
}