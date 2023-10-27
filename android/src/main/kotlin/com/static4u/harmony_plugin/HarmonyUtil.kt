package com.static4u.harmony_plugin

import android.annotation.SuppressLint
import android.content.Context
import android.os.Build
import android.provider.Settings
import android.text.TextUtils
import android.text.TextUtils.substring
import android.util.Log

/**
 * 获取鸿蒙系统信息
 */
object HarmonyUtil {
  val isHarmonyOs: Boolean
    /**
     * 是否为鸿蒙系统
     *
     * @return true为鸿蒙系统
     */
    get() = try {
      val buildExClass = Class.forName("com.huawei.system.BuildEx")
      val osBrand = buildExClass.getMethod("getOsBrand").invoke(buildExClass)
      if (osBrand == null) {
        false
      } else {
        val isHarmony = "Harmony".equals(osBrand.toString(), ignoreCase = true)
        Log.i("HarmonyUtil", "当前设备是鸿蒙系统")
        isHarmony
      }
    } catch (e: Exception) {
      e.printStackTrace()
      false
    }
  val harmonyVersion: String
    /**
     * 获取鸿蒙系统版本号
     *
     * @return 版本号
     */
    get() = getProp("hw_sc.build.platform.version", "")

  val harmonyDisplayVersion: String
    /**
     * 获取鸿蒙系统版本号
     *
     * @return 版本号
     */
    get() {
      return Build.DISPLAY.let {
        //ELS-AN00 2.0.0.222(CO0E206R3P6)
        //切分出2.0.0.222
        var text = it
        if (it.contains("(")) {//分离出ELS-AN00 2.0.0.222
          text = text.substring(0, it.indexOf("("))
        }
        if (text.contains(" ")) {//再分离出2.0.0.222
          text = text.substring(text.lastIndexOf(" ") + 1, text.length)
        }
        text.replace(" ","")
      }
    }

  @SuppressLint("PrivateApi")
  private fun getProp(property: String, defaultValue: String): String {
    try {
      val spClz = Class.forName("android.os.SystemProperties")
      val method = spClz.getDeclaredMethod("get", String::class.java)
      val value = method.invoke(spClz, property) as String
      if (TextUtils.isEmpty(value)) {
        return defaultValue
      }
      Log.i("HarmonyUtil", "当前设备是鸿蒙" + value + "系统")
      return value
    } catch (e: Exception) {
      e.printStackTrace()
    }
    return defaultValue
  }

  /**
   * 判断是否开启鸿蒙纯净模式
   */
  fun isPureMode(context: Context?): Boolean {
    var result = false
    if (!isHarmonyOs) {
      return false
    }
    try {
      if (context != null) {
        result = 0 == Settings.Secure.getInt(context.contentResolver, "pure_mode_state", 0)
      }
    } catch (e: Exception) {
      e.printStackTrace()
    }
    return result
  }
}