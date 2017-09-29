package com.bov.vitali.training.presentation.login.fragment;

import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.arellomobile.mvp.presenter.InjectPresenter;
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
                    TrainingApplication.INSTANCE.getRouter().navigateTo(Screens.MAIN_ACTIVITY, Uri.parse(url));
                    return true;
                } else {
                    Log.i("MyTag", "shouldOverrideUrlLoading: " + "Переход на: ,  " + Uri.parse(url));
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
                "client_id=" + getResources().getString(R.string.client_id) +
                "&scope=" + "basicProfile,publishPost" +
                "&state=" + "training" +
                "&response_type=" + "code" +
                "&redirect_uri=" + "https://bitbucket.org/vitalibov/training/";
    }
}