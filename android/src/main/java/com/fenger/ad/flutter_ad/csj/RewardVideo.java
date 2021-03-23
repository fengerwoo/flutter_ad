package com.fenger.ad.flutter_ad.csj;


import android.app.Activity;
import android.content.Context;

import com.bytedance.sdk.openadsdk.AdSlot;
import com.bytedance.sdk.openadsdk.TTAdConstant;
import com.bytedance.sdk.openadsdk.TTAdManager;
import com.bytedance.sdk.openadsdk.TTAdNative;
import com.bytedance.sdk.openadsdk.TTAdSdk;
import com.bytedance.sdk.openadsdk.TTAppDownloadListener;
import com.bytedance.sdk.openadsdk.TTRewardVideoAd;
import com.fenger.ad.flutter_ad.CallBack;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class RewardVideo {


    private RewardVideo(){}
    private static RewardVideo instance = null;
    public static RewardVideo getInstance() {
        if (instance == null) {
            synchronized (RewardVideo.class) {
                if (instance == null) {
                    instance = new RewardVideo();
                }
            }
        }
        return instance;
    }


    private Map<String, CallBack> videoADMap = new HashMap<String, CallBack>();


    public void preload(Context context, String codeID, Integer adCount, String rewardName, Integer rewardAmount, String userID, String extra, final CallBack callback){
        TTAdManager mTTAdManager = TTAdSdk.getAdManager();
        TTAdNative mTTAdNative = mTTAdManager.createAdNative(context);
        AdSlot adSlot = new AdSlot.Builder()
                .setCodeId(codeID)
                .setSupportDeepLink(true)
                .setAdCount(adCount)
                //个性化模板广告需要设置期望个性化模板广告的大小,单位dp,激励视频场景，只要设置的值大于0即可。仅模板广告需要设置此参数
                .setExpressViewAcceptedSize(500,500)
                .setImageAcceptedSize(1080, 1920)

                .setRewardName(rewardName)
                .setRewardAmount(rewardAmount)
                .setUserID(userID)
                .setOrientation(TTAdConstant.VERTICAL)
                .setMediaExtra(extra)
                .build();

        final RewardVideo rv = this;
        final String vid = rv.getVID();

        System.out.println("");

        //播放回调
        final TTRewardVideoAd.RewardAdInteractionListener rewardAdInteractionListener = new TTRewardVideoAd.RewardAdInteractionListener() {
            @Override
            public void onAdShow() {
                // 回调结果
                Map<String, Object> map = new HashMap<String, Object>(){{
                    put("code", 3);
                    put("message", "onADShow@激励视频广告页面展示");
                    put("data",  new HashMap<String, Object>(){{}});
                }};
                callback.call(map);
            }

            @Override
            public void onAdVideoBarClick() {
                // 回调结果
                Map<String, Object> map = new HashMap<String, Object>(){{
                    put("code", 6);
                    put("message", "onAdVideoBarClick@激励视频广告被点击");
                    put("data",  new HashMap<String, Object>(){{}});
                }};
                callback.call(map);
            }

            @Override
            public void onAdClose() {
                // 回调结果
                Map<String, Object> map = new HashMap<String, Object>(){{
                    put("code", 8);
                    put("message", "onAdClose@激励视频广告被关闭");
                    put("data",  new HashMap<String, Object>(){{}});
                }};
                callback.call(map);
            }

            @Override
            public void onVideoComplete() {
                // 回调结果
                Map<String, Object> map = new HashMap<String, Object>(){{
                    put("code", 7);
                    put("message", "onVideoComplete@激励视频播放完毕");
                    put("data",  new HashMap<String, Object>(){{}});
                }};
                callback.call(map);
            }

            @Override
            public void onVideoError() {
                // 回调结果
                final Map<String, Object> data = new HashMap<String, Object>(){{
                    put("code", 0);
                    put("message", "播放错误");
                }};

                // 回调结果
                Map<String, Object> map = new HashMap<String, Object>(){{
                    put("code", 0);
                    put("message", "onError@播放错误");
                    put("data", data);
                }};
                callback.call(map);
            }



            @Override
            public void onRewardVerify(final boolean rewardVerify, final int rewardAmount, final String rewardName, final int code, final String msg) {
                // 回调结果
                Map<String, Object> map = new HashMap<String, Object>(){{
                    put("code", 5);
                    put("message", "onRewardVerify@激励视频触发激励");
                    put("data",  new HashMap<String, Object>(){{
                        put("rewardVerify", rewardVerify);
                        put("rewardAmount", rewardAmount);
                        put("rewardName", rewardName);
                        put("code", code);
                        put("msg", msg);
                    }});
                }};
                callback.call(map);
            }

            @Override
            public void onSkippedVideo() {

            }
        };

        // 下载安装回调
        final TTAppDownloadListener ttAppDownloadListener = new TTAppDownloadListener() {
            @Override
            public void onIdle() {

            }

            @Override
            public void onDownloadActive(final long totalBytes, final long currBytes, final String fileName, final String appName) {
                // 回调结果
                Map<String, Object> map = new HashMap<String, Object>(){{
                    put("code", 9);
                    put("message", "onDownloadActive@下载中");
                    put("data",  new HashMap<String, Object>(){{
                        put("totalBytes", totalBytes);
                        put("currBytes", currBytes);
                        put("fileName", fileName);
                        put("appName", appName);
                    }});
                }};
                callback.call(map);
            }

            @Override
            public void onDownloadPaused(long l, long l1, String s, String s1) {

            }

            @Override
            public void onDownloadFailed(long l, long l1, String s, String s1) {

            }

            @Override
            public void onDownloadFinished(final long totalBytes, final String fileName, final String appName) {
                // 回调结果
                Map<String, Object> map = new HashMap<String, Object>(){{
                    put("code", 10);
                    put("message", "onDownloadFinished@下载完成");
                    put("data",  new HashMap<String, Object>(){{
                        put("totalBytes", totalBytes);
                        put("fileName", fileName);
                        put("appName", appName);
                    }});
                }};
                callback.call(map);
            }

            @Override
            public void onInstalled(final String fileName, final String appName) {
                // 回调结果
                Map<String, Object> map = new HashMap<String, Object>(){{
                    put("code", 11);
                    put("message", "onInstalled@安装完成");
                    put("data",  new HashMap<String, Object>(){{
                        put("fileName", fileName);
                        put("appName", appName);
                    }});
                }};
                callback.call(map);
            }
        };

        // 预加载
        mTTAdNative.loadRewardVideoAd(adSlot, new TTAdNative.RewardVideoAdListener() {
            @Override
            public void onError(final int i, final String s) {
                final Map<String, Object> data = new HashMap<String, Object>(){{
                    put("code", i);
                    put("message", s);
                }};

                // 回调结果
                Map<String, Object> map = new HashMap<String, Object>(){{
                    put("code", -1);
                    put("message", "onError@错误：code:"+i+" message:"+s);
                    put("data", data);
                }};
                callback.call(map);
            }

            @Override
            public void onRewardVideoAdLoad(TTRewardVideoAd ttRewardVideoAd) {

                if (ttRewardVideoAd != null){


                    callback.setAd(ttRewardVideoAd);
                    rv.videoADMap.put(vid, callback);
                    ttRewardVideoAd.setRewardAdInteractionListener(rewardAdInteractionListener);
                    ttRewardVideoAd.setDownloadListener(ttAppDownloadListener);

                    // 回调结果
                    Map<String, Object> map = new HashMap<String, Object>(){{
                        put("code", 2);
                        put("message", "onRewardVideoAdLoad@预加载中");
                        put("data",  new HashMap<String, Object>(){{}});
                    }};
                    callback.call(map);

                }else{
                    this.onError(0, "onRewardVideoAdLoad的ttRewardVideoAd为null");
                }


            }

            @Override
            public void onRewardVideoCached() {
                // 回调结果
                Map<String, Object> map = new HashMap<String, Object>(){{
                    put("code", 1);
                    put("message", "onRewardVideoCached@预加载完成");
                    put("data",  new HashMap<String, Object>(){{ put("vid", vid); }});
                }};
                callback.call(map);
            }
        });
    }


    public boolean show(Activity context, String vid){
        CallBack callBack = this.videoADMap.get(vid);
        if(callBack != null && callBack.getAd() != null){
            ((TTRewardVideoAd)callBack.getAd()).showRewardVideoAd(context);
            return true;
        }else{
            return false;
        }
    }


    private String getVID(){
        return System.currentTimeMillis()+ UUID.randomUUID().toString();
    }

}
