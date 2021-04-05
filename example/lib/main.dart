import 'package:flutter/material.dart';
import 'dart:async';

import 'package:flutter/services.dart';
import 'package:flutter_ad/flutter_ad.dart';
import 'package:flutter_ad_example/pangle_view_demo.dart';
import 'package:logger/logger.dart';
import 'package:logger_flutter/logger_flutter.dart';

void main() {
  runApp(MyApp());
}

class MyApp extends StatefulWidget {
  @override
  _MyAppState createState() => _MyAppState();
}

class _MyAppState extends State<MyApp> {


  @override
  void initState() {
    super.initState();
  }

  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      home: HomePage(),
    );
  }
}



class HomePage extends StatelessWidget {

  var logger = Logger(printer: PrettyPrinter(printTime: true));

  String pangleRewardVideoID;
  String unionGDTRewardVideoID;


  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: const Text('flutter_ad Demo'),
      ),
      body: SingleChildScrollView(
          child: Center(
            child: Column(
              children: [

                Card(
                  child: Column(
                    children: [
                      ListTile(title: Text("穿山甲"), subtitle: Text("穿山甲的广告接口演示，请先点击初始化接口"),),
                      Container(
                        // width: double.infinity,
                        child: Wrap(
                          crossAxisAlignment: WrapCrossAlignment.start,
                          spacing: 10,
                          children: [

                            ElevatedButton.icon(
                                onPressed: () async {
                                  /// 初始化调用示例
                                  await Pangle.getInstance().init("5056758");
                                  logger.i("穿山甲 - 初始化调用成功");
                                },
                                icon: Icon(Icons.home_filled),
                                label: Text("初始化接口")),

                            ElevatedButton.icon(
                                onPressed: () async{
                                  /// 调用开屏广告示例
                                  await Pangle.getInstance().showSplashAD("887310537");
                                  logger.i("穿山甲 - 开屏广告调用成功");
                                },
                                icon: Icon(Icons.web_asset),
                                label: Text("显示开屏广告")),

                            ElevatedButton.icon(
                                onPressed: ()async{
                                  /// 预加载激励视频示例
                                  String callBackID =  Response.randomID();
                                  Pangle.getInstance().preloadRewardVideo("945122969", adCount: 1, rewardName: "书币", rewardAmount: 1, userID: "100", extra: "20", callbackID: callBackID);
                                  Pangle.getInstance().responseEventHandler.listen((Response response) {
                                    if(response.callbackID == callBackID && response.data['code'] == 1){
                                      pangleRewardVideoID = response.data['data']['vid'];
                                    }
                                    logger.i("穿山甲 - 激励视频回调：${response.data}");
                                  });

                                  logger.i("穿山甲 - 预加载激励视频调用成功，callBackID：${callBackID}");
                                },
                                icon: Icon(Icons.cloud_download),
                                label: Text("预加载激励视频")),

                            ElevatedButton.icon(
                                onPressed: (){
                                  if(pangleRewardVideoID != null){
                                    /// 播放激励视频示例（需要传入从预加载回调 code为1 里带的vid）
                                    Pangle.getInstance().showRewardVideo(pangleRewardVideoID);
                                    logger.i("穿山甲 - 显示激励视频调用成功");
                                  }
                                },
                                icon: Icon(Icons.play_arrow),
                                label: Text("播放激励视频")),


                            ElevatedButton.icon(
                                onPressed: (){
                                  Navigator.push(context, MaterialPageRoute(builder: (_)=> PangleBannerHome() ));
                                },
                                icon: Icon(Icons.pages),
                                label: Text("Banner广告")),

                          ],
                        ),
                      ),
                    ],
                  ),
                ),




                Card(
                  child: Column(
                    children: [
                      ListTile(title: Text("广点通"), subtitle: Text("广点通的广告接口演示，请先点击初始化接口"),),
                      Container(
                        // width: double.infinity,
                        child: Wrap(
                          crossAxisAlignment: WrapCrossAlignment.start,
                          spacing: 10,
                          children: [

                            ElevatedButton.icon(
                                onPressed: () async {
                                  /// 初始化调用示例
                                  await UnionGDT.getInstance().init("1101152570");
                                },
                                icon: Icon(Icons.home_filled),
                                label: Text("初始化接口")),

                            ElevatedButton.icon(
                                onPressed: () async{
                                  /// 调用开屏广告示例
                                  UnionGDT.getInstance().showSplashAD("8863364436303842593");
                                },
                                icon: Icon(Icons.web_asset),
                                label: Text("显示开屏广告")),

                            ElevatedButton.icon(
                                onPressed: ()async{
                                  /// 预加载激励视频示例
                                  String callBackID =  Response.randomID();
                                  UnionGDT.getInstance().preloadRewardVideo("6040295592058680", voice: true, extra: "123", userID: "123", callbackID: callBackID);
                                  UnionGDT.getInstance().responseEventHandler.listen((Response response) {
                                    if(response.callbackID == callBackID && response.data['code'] == 1){
                                      unionGDTRewardVideoID = response.data['data']['vid'];
                                    }
                                  });

                                },
                                icon: Icon(Icons.cloud_download),
                                label: Text("预加载激励视频")),

                            ElevatedButton.icon(
                                onPressed: (){
                                  if(unionGDTRewardVideoID != null){
                                    /// 播放激励视频示例（需要传入从预加载回调 code为1 里带的vid）
                                    UnionGDT.getInstance().showRewardVideo(unionGDTRewardVideoID);
                                  }
                                },
                                icon: Icon(Icons.play_arrow),
                                label: Text("播放激励视频")),

                          ],
                        ),
                      ),
                    ],
                  ),
                ),

                Container(
                  margin: EdgeInsets.only(top: 20),
                  child: LogConsoleOnShake(
                    dark: true,
                    child: Center(
                      child: Text("摇一摇手机，查看控制台日志", style: TextStyle(color: Colors.red, fontWeight: FontWeight.w700, fontSize: 15),),
                    ),
                  ),
                ),

              ],
            ),
          )
      ),
    );
  }
}
