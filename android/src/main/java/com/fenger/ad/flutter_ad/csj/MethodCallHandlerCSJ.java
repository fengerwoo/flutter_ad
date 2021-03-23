package com.fenger.ad.flutter_ad.csj;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.bytedance.sdk.openadsdk.TTAdConfig;
import com.bytedance.sdk.openadsdk.TTAdSdk;
import com.fenger.ad.flutter_ad.CallBack;
import com.fenger.ad.flutter_ad.LifeAware;
import com.fenger.ad.flutter_ad.Utils;

import java.util.HashMap;
import java.util.Map;

import io.flutter.embedding.engine.plugins.FlutterPlugin;
import io.flutter.embedding.engine.plugins.activity.ActivityPluginBinding;
import io.flutter.embedding.engine.plugins.service.ServicePluginBinding;
import io.flutter.plugin.common.MethodCall;
import io.flutter.plugin.common.MethodChannel;
import io.flutter.plugin.common.MethodChannel.MethodCallHandler;


public class MethodCallHandlerCSJ implements MethodCallHandler, LifeAware {

    private MethodChannel channel;
    public MethodCallHandlerCSJ(MethodChannel channel){
        this.channel = channel;    
    }

    private Activity activity;
    
    @Override
    public void onAttachedToEngine(@NonNull FlutterPlugin.FlutterPluginBinding flutterPluginBinding) {

    }

    @Override
    public void onDetachedFromEngine(@NonNull FlutterPlugin.FlutterPluginBinding binding) {

    }

    @Override
    public void onAttachedToActivity(@NonNull ActivityPluginBinding binding) {
        activity = binding.getActivity();
    }

    @Override
    public void onDetachedFromActivityForConfigChanges() {

    }

    @Override
    public void onReattachedToActivityForConfigChanges(@NonNull ActivityPluginBinding binding) {

    }

    @Override
    public void onDetachedFromActivity() {

    }

    @Override
    public void onAttachedToService(@NonNull ServicePluginBinding binding) {

    }

    @Override
    public void onDetachedFromService() {

    }



    @Override
    public void onMethodCall(@NonNull MethodCall call, @NonNull MethodChannel.Result result) {
        switch (call.method){
            case "init":
                String appid = call.argument("appID");
                this.init(appid, result);
                break;

            case "showSplashAD":
                String showSplashADCodeID = call.argument("codeID");
                this.showSplashAD(showSplashADCodeID, result);
                break;
                
            case "preloadRewardVideo":
                String codeID = call.argument("codeID");
                Integer adCount = call.argument("adCount");
                String rewardName = call.argument("rewardName");
                Integer rewardAmount = call.argument("rewardAmount");
                String userID = call.argument("userID");
                String extra = call.argument("extra");
                String callbackID = call.argument("callbackID");
                this.preloadRewardVideo(codeID, adCount, rewardName, rewardAmount, userID, extra, callbackID);
                break;

            case "showRewardVideo":
                String vid = call.argument("vid");
                this.showRewardVideo(vid, result);
                break;
        }
    }


    public void callBack(final String type, final String callback_id ,final Map data){
        this.channel.invokeMethod("dartCallBack", new HashMap<String, Object>(){{
            put("type", type);
            put("callback_id", callback_id);
            put("data", data);
        }});
    }

    public void handlePermissions(){
        if (ContextCompat.checkSelfPermission(this.activity, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this.activity, new String[]{Manifest.permission.READ_PHONE_STATE}, 1);
        }

        if (ContextCompat.checkSelfPermission(this.activity, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this.activity, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
        }
    }


    /**
     * 初始化穿山甲
     * @param appid
     * @param result
     */
    public void init(String appid, MethodChannel.Result result){
        this.handlePermissions();
        TTAdSdk.init(this.activity, new TTAdConfig.Builder().
                appId(appid).
                appName(Utils.getAppName(this.activity)).
                build());

        result.success(null);
    }

    /**
     * 显示开屏广告页面
     * @param codeID
     * @param result
     */
    public void showSplashAD(String codeID, MethodChannel.Result result){
        Intent intent = new Intent();
        intent.setClass(activity,SplashActivity.class);
        intent.putExtra("codeID", codeID);
        activity.startActivity(intent);
        activity.overridePendingTransition(0,0);
        result.success(null);
    }

    /**
     * 预加载激励视频
     * @param codeID
     * @param adCount
     * @param rewardName
     * @param rewardAmount
     * @param userID
     * @param extra
     */
    public void preloadRewardVideo(String codeID, Integer adCount, String rewardName, Integer rewardAmount, String userID, String extra, final String callbackID){

        RewardVideo.getInstance().preload(this.activity, codeID, adCount, rewardName, rewardAmount, userID, extra, new CallBack() {
            @Override
            public void call(Map<String, Object> map) {
                MethodCallHandlerCSJ.this.callBack("rewardVideo", callbackID ,map);
            }
        });
    }

    /**
     * 播放激励视频
     * @param vid
     * @param result
     */
    public void showRewardVideo(String vid,  MethodChannel.Result result){
        final boolean show =  RewardVideo.getInstance().show((Activity) this.activity, vid);
        result.success(show);
    }


}
