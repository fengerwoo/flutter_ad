package com.fenger.ad.flutter_ad.gdt;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.qq.e.ads.splash.SplashAD;
import com.qq.e.ads.splash.SplashADListener;
import com.qq.e.ads.splash.SplashADZoomOutListener;
import com.qq.e.comm.util.AdError;

public class SplashActivity extends Activity{

    String posID;
    SplashAD splashAD;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        final LinearLayout lview = new LinearLayout(this);
        setContentView(lview);

        Intent intent = getIntent();
        posID = intent.getStringExtra("posID");


        TextView skipView = new TextView(this);
        skipView.setText("跳过");

        splashAD = new SplashAD(this, posID, new SplashADListener() {
            @Override
            public void onADDismissed() {
                SplashActivity.this.finish();
            }

            @Override
            public void onNoAD(AdError adError) {
                SplashActivity.this.finish();
            }

            @Override
            public void onADPresent() {

            }

            @Override
            public void onADClicked() {

            }

            @Override
            public void onADTick(long l) {

            }

            @Override
            public void onADExposure() {

            }

            @Override
            public void onADLoaded(long l) {

            }
        });
        splashAD.fetchAndShowIn(lview);

    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(0, 0);
    }
}
