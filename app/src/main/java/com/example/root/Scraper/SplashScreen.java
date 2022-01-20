package com.example.root.Scraper;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;

public class SplashScreen extends AppCompatActivity {
    private final int SPLASH_TIME=4000;
    AdView adViewSplash;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_splash_screen);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent=new Intent(SplashScreen.this,MainActivity.class);
                startActivity(intent);
                finish();
            }
        },SPLASH_TIME);

        //Ad implementation
        MobileAds.initialize(this,
                "ca-app-pub-8398979833844274~5519452973");

        adViewSplash = (AdView) findViewById(R.id.adViewFront);
        AdRequest adRequest = new AdRequest.Builder().build();
        adViewSplash.loadAd(adRequest);

    }
}
