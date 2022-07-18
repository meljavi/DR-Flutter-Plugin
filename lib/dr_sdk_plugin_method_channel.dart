import 'package:flutter/foundation.dart';
import 'package:flutter/services.dart';

import 'dr_sdk_plugin_platform_interface.dart';

/// An implementation of [DrSdkPluginPlatform] that uses method channels.
class MethodChannelDrSdkPlugin extends DrSdkPluginPlatform {
  /// The method channel used to interact with the native platform.
  @visibleForTesting
  final methodChannel = const MethodChannel('dr_sdk_plugin');

  @override
  Future<String?> getPlatformVersion() async {
    final version = await methodChannel.invokeMethod<String>('getPlatformVersion');
    return version;
  }

  @override
  void invokeFCM(Map<String, dynamic> parameters) {
    methodChannel.invokeMethod('invokeFCM',
        {'remoteMessage':parameters});
  }

  @override
  void launch() {
    methodChannel.invokeMethod('launch');
  }

  @override
  void init() {
    methodChannel.invokeMethod('init');
  }

  @override
  void refreshToken() {
    methodChannel.invokeMethod('refreshToken');
  }
}
