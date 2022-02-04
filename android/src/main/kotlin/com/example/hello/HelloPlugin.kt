package com.example.hello

import androidx.annotation.NonNull

import io.flutter.embedding.engine.plugins.FlutterPlugin
import io.flutter.plugin.common.MethodCall
import io.flutter.plugin.common.MethodChannel
import io.flutter.plugin.common.MethodChannel.MethodCallHandler
import io.flutter.plugin.common.MethodChannel.Result
import io.flutter.embedding.engine.plugins.activity.ActivityAware
import io.flutter.embedding.engine.plugins.activity.ActivityPluginBinding

import android.app.Activity;
import android.content.Context
import android.content.pm.PackageManager;

/** ① AcitvityAwareのインタフェースを追加する。クラス名は適宜変更すること */
class HelloPlugin: FlutterPlugin, MethodCallHandler, ActivityAware {
  private lateinit var channel : MethodChannel

  // ② 他のメソッドでも利用できるようにプラグイン用のクラスのメンバ変数を定義
  private lateinit var activity : Activity
   private lateinit var context : Context

  override fun onAttachedToEngine(@NonNull flutterPluginBinding: FlutterPlugin.FlutterPluginBinding) {
    channel = MethodChannel(flutterPluginBinding.binaryMessenger, "hello")
    channel.setMethodCallHandler(this)
    context = flutterPluginBinding.applicationContext

    // ✨✨ ③ Flutterエンジンにattachされたタイミングでメンバ変数にbindingされたContextを格納
    // context = flutterPluginBinding.applicationContext
  }

  override fun onMethodCall(@NonNull call: MethodCall, @NonNull result: Result) {
    // ✨✨ ⑥ ContextやActivityを使用して各々好きに実装する
    // 中略
    if (call.method == "getPlatformVersion") {
      result.success("Android ${android.os.Build.VERSION.RELEASE}")
    } else  if(call.method == "getAppVersion"){
      try {
        // android.content.Context#getPackageName
        val name = activity.packageName

        // インストールされているアプリケーションパッケージの
        // 情報を取得するためのオブジェクトを取得
        // android.content.Context#getPackageManager
        val pm = activity.packageManager
        // アプリケーションパッケージの情報を取得
        val info = pm.getPackageInfo(name, PackageManager.GET_META_DATA)
        // バージョン番号の文字列を返す
        info.versionName
        result.success("Android ${name}${ info.versionName}")
      } catch (e: PackageManager.NameNotFoundException) {
        e.printStackTrace()
        null
      }
      result.notImplemented()
    }else  if(call.method == "getAppVersion2"){
      var version = ""
      try {
        val pInfo = context.packageManager.getPackageInfo(context.packageName, 0)
        version = pInfo.versionName
      } catch (e: PackageManager.NameNotFoundException) {
        result.success("a${version}")
      }
result.success("a${version}")

    }else if(call.method == "getAppVersion3") {
      var appInfo2 =  AppInfo2()
      var name2 = AppInfo2().getVersionName(activity)
      var name = appInfo2.getVersionName(activity)
      result.success("${name}")
    }else {
      result.notImplemented()
    }
  }

  override fun onDetachedFromEngine(@NonNull binding: FlutterPlugin.FlutterPluginBinding) {
    channel.setMethodCallHandler(null)
  }

  //
  override fun onAttachedToActivity(binding: ActivityPluginBinding) {
    // ✨✨ ④ Activityがattachされたタイミングでメンバ変数にbindingされたActivityを格納
    activity = binding.activity
  }

  // ✨✨ ⑤ 以下のコールバック群は、ActivityAwareのインタフェースを実装する上で定義必須だが今回は使用しないため、TODOレベルで記載しておく
  override fun onDetachedFromActivityForConfigChanges() {
  }

  override fun onReattachedToActivityForConfigChanges(binding: ActivityPluginBinding) {
  }

  override fun onDetachedFromActivity() {
  }
}



class AppInfo2 {
  fun getVersionName(activity: Activity): String? {
    return try {
      val pm = activity.packageManager
      val info = pm.getPackageInfo(activity.packageName, PackageManager.GET_META_DATA)
      info.versionName
    } catch (e: PackageManager.NameNotFoundException) {
      e.printStackTrace()
      null
    }
  }
}
