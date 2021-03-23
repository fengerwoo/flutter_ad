package com.fenger.ad.flutter_ad;

import androidx.annotation.NonNull;


import com.fenger.ad.flutter_ad.csj.MethodCallHandlerCSJ;
import com.fenger.ad.flutter_ad.gdt.MethodCallHandlerGDT;

import java.util.ArrayList;
import java.util.List;


import io.flutter.embedding.engine.plugins.FlutterPlugin;
import io.flutter.embedding.engine.plugins.activity.ActivityAware;
import io.flutter.embedding.engine.plugins.activity.ActivityPluginBinding;
import io.flutter.embedding.engine.plugins.service.ServiceAware;
import io.flutter.embedding.engine.plugins.service.ServicePluginBinding;
import io.flutter.plugin.common.MethodChannel;



/** FlutterAdPlugin */
public class FlutterAdPlugin implements FlutterPlugin, ActivityAware, ServiceAware {

  private List<LifeAware> needLife = new ArrayList();

  private MethodChannel csjChannel;
  private MethodChannel gdtChannel;


  @Override
  public void onAttachedToEngine(@NonNull FlutterPluginBinding flutterPluginBinding) {

    // 穿山甲插件
    csjChannel = new MethodChannel(flutterPluginBinding.getBinaryMessenger(), "flutter_ad/csj");
    MethodCallHandlerCSJ mchCSJ = new MethodCallHandlerCSJ(csjChannel);
    csjChannel.setMethodCallHandler(mchCSJ);
    needLife.add(mchCSJ);

    // 广点通插件
    gdtChannel = new MethodChannel(flutterPluginBinding.getBinaryMessenger(), "flutter_ad/gdt");
    MethodCallHandlerGDT mchGDT = new MethodCallHandlerGDT(gdtChannel);
    gdtChannel.setMethodCallHandler(mchGDT);
    needLife.add(mchGDT);


    for (LifeAware lifeAware : needLife){
      lifeAware.onAttachedToEngine(flutterPluginBinding);
    }
  }


  @Override
  public void onDetachedFromEngine(@NonNull FlutterPluginBinding binding) {
    csjChannel.setMethodCallHandler(null);
    gdtChannel.setMethodCallHandler(null);


    for (LifeAware lifeAware : needLife){
      lifeAware.onDetachedFromEngine(binding);
    }
  }



  // ---- Activity 生命周期 ---------
  @Override
  public void onAttachedToActivity(@NonNull ActivityPluginBinding binding) {
    for (LifeAware lifeAware : needLife){
      lifeAware.onAttachedToActivity(binding);
    }
  }

  @Override
  public void onDetachedFromActivityForConfigChanges() {
    for (LifeAware lifeAware : needLife){
      lifeAware.onDetachedFromActivityForConfigChanges();
    }
  }

  @Override
  public void onReattachedToActivityForConfigChanges(@NonNull ActivityPluginBinding binding) {
    for (LifeAware lifeAware : needLife){
      lifeAware.onReattachedToActivityForConfigChanges(binding);
    }
  }

  @Override
  public void onDetachedFromActivity() {
    for (LifeAware lifeAware : needLife){
      lifeAware.onDetachedFromActivity();
    }
  }

  // ---- Service 生命周期 ---------
  @Override
  public void onAttachedToService(@NonNull ServicePluginBinding binding) {
    for (LifeAware lifeAware : needLife){
      lifeAware.onAttachedToService(binding);
    }
  }

  @Override
  public void onDetachedFromService() {
    for (LifeAware lifeAware : needLife){
      lifeAware.onDetachedFromService();
    }
  }




}


