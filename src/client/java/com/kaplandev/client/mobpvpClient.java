package com.kaplandev.client;


//import com.kaplandev.block.BlockEntityTypes;

import com.kaplandev.block.Blocks;
import com.kaplandev.client.config.ConfigManager;
import com.kaplandev.client.gui.BetaNoticeScreen;
import com.kaplandev.client.gui.MobTableScreen;
import com.kaplandev.client.info.dink;
import com.kaplandev.client.renderer.entity.block.MobTableBlockRenderer;
import com.kaplandev.client.renderer.entity.block.PvpSpawnerBlockRenderer;
import com.kaplandev.client.renderer.entity.block.PvpSpawnerMaxBlockRenderer;
import com.kaplandev.client.renderer.entity.boss.BulwarkRenderer;
import com.kaplandev.client.renderer.entity.mob.CustomZombieRenderer;
import com.kaplandev.client.renderer.entity.mob.SuperZombieRenderer;
import com.kaplandev.client.renderer.entity.mobpvp.MiniIronGolemRenderer;
import com.kaplandev.entity.EntityType;
import com.kaplandev.handler.type.ScreenHandlerTypes;

import com.kaplanlib.api.version.BetaVersions;
import com.kaplanlib.api.version.VersionUtils;

import me.shedaniel.autoconfig.AutoConfig;
import me.shedaniel.autoconfig.serializer.GsonConfigSerializer;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.minecraft.client.gui.screen.TitleScreen;
import net.minecraft.client.gui.screen.ingame.HandledScreens;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactories;
import net.minecraft.client.render.entity.FlyingItemEntityRenderer;
import net.minecraft.client.render.entity.SkeletonEntityRenderer;

public class mobpvpClient implements ClientModInitializer {
    private String title;
    private String message;

    @Override
    public void onInitializeClient() {
        AutoConfig.register(ConfigManager.class, GsonConfigSerializer::new);
        ConfigManager config = AutoConfig.getConfigHolder(ConfigManager.class).getConfig();
        BlockEntityRendererFactories.register(EntityType.PVP_SPAWNER, PvpSpawnerBlockRenderer::new);
        BlockEntityRendererFactories.register(EntityType.PVP_SPAWNER_MAX, PvpSpawnerMaxBlockRenderer::new);
        BlockEntityRendererFactories.register(EntityType.MOB_TABLE, MobTableBlockRenderer::new);
        BlockRenderLayerMap.INSTANCE.putBlock(Blocks.PVP_SPAWNER, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(Blocks.PVP_SPAWNER_MAX, RenderLayer.getCutout());
        // BlockEntityRendererFactories.register(BlockEntityTypes.IRON_CHEST_ENTITY_TYPE, IronChestBlockRenderer::new);
        EntityRendererRegistry.register(net.minecraft.entity.EntityType.ZOMBIE, CustomZombieRenderer::new);
        EntityRendererRegistry.register(EntityType.MAD_ZOMBIE, SuperZombieRenderer::new);
        EntityRendererRegistry.register(EntityType.MAD_SKELETON, SkeletonEntityRenderer::new);
        EntityRendererRegistry.register(EntityType.BULWARK, BulwarkRenderer::new);
        EntityRendererRegistry.register(EntityType.MINIGOLEM, MiniIronGolemRenderer::new);
        EntityRendererRegistry.register(EntityType.IRON_REINFORCED_COPPER_BALL, FlyingItemEntityRenderer::new);
        HandledScreens.register(ScreenHandlerTypes.MOB_TABLE, MobTableScreen::new);


        ClientTickEvents.START_CLIENT_TICK.register(client -> {

            if (config.showAKOTOriginalMessage && client.currentScreen instanceof TitleScreen && !config.debugHasShownToast) {
                if (!config.hasOpenedBetaNotice && BetaVersions.IS_BETA) {
                    config.hasOpenedBetaNotice = true;
                    client.setScreen(new BetaNoticeScreen(client.currentScreen));
                }
                dink.showToast(title, message);
                if (BetaVersions.IS_BETA) {
                    dink.showToast("Debug", VersionUtils.getBetaVersionNumber());
                }
                config.debugHasShownToast = true;
            }
            if (client == null || client.world == null) return;

            // İlk kez dünya/sunucuya girişte popup göster
          /*  if (!hasOpenedPopup && ModConfig.showWelcomePopup && client.currentScreen == null) {
                hasOpenedPopup = true;
                client.setScreen(new WelcomeScreen()); // Artık ayrı ayar kontrol ediliyor
            }*/
        });

    }

}
