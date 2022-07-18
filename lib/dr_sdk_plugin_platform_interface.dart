import 'package:plugin_platform_interface/plugin_platform_interface.dart';

import 'dr_sdk_plugin_method_channel.dart';

abstract class DrSdkPluginPlatform extends PlatformInterface {
  /// Constructs a DrSdkPluginPlatform.
  DrSdkPluginPlatform() : super(token: _token);

  static final Object _token = Object();

  static DrSdkPluginPlatform _instance = MethodChannelDrSdkPlugin();

  /// The default instance of [DrSdkPluginPlatform] to use.
  ///
  /// Defaults to [MethodChannelDrSdkPlugin].
  static DrSdkPluginPlatform get instance => _instance;
  
  /// Platform-specific implementations should set this with their own
  /// platform-specific class that extends [DrSdkPluginPlatform] when
  /// they register themselves.
  static set instance(DrSdkPluginPlatform instance) {
    PlatformInterface.verifyToken(instance, _token);
    _instance = instance;
  }

  Future<String?> getPlatformVersion() {
    throw UnimplementedError('platformVersion() has not been implemented.');
  }

  void refreshToken(){
    throw UnimplementedError('refresh() has not been implemented.');
  }

  void init(){
    throw UnimplementedError('init() has not been implemented.');
  }

  void launch(){
    throw UnimplementedError('launch() has not been implemented.');
  }

  void invokeFCM(Map<String, dynamic> parameters){
    throw UnimplementedError('invokeFCM() has not been implemented.');
  }

}
