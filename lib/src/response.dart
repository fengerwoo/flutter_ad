import 'dart:math';

/// 结果Model
/// Created by 枫儿 on 2021/3/23.
/// @email：hsnndly@163.com
class Response{
  String type;
  String callbackID;
  Map data;

  Response({
    this.type,
    this.callbackID,
    this.data,
  });


  /// @Desc  : 生成随机字符串，可传入字符串长度，默认32位
  /// @author: 枫儿
  static String randomID({int stringLenght = 32}){
    String alphabet = 'qwertyuiopasdfghjklzxcvbnmQWERTYUIOPASDFGHJKLZXCVBNM1234567890';
    String left = "";
    for (var i = 0; i < stringLenght; i++) {
      left = left + alphabet[Random().nextInt(alphabet.length)];
    }
    return left;
  }

}


class ResponseType{
  static String rewardVideo = 'rewardVideo';
}