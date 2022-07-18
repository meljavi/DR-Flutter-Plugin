
import 'dr_sdk_plugin_platform_interface.dart';

class DrSdkPlugin {
  Future<String?> getPlatformVersion() {
    return DrSdkPluginPlatform.instance.getPlatformVersion();
  }

  void refreshToken(){
    DrSdkPluginPlatform.instance.refreshToken();
  }

  void init(){
    DrSdkPluginPlatform.instance.init();
  }

  void launch(){
    DrSdkPluginPlatform.instance.launch();
  }

  void invokeFCM(Map<String, dynamic> parameters){
    DrSdkPluginPlatform.instance.invokeFCM(parameters);
  }
}
