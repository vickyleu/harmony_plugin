import 'harmony_plugin_platform_interface.dart';

class HarmonyPlugin {
  Future<bool> isHarmonyOS() {
    return HarmonyPluginPlatform.instance.isHarmonyOS();
  }

  Future<String> getHarmonyVersion() {
    return HarmonyPluginPlatform.instance.getHarmonyVersion();
  }
  Future<String> getHarmonyDisplayVersion() {
    return HarmonyPluginPlatform.instance.getHarmonyDisplayVersion();
  }

  Future<String> getHarmonyDisplayVersion() {
    return HarmonyPluginPlatform.instance.getHarmonyDisplayVersion();
  }

  Future<bool> isHarmonyPureMode() {
    return HarmonyPluginPlatform.instance.isHarmonyPureMode();
  }
}
