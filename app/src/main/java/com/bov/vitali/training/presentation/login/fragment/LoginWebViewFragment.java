package com.bov.vitali.training.presentation.login.fragment;

import android.annotation.TargetApi;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.arellomobile.mvp.presenter.InjectPresenter;
import com.bov.vitali.training.BuildConfig;
import com.bov.vitali.training.R;
import com.bov.vitali.training.TrainingApplication;
import com.bov.vitali.training.common.navigation.BackButtonListener;
import com.bov.vitali.training.common.navigation.Screens;
import com.bov.vitali.training.presentation.base.fragment.BaseFragment;
import com.bov.vitali.training.presentation.login.presenter.LoginWebViewPresenter;
import com.bov.vitali.training.presentation.login.view.LoginWebView;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;

@EFragment(R.layout.fragment_login_web_view)
public class LoginWebViewFragment extends BaseFragment implements LoginWebView, BackButtonListener {

    @InjectPresenter LoginWebViewPresenter presenter;
    @ViewById WebView webViewLogin;

    public static LoginWebViewFragment getInstance(int position) {
        LoginWebViewFragment fragment = new LoginWebViewFragment_();
        Bundle b = new Bundle();
        b.putInt(Screens.KEY, position);
        fragment.setArguments(b);
        return fragment;
    }

    @AfterViews
    void initWebView() {
        webViewLogin.setWebViewClient(new WebViewClient());
        webViewLogin.getSettings().setJavaScriptEnabled(true);
        webViewLogin.loadUrl(getUrl());
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
                if (host != null && host.equals("bitbucket.org")) {
                    Log.i("MyTag", "shouldOverrideUrlLoading: " + "Переход на BitBucket, " + Uri.parse(url));
                    String code = uri.getQueryParameter("code");
                    presenter.getToken(code);
                    TrainingApplication.INSTANCE.getRouter().navigateTo(Screens.MAIN_ACTIVITY);
                    return true;
                } else {
                    Log.i("MyTag", "shouldOverrideUrlLoading: " + "Переход на: ,  " + Uri.parse(url));
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
                if (host != null && host.equals("bitbucket.org")) {
                    String code = uri.getQueryParameter("code");
                    presenter.getToken(code);
                    TrainingApplication.INSTANCE.getRouter().navigateTo(Screens.MAIN_ACTIVITY);
                    return true;
                } else {
                    return false;
                }
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_login_web_view, container, false);

        return view;
    }

    public boolean onBackPressed() {
        if(webViewLogin.canGoBack()) {
            webViewLogin.goBack();
        } else {
            presenter.onBackPressed();
        }
        return true;
    }

    private String getUrl() {
        return "https://medium.com/m/oauth/authorize?" +
                "client_id=" + BuildConfig.MEDIUM_CLIENT_ID +
                "&scope=" + "basicProfile,publishPost" +
                "&state=" + "training" +
                "&response_type=" + "code" +
                "&redirect_uri=" + BuildConfig.MEDIUM_CLIENT_SECRET;
    }
}