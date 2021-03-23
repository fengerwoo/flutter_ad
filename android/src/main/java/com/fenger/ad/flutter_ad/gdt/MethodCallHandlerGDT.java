package com.fenger.ad.flutter_ad.gdt;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.fenger.ad.flutter_ad.LifeAware;
import com.fenger.ad.flutter_ad.csj.MethodCallHandlerCSJ;
import com.qq.e.comm.managers.GDTADManager;

import java.util.HashMap;
import java.util.Map;

import io.flutter.embedding.engine.plugins.FlutterPlugin;
import io.flutter.embedding.engine.plugins.activity.ActivityPluginBinding;
import io.flutter.embedding.engine.plugins.service.ServicePluginBinding;
import io.flutter.plugin.common.MethodCall;
import io.flutter.plugin.common.MethodChannel;
import io.flutter.plugin.common.MethodChannel.MethodCallHandler;

public class MethodCallHandlerGDT implements MethodCallHandler, LifeAware {

    private MethodChannel channel;
    private Activity activity;
    public MethodCallHandlerGDT(MethodChannel channel) {
        this.channel = channel;
    }

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
                String showSplashADPosID = call.argument("posID");
                this.showSplashAD(showSplashADPosID, result);
                break;

            case "preloadRewardVideo":
                String posID = call.argument("posID");
                Boolean voice = call.argument("voice");
                String extra = call.argument("extra");
                String userID = call.argument("userID");
                String callbackID = call.argument("callbackID");
                this.preloadRewardVideo(posID, voice, extra, userID, callbackID);
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
     * 初始化广点通SDK
     * @param appid
     * @param result
     */
    public void init(String appid, MethodChannel.Result result){
        this.handlePermissions();
        GDTADManager.getInstance().initWith(this.activity, appid);
        result.success(null);
    }


    /**
     * 显示开屏广告页面
     * @param posID
     * @param result
     */
    public void showSplashAD(String posID, MethodChannel.Result result){
        Intent intent = new Intent();
        intent.setClass(activity, SplashActivity.class);
        intent.putExtra("posID", posID);
        activity.startActivity(intent);
        activity.overridePendingTransition(0,0);
        result.success(null);
    }

    /**
     * 预加载激励视频
     * @param posID
     * @param voice
     * @param extra
     * @param userID
     * @param callbackID
     */
    public void preloadRewardVideo(String posID, Boolean voice, String extra, String userID, final String callbackID){
        RewardVideo.getInstance().preload(this.activity, posID, voice, extra, userID, new GDTCallBack() {
            @Override
            public void call(Map<String, Object> map) {
                MethodCallHandlerGDT.this.callBack("rewardVideo", callbackID ,map);
            }
        });
    }

    /**
     * 播放激励视频
     * @param vid
     * @param result
     */
    public void showRewardVideo(String vid,  MethodChannel.Result result){
        final boolean show =  RewardVideo.getInstance().show(vid);
        result.success(show);
    }

}
