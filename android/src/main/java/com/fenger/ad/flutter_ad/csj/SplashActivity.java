package com.fenger.ad.flutter_ad.csj;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;

import com.bytedance.sdk.openadsdk.AdSlot;
import com.bytedance.sdk.openadsdk.TTAdManager;
import com.bytedance.sdk.openadsdk.TTAdNative;
import com.bytedance.sdk.openadsdk.TTAdSdk;
import com.bytedance.sdk.openadsdk.TTSplashAd;

public class SplashActivity extends Activity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final LinearLayout lview = new LinearLayout(this);

        setContentView(lview);

        Intent intent = getIntent();
        String codeid = intent.getStringExtra("codeID");


        TTAdManager mTTAdManager = TTAdSdk.getAdManager();
        TTAdNative mTTAdNative = mTTAdManager.createAdNative(this);

        AdSlot adSlot = new AdSlot.Builder()
                .setCodeId(codeid)
                .setImageAcceptedSize(1080, 1920)
                .build();

        mTTAdNative.loadSplashAd(adSlot, new TTAdNative.SplashAdListener() {
            @Override
            public void onError(int i, String s) {
                Log.e("zzz", i+":"+s);
                SplashActivity.this.finish();
            }

            @Override
            public void onTimeout() {
                SplashActivity.this.finish();
                Log.e("zzz", "onTimeout");
            }

            @Override
            public void onSplashAdLoad(TTSplashAd ttSplashAd) {
                //获取SplashView
                View view = ttSplashAd.getSplashView();
                if (view != null && lview != null && !SplashActivity.this.isFinishing()) {
                    lview.removeAllViews();
                    //把SplashView 添加到ViewGroup中,注意开屏广告view：width >=70%屏幕宽；height >=50%屏幕高
                    lview.addView(view);
                    //设置不开启开屏广告倒计时功能以及不显示跳过按钮,如果这么设置，您需要自定义倒计时逻辑
                    //ad.setNotAllowSdkCountdown();
                }else {
                    SplashActivity.this.finish();
                }

                ttSplashAd.setSplashInteractionListener(new TTSplashAd.AdInteractionListener() {
                    @Override
                    public void onAdClicked(View view, int i) {

                    }

                    @Override
                    public void onAdShow(View view, int i) {

                    }

                    @Override
                    public void onAdSkip() {
                        SplashActivity.this.finish();
                    }

                    @Override
                    public void onAdTimeOver() {
                        SplashActivity.this.finish();
                    }
                });
            }
        });

    }

    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            return true;
        }
        return false;
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(0, 0);
    }
}
