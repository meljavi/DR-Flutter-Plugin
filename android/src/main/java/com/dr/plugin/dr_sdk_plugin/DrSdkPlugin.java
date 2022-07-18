package com.dr.plugin.dr_sdk_plugin;

import android.app.Application;

import androidx.annotation.NonNull;

import org.json.JSONObject;

import java.util.HashMap;

import amazonia.iu.com.amlibrary.activities.DRComplianceActivity;
import amazonia.iu.com.amlibrary.client.IUApp;
import io.flutter.embedding.engine.plugins.FlutterPlugin;
import io.flutter.plugin.common.MethodCall;
import io.flutter.plugin.common.MethodChannel;
import io.flutter.plugin.common.MethodChannel.MethodCallHandler;
import io.flutter.plugin.common.MethodChannel.Result;

/** DrSdkPlugin */
public class DrSdkPlugin implements FlutterPlugin, MethodCallHandler {
  /// The MethodChannel that will the communication between Flutter and native Android
  ///
  /// This local reference serves to register the plugin with the Flutter Engine and unregister it
  /// when the Flutter Engine is detached from the Activity
  private MethodChannel channel;
  private static Application application;
  private static DRComplianceActivity drComplianceActivity;

  @Override
  public void onAttachedToEngine(@NonNull FlutterPluginBinding flutterPluginBinding) {
    channel = new MethodChannel(flutterPluginBinding.getBinaryMessenger(), "dr_sdk_plugin");
    channel.setMethodCallHandler(this);
  }

  public static void init(Application application,DRComplianceActivity drComplianceActivity) {
    DrSdkPlugin.application = application;
    DrSdkPlugin.drComplianceActivity=drComplianceActivity;
  }

  @Override
  public void onMethodCall(@NonNull MethodCall call, @NonNull Result result) {

    switch (call.method){
      case "getPlatformVersion":
        result.success("Android " + android.os.Build.VERSION.RELEASE);
        break;
      case "refreshToken":
        IUApp.refreshFCMToken(application);
        break;
      case "invokeFCM":
        try{
        HashMap<String, Object> hashMap = (HashMap<String, Object>) call.arguments;
        HashMap<String, Object> finalMap=  (HashMap<String, Object>)hashMap.get("remoteMessage");
          JSONObject json =new JSONObject(finalMap);
          IUApp.handleFCMMessage(application, json);
        }catch (Exception e){
          e.printStackTrace();
        }
      finally {
          result.success(null);
        }
        break;
      case "launch":
        IUApp.init(application, null);
        break;
      case "init":
        IUApp.launch(application);
        break;
      default:
        result.notImplemented();
    }
  }

  @Override
  public void onDetachedFromEngine(@NonNull FlutterPluginBinding binding) {
    channel.setMethodCallHandler(null);
  }
}
