package com.kaplandev.client;

import com.kaplandev.block.BlockEntityTypes;
import com.kaplandev.client.config.ConfigManager;
import com.kaplandev.client.info.dink;
import com.kaplandev.client.renderer.CustomZombieRenderer;
import com.kaplandev.client.renderer.entity.block.IronChestBlockRenderer;
import com.kaplandev.client.renderer.entity.block.PvpSpawnerBlockRenderer;
import com.kaplandev.client.renderer.entity.boss.BulwarkRenderer;
import com.kaplandev.client.renderer.entity.mobpvp.MiniIronGolemRenderer;
import com.kaplandev.client.renderer.entity.zombie.SuperZombieRenderer;
import com.kaplandev.client.renderer.entity.block.PvpSpawnerMaxBlockRenderer;
import com.kaplandev.entity.EntityType;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientLifecycleEvents;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.minecraft.client.gui.screen.TitleScreen;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactories;
import net.minecraft.client.render.entity.SkeletonEntityRenderer;

public class mobpvpClient implements ClientModInitializer {
    private boolean hasShownToast = false;
    private boolean hasOpenedPopup = false;

    @Override
    public void onInitializeClient() {
        BlockEntityRendererFactories.register(EntityType.PVP_SPAWNER, PvpSpawnerBlockRenderer::new);
        BlockEntityRendererFactories.register(EntityType.PVP_SPAWNER_MAX, PvpSpawnerMaxBlockRenderer::new);
        BlockEntityRendererFactories.register(BlockEntityTypes.IRON_CHEST_ENTITY_TYPE, IronChestBlockRenderer::new);
        EntityRendererRegistry.register(net.minecraft.entity.EntityType.ZOMBIE, CustomZombieRenderer::new);
        EntityRendererRegistry.register(EntityType.MAD_ZOMBIE, SuperZombieRenderer::new);
        EntityRendererRegistry.register(EntityType.MAD_SKELETON, SkeletonEntityRenderer::new);
        EntityRendererRegistry.register(EntityType.BULWARK, BulwarkRenderer::new);
        EntityRendererRegistry.register(EntityType.MINIGOLEM, MiniIronGolemRenderer::new);

        ConfigManager.initialize();


        // Oyundan çıkarken config kaydet
        ClientLifecycleEvents.CLIENT_STOPPING.register(client -> ConfigManager.save());

        ClientTickEvents.START_CLIENT_TICK.register(client -> {

            if (ConfigManager.showAKOTOriginalMessage && client.currentScreen instanceof TitleScreen && !hasShownToast) {
                dink.showToast("Merhaba!", "Orjinal bir KaplanBedwars eklentisi kullanıyorsunuz!");
                hasShownToast = true;
            }
            if (client == null || client.world == null) return;

            //TODO: Bu ekranı düzelt

            // İlk kez dünya/sunucuya girişte popup göster
          /*  if (!hasOpenedPopup && ModConfig.showWelcomePopup && client.currentScreen == null) {
                hasOpenedPopup = true;
                client.setScreen(new WelcomeScreen()); // Artık ayrı ayar kontrol ediliyor
            }*/
        });
    }
}
