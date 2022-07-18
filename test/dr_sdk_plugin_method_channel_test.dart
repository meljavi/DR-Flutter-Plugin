import 'package:flutter/services.dart';
import 'package:flutter_test/flutter_test.dart';
import 'package:dr_sdk_plugin/dr_sdk_plugin_method_channel.dart';

void main() {
  MethodChannelDrSdkPlugin platform = MethodChannelDrSdkPlugin();
  const MethodChannel channel = MethodChannel('dr_sdk_plugin');

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
    expect(await platform.getPlatformVersion(), '42');
  });
}
