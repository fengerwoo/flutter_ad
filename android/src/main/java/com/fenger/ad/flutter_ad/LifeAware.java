package com.fenger.ad.flutter_ad;

import androidx.annotation.NonNull;

import io.flutter.embedding.engine.plugins.FlutterPlugin;
import io.flutter.embedding.engine.plugins.activity.ActivityPluginBinding;
import io.flutter.embedding.engine.plugins.service.ServicePluginBinding;

public interface LifeAware  {
    // 引擎生命周期
    public void onAttachedToEngine(@NonNull FlutterPlugin.FlutterPluginBinding flutterPluginBinding);
    public void onDetachedFromEngine(@NonNull FlutterPlugin.FlutterPluginBinding binding);

    // ---- Activity 生命周期 ---------
    public void onAttachedToActivity(@NonNull ActivityPluginBinding binding);
    public void onDetachedFromActivityForConfigChanges();
    public void onReattachedToActivityForConfigChanges(@NonNull ActivityPluginBinding binding);
    public void onDetachedFromActivity();

    // ---- Service 生命周期 ---------
    public void onAttachedToService(@NonNull ServicePluginBinding binding);
    public void onDetachedFromService();

}
