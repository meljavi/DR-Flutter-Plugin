package com.dr.plugin.dr_sdk_plugin;

import android.app.Application;

import androidx.annotation.NonNull;

import org.json.JSONObject;

import java.util.HashMap;

import amazonia.iu.com.amlibrary.client.IUApp;
import io.flutter.embedding.engine.plugins.FlutterPlugin;
import io.flutter.plugin.common.MethodCall;
import io.flutter.plugin.common.MethodChannel;
import io.flutter.plugin.common.MethodChannel.MethodCallHandler;
import io.flutter.plugin.common.MethodChannel.Result;
import android.util.Log;
/** DrSdkPlugin */
public class DrSdkPlugin implements FlutterPlugin, MethodCallHandler {

  private MethodChannel channel;
  private static Application application;
  private static Class<?> drComplianceActivity;

  @Override
  public void onAttachedToEngine(@NonNull FlutterPluginBinding flutterPluginBinding) {
    channel = new MethodChannel(flutterPluginBinding.getBinaryMessenger(), "dr_sdk_plugin");
    channel.setMethodCallHandler(this);
  }

  public static void init(Application application,Class<?> drComplianceActivity) {
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
        Log.d("TestIU","DrSdkPlugin call invokeFCM()");
        try{
        IUApp.init(application, drComplianceActivity);
        HashMap<String, Object> hashMap = (HashMap<String, Object>) call.arguments;
        HashMap<String, Object> finalMap=  (HashMap<String, Object>)hashMap.get("remoteMessage");
        JSONObject json =new JSONObject(finalMap);
        IUApp.handleFCM(application, json);
        }catch (Exception e){
          e.printStackTrace();
        }
      finally {
          result.success(null);
        }
        break;
      case "launch":
        Log.d("TestIU","DrSdkPlugin call init()");
        IUApp.init(application, drComplianceActivity);
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
