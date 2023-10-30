package com.static4u.harmony_plugin

import android.app.Activity
import io.flutter.embedding.engine.plugins.FlutterPlugin
import io.flutter.embedding.engine.plugins.activity.ActivityAware
import io.flutter.embedding.engine.plugins.activity.ActivityPluginBinding
import io.flutter.plugin.common.MethodCall
import io.flutter.plugin.common.MethodChannel
import io.flutter.plugin.common.MethodChannel.Result
import io.flutter.plugin.common.MethodChannel.MethodCallHandler

/** HarmonyPlugin */
class HarmonyPlugin : FlutterPlugin, MethodCallHandler, ActivityAware {
    /// The MethodChannel that will the communication between Flutter and native Android
    ///
    /// This local reference serves to register the plugin with the Flutter Engine and unregister it
    /// when the Flutter Engine is detached from the Activity
    private lateinit var channel: MethodChannel
    private lateinit var activity: Activity

    override fun onAttachedToEngine(flutterPluginBinding: FlutterPlugin.FlutterPluginBinding) {
        channel = MethodChannel(flutterPluginBinding.binaryMessenger, "com.static4u.harmony_plugin")
        channel.setMethodCallHandler(this)
    }

    override fun onMethodCall(call: MethodCall, result: Result) {
        if (call.method == "isHarmonyOS") {
            try {
                result.success(HarmonyUtil.isHarmonyOs)
            } catch (e: Exception) {
                e.printStackTrace()
                result.error("isHarmonyOS", e.message, e)
            }
        } else if (call.method == "getHarmonyVersion") {
            try {
                result.success(HarmonyUtil.harmonyVersion)
            } catch (e: Exception) {
                e.printStackTrace()
                result.error("getHarmonyVersion", e.message, e)
            }
        } else if (call.method == "getHarmonyDisplayVersion") {
            try {
                result.success(HarmonyUtil.harmonyDisplayVersion)
            } catch (e: Exception) {
                e.printStackTrace()
                result.error("getHarmonyDisplayVersion", e.message, e)
            }
        } else if (call.method == "isHarmonyPureMode") {
            try {
                result.success(HarmonyUtil.isPureMode(activity))
            } catch (e: Exception) {
                e.printStackTrace()
                result.error("isHarmonyPureMode", e.message, e)
            }
        } else {
            result.notImplemented()
        }
    }

    override fun onDetachedFromEngine(binding: FlutterPlugin.FlutterPluginBinding) {
        channel.setMethodCallHandler(null)
    }

    override fun onAttachedToActivity(binding: ActivityPluginBinding) {
        activity = binding.activity
    }

    override fun onDetachedFromActivity() {
    }

    override fun onDetachedFromActivityForConfigChanges() {
        onDetachedFromActivity()
    }

    override fun onReattachedToActivityForConfigChanges(binding: ActivityPluginBinding) {
        onAttachedToActivity(binding)
    }
}
