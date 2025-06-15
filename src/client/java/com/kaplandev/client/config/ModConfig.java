package com.kaplandev.client.config;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import net.fabricmc.loader.api.FabricLoader;

public class ModConfig {
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
    private static Path configPath;

    public static boolean showAKOTOriginalMessage = true;

    public static void initialize() {
        try {
            configPath = FabricLoader.getInstance().getConfigDir().resolve("mobpvp_config.json");

            // Config klasörü yoksa oluştur
            Files.createDirectories(configPath.getParent());

            // Config dosyası yoksa oluştur
            if (!Files.exists(configPath)) {
                save();
            } else {
                load();
            }
        } catch (IOException e) {
            System.err.println("MobPVP config başlatılamadı: " + e.getMessage());
        }
    }

    public static void save() {
        try {
            ConfigData data = new ConfigData(showAKOTOriginalMessage);
            String json = GSON.toJson(data);
            Files.writeString(configPath, json);
        } catch (IOException e) {
            System.err.println("MobPVP config kaydedilemedi: " + e.getMessage());
        }
    }

    public static void load() {
        try {
            String json = Files.readString(configPath);
            ConfigData data = GSON.fromJson(json, ConfigData.class);
            if (data != null) {
                showAKOTOriginalMessage = data.showAKOTOriginalMessage;
            }
        } catch (IOException e) {
            System.err.println("MobPVP config yüklenemedi: " + e.getMessage());
        }
    }

    private static class ConfigData {
        boolean showAKOTOriginalMessage;

        public ConfigData() {} // GSON için boş constructor

        ConfigData(boolean showAKOTOriginalMessage) {
            this.showAKOTOriginalMessage = showAKOTOriginalMessage;
        }
    }
}