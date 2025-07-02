package com.kaplandev.api;

public interface MobPvPInitializer {
    default void onLoad() {
        // Mod yüklendiğinde çalışır.
    }

    default void onClose() {
        // Mod kapanırken çalışır.
    }

}
