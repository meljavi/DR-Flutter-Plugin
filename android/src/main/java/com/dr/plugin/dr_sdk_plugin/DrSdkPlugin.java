package com.dr.plugin.dr_sdk_plugin;

import android.app.Application;

import androidx.annotation.NonNull;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

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
    IUApp.init(application, drComplianceActivity);
  }

  @Override
  public void onMethodCall(@NonNull MethodCall call, @NonNull Result result) {

    switch (call.method){
      case "refreshToken":
        IUApp.refreshFCMToken(application);
        break;
      case "invokeFCM":
        Log.d("TestIU","DrSdkPlugin Updated call invokeFCM()");
        try{
        @SuppressWarnings("unchecked")
        HashMap<String, Object> hashMap = (HashMap<String, Object>) call.arguments;
        @SuppressWarnings("unchecked")
        HashMap<String, Object> finalMap=  (HashMap<String, Object>)hashMap.get("remoteMessage");
        JSONObject json =new JSONObject(finalMap);
          ExecutorService executorService= Executors.newSingleThreadExecutor();
          executorService.submit(()->{
            IUApp.handleFCMMessage(application, json);
          });
          executorService.shutdown();

        }catch (Exception e){
          e.printStackTrace();
        }
      finally {
          result.success(null);
        }
        break;
      case "launch":
        IUApp.launch(application);
        break;
      case "init":
        Log.d("TestIU","DrSdkPlugin call init()");
        IUApp.init(application, drComplianceActivity);
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
