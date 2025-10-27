package com.kaplanlib.api;

import com.kaplanlib.api.rules.KaplanBedwars;

import java.util.ArrayList;
import java.util.List;
/**
 * Eklentileri yöneten basit sistem.
 */
public class PluginRegistry {

    private static final List<MobPvPInitializer> PLUGINS = new ArrayList<>();

    public static void register(MobPvPInitializer plugin) {
        KaplanBedwars.validateKaplanInternal(plugin.getClass());
        PLUGINS.add(plugin);
    }

    public static void callOnLoad() {
        for (MobPvPInitializer plugin : PLUGINS) {
            plugin.onLoad();
        }
    }

    public static void callOnClose() {
        for (MobPvPInitializer plugin : PLUGINS) {
            plugin.onClose();
        }
    }
}
