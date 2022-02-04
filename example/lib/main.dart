import 'package:flutter/material.dart';
import 'dart:async';

import 'package:flutter/services.dart';
import 'package:hello/hello.dart';

void main() {
  runApp(const MyApp());
}

class MyApp extends StatefulWidget {
  const MyApp({Key? key}) : super(key: key);

  @override
  State<MyApp> createState() => _MyAppState();
}

class _MyAppState extends State<MyApp> {
  String _platformVersion = 'Unknown';
  String _appVersion = 'unknown';

  @override
  void initState() {
    super.initState();
    initPlatformState();
    initAppState();
  }

  Future<void> initAppState() async {
    String appVersion;
    try {
      appVersion = await Hello.appVersion ?? 'Unknown app version';
    } on PlatformException {
      appVersion = 'Failed to get App version.';
    }
    if (!mounted) return;
    setState(() {
      _appVersion = appVersion;
    });
  }

  Future<void> initPlatformState() async {
    String platformVersion;
    try {
      platformVersion =
          await Hello.platformVersion ?? 'Unknown platform version';
    } on PlatformException {
      platformVersion = 'Failed to get platform version.';
    }
    if (!mounted) return;

    setState(() {
      _platformVersion = platformVersion;
    });
  }

  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      home: Scaffold(
        appBar: AppBar(
          title: const Text('Plugin example app'),
        ),
        body: Center(
          child: Column(
            children: [
              Text('platformVersion: $_platformVersion\n'),
              Text('appVersion: $_appVersion\n'),
              ElevatedButton(
                onPressed: initAppState,
                child: Text('appVersion: $_appVersion\n'),
              )
            ],
          ),
        ),
      ),
    );
  }
}
