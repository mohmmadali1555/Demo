package com.creativeitem.academy.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

import com.creativeitem.academy.Network.Api;
import com.creativeitem.academy.R;
import com.creativeitem.academy.Utils.Helpers;
import com.github.ybq.android.spinkit.sprite.Sprite;
import com.github.ybq.android.spinkit.style.Circle;
import com.github.ybq.android.spinkit.style.Wave;

public class CoursePurchaseActivity extends AppCompatActivity {
    WebView coursePurchaseView;
    private ProgressBar progressBar;
    private int mCourseId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.course_purchase);
        init();
        initProgressBar();
        getThecourseId();
        setupCoursePurchaseWebView();
    }

    private void init() {
        coursePurchaseView = findViewById(R.id.coursePurchaseView);
        coursePurchaseView.setVerticalScrollBarEnabled(false);
    }
    // Initialize the progress bar
    private void initProgressBar() {
        progressBar = (ProgressBar)findViewById(R.id.progressBar);
        Sprite circularLoading = new Circle();
        progressBar.setIndeterminateDrawable(circularLoading);
    }

    private void getThecourseId() {
        this.mCourseId = (int) getIntent().getSerializableExtra("courseId");
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent();
        intent.putExtra("courseId", this.mCourseId);
        setResult(RESULT_OK, intent);
        finish();
    }

    private void setupCoursePurchaseWebView() {
        // Auth Token
        SharedPreferences preferences = getSharedPreferences(Helpers.SHARED_PREF, 0);
        final String authToken = preferences.getString("userToken", "null");
        progressBar.setVisibility(View.VISIBLE);
        coursePurchaseView.getSettings().setJavaScriptEnabled(true); // enable javascript
        coursePurchaseView.getSettings().setDomStorageEnabled(true);
        coursePurchaseView.getSettings().setAppCacheEnabled(true);
        coursePurchaseView.getSettings().setAppCachePath(getApplicationContext().getFilesDir().getAbsolutePath() + "/cache");
        coursePurchaseView.getSettings().setDatabaseEnabled(true);
        coursePurchaseView.getSettings().setDatabasePath(getApplicationContext().getFilesDir().getAbsolutePath() + "/databases");
        coursePurchaseView.getSettings().setPluginState(WebSettings.PluginState.ON);
        coursePurchaseView.getSettings().setDomStorageEnabled(true);
        coursePurchaseView.loadUrl(Api.COURSE_PURCHASE_URL+authToken+"/"+mCourseId);
        coursePurchaseView.setWebViewClient(new WebViewClient() {
            public void onPageFinished(WebView view, String url) {
                progressBar.setVisibility(View.GONE);
            }
        });
    }
    public void handleBackButton(View view) {
        CoursePurchaseActivity.super.onBackPressed();
    }
}
