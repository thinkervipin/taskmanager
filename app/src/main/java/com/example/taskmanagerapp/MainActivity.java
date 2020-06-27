package com.example.taskmanagerapp;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.Window;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;


public class MainActivity extends Activity implements NetworkStateReceiver.NetworkStateReceiverListener{
    public WebView webv;
    SwipeRefreshLayout swipeRefreshLayout;
    private NetworkStateReceiver networkStateReceiver;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);
        swipeRefreshLayout = findViewById(R.id.swipelayout1);
        networkStateReceiver = new NetworkStateReceiver();
        networkStateReceiver.addListener(this);
        this.registerReceiver(networkStateReceiver, new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION));

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                webv.reload();
                Toast.makeText(MainActivity.this, "poda patti", Toast.LENGTH_SHORT).show();
                swipeRefreshLayout.setRefreshing(false);
            }
        });

        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy =
                    new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }
        webv  =(WebView)findViewById(R.id.webv);
        WebSettings webSettings = webv.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webv.loadUrl("http://www.google.com/");
        webv.setWebViewClient(new WebViewClient());
    }
    @Override
    public void onBackPressed(){
        if(webv.canGoBack()){
            webv.goBack();

        }
        else {
            super.onBackPressed();
        }
        }

    @Override
    public void networkAvailable() {

    }

    @Override
    public void networkUnavailable() {
        Toast.makeText(this, "Please connect to the internet", Toast.LENGTH_SHORT).show();
    }
}