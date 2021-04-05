import 'dart:io';

import 'package:flutter/material.dart';
import 'package:flutter/services.dart';
import 'package:flutter_ad/flutter_ad.dart';

class PangleBanner extends StatelessWidget {

  final String adid;
  final bool supportDeepLink;
  final int expressAdNum;
  final int expressTime;
  final double expressViewWidth;
  final double expressViewHeight;
  final ValueChanged<Response> callBack;

  PangleBanner({
    Key key,
    @required this.adid,
    this.supportDeepLink = false,
    this.expressAdNum = 1,
    this.expressTime = 30,
    @required this.expressViewWidth,
    @required this.expressViewHeight,
    this.callBack,
  }): super(key: key);

  @override
  Widget build(BuildContext context) {
    if(Platform.isAndroid){
      return AndroidView(
        viewType: "flutter_ad/csj/banner",
        creationParams: {
          "adid": adid,
          "supportDeepLink": supportDeepLink,
          "expressAdNum": expressAdNum,
          "expressTime": expressTime,
          "expressViewWidth": expressViewWidth,
          "expressViewHeight": expressViewHeight,
        },
        creationParamsCodec: StandardMessageCodec(),
      );
    }else{
      return Container();
    }
  }
}

