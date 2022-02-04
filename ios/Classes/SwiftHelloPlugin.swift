import Flutter
import UIKit

public class SwiftHelloPlugin: NSObject, FlutterPlugin {
  public static func register(with registrar: FlutterPluginRegistrar) {
    let channel = FlutterMethodChannel(name: "hello", binaryMessenger: registrar.messenger())
    let instance = SwiftHelloPlugin()
    registrar.addMethodCallDelegate(instance, channel: channel)
  }

  public func handle(_ call: FlutterMethodCall, result: @escaping FlutterResult) {
      
      switch call.method {
           case "getDeviceInfo":
             result("getDeviceInfo:iOS " + UIDevice.current.systemVersion)
           case "getPlatformVersion":
             result("getPlatformVersion:iOS " + UIDevice.current.systemVersion)
           default:
             result("iOS d " + UIDevice.current.systemVersion)
         }
  }


}
