import 'package:flutter_test/flutter_test.dart';
import 'package:dr_sdk_plugin/dr_sdk_plugin.dart';
import 'package:dr_sdk_plugin/dr_sdk_plugin_platform_interface.dart';
import 'package:dr_sdk_plugin/dr_sdk_plugin_method_channel.dart';
import 'package:plugin_platform_interface/plugin_platform_interface.dart';

class MockDrSdkPluginPlatform 
    with MockPlatformInterfaceMixin
    implements DrSdkPluginPlatform {

  @override
  Future<String?> getPlatformVersion() => Future.value('42');
}

void main() {
  final DrSdkPluginPlatform initialPlatform = DrSdkPluginPlatform.instance;

  test('$MethodChannelDrSdkPlugin is the default instance', () {
    expect(initialPlatform, isInstanceOf<MethodChannelDrSdkPlugin>());
  });

  test('getPlatformVersion', () async {
    DrSdkPlugin drSdkPlugin = DrSdkPlugin();
    MockDrSdkPluginPlatform fakePlatform = MockDrSdkPluginPlatform();
    DrSdkPluginPlatform.instance = fakePlatform;
  
    expect(await drSdkPlugin.getPlatformVersion(), '42');
  });
}
