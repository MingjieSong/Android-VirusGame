package com.androidApp.virusGame.UI;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import com.androidApp.virusGame.R;


/**
 * Fragment that shows WebView for CDC Website
 *
 *  Written with the assitance of adamcchampion's tictactoe app modified on 2017/08/14.
 */

public class WebFragment extends Fragment  {

    public WebFragment() {
        // Required empty public constructor
    }
    private String mUrl;
    private ProgressBar mProgressBar;

    private static final String ARG_URI = "url";
    // private static final String TAG = HelpWebViewFragment.class.getSimpleName();



    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_web, container, false);

        WebView helpInWebView = v.findViewById(R.id.resourceswebview);

        Activity activity = getActivity();

        //WebView.setWebContentsDebuggingEnabled(true);
        // helpInWebView.getSettings().setJavaScriptEnabled(true);
       helpInWebView.setWebViewClient(new WebViewClient() {
            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
                return false;
            }
        });
        helpInWebView.setWebChromeClient(new WebChromeClient() {
            public void onProgressChanged(WebView webView, int progress) {
//                if (progress == 100) {
//                    mProgressBar.setVisibility(View.GONE);
//                }
//                else {
//                    mProgressBar.setVisibility(View.VISIBLE);
//                    mProgressBar.setProgress(progress);
//                }
            }
        });

        helpInWebView.loadUrl("https://coronavirus.dc.gov/?gclid=CjwKCAiAv4n9BRA9EiwA30WND7LYSdXq2fxretSMGL9156XGVQzGyFR8ST5gpb-SRtmoVcdW1YDveRoC_xcQAvD_BwE");


        return v;
    }

    @Override
    public void onResume() {
        super.onResume();
//        try {
//            AppCompatActivity activity = (AppCompatActivity) getActivity();
//
//            if (activity != null) {
//                ActionBar actionBar = activity.getSupportActionBar();
//                if (actionBar != null) {
//                    actionBar.setSubtitle(getResources().getString(R.string.help_webview));
//                }
//            }
//        }
//        catch (NullPointerException npe) {
//            //Timber.e("Could not set subtitle");
//        }
    }
}
