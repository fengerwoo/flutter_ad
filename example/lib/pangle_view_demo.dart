
import 'package:flutter/material.dart';
import 'package:flutter_ad/flutter_ad.dart';

class PangleBannerHome extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: Text("穿山甲Banner广告"),
      ),

      body: PangleBanner(
        adid: "945330217",
        expressViewWidth: MediaQuery.of(context).size.width,
        expressViewHeight: 200,
      ),

    );
  }
}
