import 'dart:async';

import 'package:flutter/services.dart';

import '../flutter_ad.dart';

/// 广点通 SDK
/// Created by 枫儿 on 2021/3/23.
/// @email：hsnndly@163.com
class UnionGDT{

  static UnionGDT _unionGDT;
  static UnionGDT getInstance(){
    if(_unionGDT == null){
      _unionGDT = UnionGDT();
      _unionGDT._initDartCall();
    }
    return _unionGDT;
  }

  MethodChannel _channel = MethodChannel("flutter_ad/gdt");
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


  /// @Desc  : 初始化广点通SDK
  /// @author: 枫儿
  void init(String appID) async{
    await _channel.invokeMapMethod("init", {
      "appID": appID,
    });
  }

  /// @Desc  : 显示开屏广告
  /// @author: 枫儿
  void showSplashAD(String posID) async{
    await _channel.invokeMapMethod("showSplashAD", {"posID": posID});
  }


  /// @Desc  : 预加载激励视频
  /// @author: 枫儿
  void preloadRewardVideo(String posID, {
    bool voice = true,
    String extra,
    String userID,
    String callbackID,
  }) async{

    await _channel.invokeMapMethod("preloadRewardVideo", {
      "posID": posID,
      "voice": voice,
      "extra": extra,
      "userID": userID,
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