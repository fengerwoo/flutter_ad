package com.fenger.ad.flutter_ad.gdt;

import android.content.Context;
import android.util.Log;

import com.qq.e.ads.rewardvideo.RewardVideoAD;
import com.qq.e.ads.rewardvideo.RewardVideoADListener;
import com.qq.e.ads.rewardvideo.ServerSideVerificationOptions;
import com.qq.e.comm.util.AdError;

import org.json.JSONObject;

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


    private Map<String, RewardVideoAD> videoADMap = new HashMap<String, RewardVideoAD>();

    /**
     * 预加载广告
     * @param context
     * @param posid
     * @param voice
     * @param extra
     * @param user_id
     * @param callback
     */
    public void preload(Context context, String posid, Boolean voice, String extra, String user_id, final GDTCallBack callback){
        final String vid = this.getVID();
        final RewardVideoAD ad = new RewardVideoAD(context, posid, new RewardVideoADListener() {
            @Override
            public void onADLoad() {
                // 回调结果
                Map<String, Object> map = new HashMap<String, Object>(){{
                    put("code", 1);
                    put("message", "onADLoad@广告加载成功，可在此回调后进行广告展示");
                    put("data", new HashMap<String, Object>(){{ put("vid", vid); }});
                }};
                callback.call(map);
            }

            @Override
            public void onVideoCached() {
                // 回调结果
                Map<String, Object> map = new HashMap<String, Object>(){{
                    put("code", 2);
                    put("message", "onVideoCached@视频素材缓存成功，可在此回调后进行广告展示");
                    put("data",  new HashMap<String, Object>(){{}});
                }};
                callback.call(map);
            }

            @Override
            public void onADShow() {
                // 回调结果
                Map<String, Object> map = new HashMap<String, Object>(){{
                    put("code", 3);
                    put("message", "onADShow@激励视频广告页面展示");
                    put("data",  new HashMap<String, Object>(){{}});
                }};
                callback.call(map);
            }

            @Override
            public void onADExpose() {
                // 回调结果
                Map<String, Object> map = new HashMap<String, Object>(){{
                    put("code", 4);
                    put("message", "onADExpose@激励视频广告曝光");
                    put("data",  new HashMap<String, Object>(){{}});
                }};
                callback.call(map);
            }

            @Override
            public void onReward(final Map data) {
                // 回调结果
                Map<String, Object> map = new HashMap<String, Object>(){{
                    put("code", 5);
                    put("message", "onReward@激励视频触发激励（观看视频大于一定时长或者视频播放完毕）");
                    put("data",  new HashMap<String, Object>(){{
                        put("ret", data);
                    }});
                }};
                Log.i("RVCallback onReward", new JSONObject(data).toString());
                callback.call(map);
            }

            @Override
            public void onADClick() {
                // 回调结果
                Map<String, Object> map = new HashMap<String, Object>(){{
                    put("code", 6);
                    put("message", "onADClick@激励视频广告被点击");
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
            public void onADClose() {
                // 回调结果
                Map<String, Object> map = new HashMap<String, Object>(){{
                    put("code", 8);
                    put("message", "onADClose@激励视频广告被关闭");
                    put("data",  new HashMap<String, Object>(){{}});
                }};
                callback.call(map);
            }

            @Override
            public void onError(final AdError adError) {

                final Map<String, Object> data = new HashMap<String, Object>(){{
                   put("code", adError.getErrorCode());
                   put("message", adError.getErrorMsg());
                }};

                // 回调结果
                Map<String, Object> map = new HashMap<String, Object>(){{
                    put("code", 0);
                    put("message", "onError@错误：code:"+adError.getErrorCode()+" message:"+adError.getErrorMsg());
                    put("data", data);
                }};
                callback.call(map);
            }
        }, voice); //创建广告

        ServerSideVerificationOptions options = new ServerSideVerificationOptions.Builder()
                .setCustomData(extra) // 设置激励视频服务端验证的自定义信息
                .setUserId(user_id) // 设置服务端验证的用户信息
                .build();
        ad.setServerSideVerificationOptions(options);

        this.videoADMap.put(vid, ad); //添加入预加载列表
        ad.loadAD(); // 预加载广告
    }


    public boolean show(String vid){
        RewardVideoAD ad = this.videoADMap.get(vid);
        if(ad != null){
            ad.showAD();
            return true;
        }else{
            return false;
        }
    }

    private String getVID(){
        return System.currentTimeMillis()+ UUID.randomUUID().toString();
    }

}
