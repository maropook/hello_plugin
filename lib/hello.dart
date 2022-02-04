import 'dart:async';

import 'package:flutter/services.dart';

class Hello {
  static const MethodChannel _channel = MethodChannel('hello');

  static Future<String?> get platformVersion async {
    final String? version = await _channel.invokeMethod('getPlatformVersion');
    return version;
  }

  static Future<String?> get appVersion async {
    final String? version = await _channel.invokeMethod('getAppVersion');
    return version;
  }
}
