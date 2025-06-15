package com.kaplandev.client;

import com.kaplandev.client.InfoSys.dink;
import com.kaplandev.client.config.ModConfig;
import com.kaplandev.client.gui.MobPVPConfigScreen;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientLifecycleEvents;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.screen.v1.ScreenEvents;
import net.fabricmc.fabric.api.client.screen.v1.Screens;
import net.minecraft.client.gui.screen.TitleScreen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.text.Text;

public class mobpvpClient implements ClientModInitializer {
    private boolean hasShownToast = false;

    @Override
    public void onInitializeClient() {
        ClientTickEvents.START_CLIENT_TICK.register(client -> {
            ModConfig.initialize();

            // Oyundan çıkışta ayarları kaydet
            ClientLifecycleEvents.CLIENT_STOPPING.register(stoppingClient -> {
                ModConfig.save();
            });

            System.out.println("MobPVP modu başarıyla yüklendi!");

            if (ModConfig.showAKOTOriginalMessage) {
                // Orijinal mesajı göster
                if (client.currentScreen instanceof TitleScreen && !hasShownToast) {
                    dink.showToast("Merhaba!", "Orjinal bir KaplanBedwars eklentisi kullanıyorsunuz!");
                    hasShownToast = true;
                }
            }
        });
    }
}