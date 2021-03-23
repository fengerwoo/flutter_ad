import 'package:flutter/services.dart';
import 'package:flutter_test/flutter_test.dart';
import 'package:flutter_ad/flutter_ad.dart';

void main() {
  const MethodChannel channel = MethodChannel('flutter_ad');

  TestWidgetsFlutterBinding.ensureInitialized();

  setUp(() {
    channel.setMockMethodCallHandler((MethodCall methodCall) async {
      return '42';
    });
  });

  tearDown(() {
    channel.setMockMethodCallHandler(null);
  });

  test('getPlatformVersion', () async {
    // expect(await FlutterAd.platformVersion, '42');
    String a =  Response.randomID();
    print(a);
  });
}
