package com.kaplandev.client;

import com.kaplandev.client.renderer.CustomZombieRenderer;
import com.kaplandev.client.InfoSys.dink;
import com.kaplandev.client.config.ModConfig;
import com.kaplandev.client.gui.WelcomeScreen;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientLifecycleEvents;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.TitleScreen;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.entity.EntityType;

public class mobpvpClient implements ClientModInitializer {
    private boolean hasShownToast = false;
    private boolean hasOpenedPopup = false;

    @Override
    public void onInitializeClient() {
        EntityRendererRegistry.register(EntityType.ZOMBIE, CustomZombieRenderer::new);
        ModConfig.initialize();

        // Oyundan çıkarken config kaydet
        ClientLifecycleEvents.CLIENT_STOPPING.register(client -> ModConfig.save());

        ClientTickEvents.START_CLIENT_TICK.register(client -> {
            if (client == null || client.world == null) return;

            // İlk kez dünya/sunucuya girişte popup göster
            if (!hasOpenedPopup && ModConfig.showWelcomePopup && client.currentScreen == null) {
                hasOpenedPopup = true;
                client.setScreen(new WelcomeScreen()); // Artık ayrı ayar kontrol ediliyor
            }

            // TitleScreen'de ilk kez toast göster
            if (ModConfig.showAKOTOriginalMessage && client.currentScreen instanceof TitleScreen && !hasShownToast) {
                dink.showToast("Merhaba!", "Orjinal bir KaplanBedwars eklentisi kullanıyorsunuz!");
                hasShownToast = true;
            }
        });
    }
}
