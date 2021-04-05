package com.fenger.ad.flutter_ad.csj.banner;

import android.app.Activity;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.bytedance.sdk.openadsdk.AdSlot;
import com.bytedance.sdk.openadsdk.TTAdNative;
import com.bytedance.sdk.openadsdk.TTAdSdk;
import com.bytedance.sdk.openadsdk.TTNativeExpressAd;

import java.util.List;
import java.util.Map;

import io.flutter.plugin.common.BinaryMessenger;
import io.flutter.plugin.platform.PlatformView;

public class BannerView implements PlatformView {

    String adid;
    Boolean supportDeepLink;
    int expressAdNum;
    int expressTime;
    float expressViewWidth;
    float expressViewHeight;

    private FrameLayout container;
    private Activity activity;

    BannerView(Activity context, BinaryMessenger messenger, int id, Map<String, Object> params){
        activity = context;
        container = new FrameLayout(context);

//        TextView tv = new TextView(context);
//        tv.setText("你好啊！我是原生");
//        container.addView(tv);

        adid = (String) params.get("adid");
        supportDeepLink = (Boolean) params.get("supportDeepLink");
        expressAdNum = (int) params.get("expressAdNum");
        expressTime = (int) params.get("expressTime");
        expressViewWidth = new Float((double) params.get("expressViewWidth"));
        expressViewHeight = new Float((double) params.get("expressViewHeight"));


        TTAdNative mTTAdNative = TTAdSdk.getAdManager().createAdNative(context);
        AdSlot adSlot = new AdSlot.Builder()
                .setCodeId(adid) //广告位id
                .setSupportDeepLink(supportDeepLink)
                .setAdCount(expressAdNum) //请求广告数量为1到3条
                .setExpressViewAcceptedSize(expressViewWidth,expressViewHeight) //期望模板广告view的size,单位dp
                .build();

        mTTAdNative.loadBannerExpressAd(adSlot, new TTAdNative.NativeExpressAdListener() {
            @Override
            public void onError(int code, String message) {
                Log.i("","");
            }

            @Override
            public void onNativeExpressAdLoad(List<TTNativeExpressAd> ads) {
               if(ads == null || ads.size() == 0){
                   return;
               }
               TTNativeExpressAd ad = ads.get(0);
               ad.setSlideIntervalTime(expressTime * 1000);
               bindAdListener(ad);
            }
        });
    }


    private void bindAdListener(TTNativeExpressAd ad){
        ad.setExpressInteractionListener(new TTNativeExpressAd.ExpressAdInteractionListener(){

            @Override
            public void onAdClicked(View view, int type) {
                Log.i("","");
            }

            @Override
            public void onAdShow(View view, int type) {
                Log.i("","");
            }

            @Override
            public void onRenderFail(View view, String msg, int code) {
                Log.i("","");
            }

            @Override
            public void onRenderSuccess(View view, float width, float height) {
                container.removeAllViews();
                container.addView(view);
            }
        });
        ad.render();
    }
    

    @Override
    public View getView() {
        return container;
    }

    @Override
    public void dispose() {

    }
}
