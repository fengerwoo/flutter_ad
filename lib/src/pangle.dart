
import 'dart:async';

import 'package:flutter/services.dart';
import 'package:flutter_ad/src/response.dart';


/// 穿山甲 SDK
/// Created by 枫儿 on 2021/3/23.
/// @email：hsnndly@163.com
class Pangle{

  static Pangle _pangle;
  static Pangle getInstance(){
    if(_pangle == null){
      _pangle = Pangle();
      _pangle._initDartCall();
    }
    return _pangle;
  }

  MethodChannel _channel = MethodChannel("flutter_ad/csj");
  StreamController<Response>_dartCallBackController = new StreamController.broadcast();
  Stream<Response> get responseEventHandler => _dartCallBackController.stream;

  void _initDartCall(){
    _channel.setMethodCallHandler((MethodCall call){
      if(call.method == "dartCallBack"){
        Map args = call.arguments;
        Response res = Response(type: args['type'], callbackID: args['callback_id'], data: args['data']);
        _dartCallBackController.add(res);
      }
      return null;
    });
  }

  /// @Desc  : 初始化穿山甲SDK
  /// @author: 枫儿
  void init(String appID) async{
    await _channel.invokeMapMethod("init", {
      "appID": appID,
    });
  }

  /// @Desc  : 显示开屏广告
  /// @author: 枫儿
  void showSplashAD(String codeID) async{
    await _channel.invokeMapMethod("showSplashAD", {"codeID": codeID});
  }


  /// @Desc  : 预加载激励视频
  /// @author: 枫儿
  void preloadRewardVideo(String codeID, {
    int adCount,
    String rewardName,
    int rewardAmount,
    String userID,
    String extra,
    String callbackID,
  }) async{

    await _channel.invokeMapMethod("preloadRewardVideo", {
      "codeID": codeID,
      "adCount": adCount,
      "rewardName": rewardName,
      "rewardAmount": rewardAmount,
      "userID": userID,
      "extra": extra,
      "callbackID": callbackID,
    });
  }

  /// @Desc  : 播放激励视频
  /// @author: 枫儿
  Future<bool> showRewardVideo(String vid) async{
    bool ret = (await _channel.invokeMapMethod("showRewardVideo", {"vid": vid})) as bool;
    return ret;
  }



}