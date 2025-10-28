package com.kaplandev.client;

import com.kaplandev.block.BlockType;
import com.kaplandev.block.Blocks;
import com.kaplandev.client.config.ConfigManager;
import com.kaplandev.client.gui.BetaNoticeScreen;
import com.kaplandev.client.gui.MobTableScreen;
import com.kaplandev.client.info.dink;
import com.kaplandev.client.renderer.entity.block.MobTableBlockRenderer;
import com.kaplandev.client.renderer.entity.block.PvpSpawnerBlockRenderer;
import com.kaplandev.client.renderer.entity.block.PvpSpawnerMaxBlockRenderer;
import com.kaplandev.client.renderer.entity.block.UpgradedHopperBlockRenderer;
import com.kaplandev.client.renderer.entity.boss.BulwarkRenderer;
import com.kaplandev.client.renderer.entity.mob.CustomZombieRenderer;
import com.kaplandev.client.renderer.entity.mob.SuperZombieRenderer;
import com.kaplandev.client.renderer.entity.mobpvp.MiniCopperGolemRenderer;
import com.kaplandev.client.renderer.entity.mobpvp.MiniIronGolemRenderer;
import com.kaplandev.entity.EntityType;
import com.kaplandev.handler.type.ScreenHandlerTypes;
import com.kaplandev.level.LevelAssigner;
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
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.hit.EntityHitResult;

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
        BlockEntityRendererFactories.register(EntityType.UPGREADED_HOPPER, UpgradedHopperBlockRenderer::new);

        BlockRenderLayerMap.INSTANCE.putBlock(BlockType.PVP_SPAWNER, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(BlockType.PVP_SPAWNER_MAX, RenderLayer.getCutout());

        EntityRendererRegistry.register(net.minecraft.entity.EntityType.ZOMBIE, CustomZombieRenderer::new);
        EntityRendererRegistry.register(EntityType.MAD_ZOMBIE, SuperZombieRenderer::new);
        EntityRendererRegistry.register(EntityType.MAD_SKELETON, SkeletonEntityRenderer::new);
        EntityRendererRegistry.register(EntityType.BULWARK, BulwarkRenderer::new);
        EntityRendererRegistry.register(EntityType.MINIGOLEM, MiniIronGolemRenderer::new);
        EntityRendererRegistry.register(EntityType.MINIGOLEM_COPPER, MiniCopperGolemRenderer::new);
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
        });

        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            if (client.world == null) return;

            if (client.crosshairTarget instanceof EntityHitResult ehr && ehr.getEntity() instanceof LivingEntity living) {
                if (LevelAssigner.hasLevel(living)) {
                    String newName = LevelAssigner.buildDisplayName(living);
                    String currentName = living.hasCustomName() ? living.getCustomName().getString() : null;

                    if (currentName == null || !currentName.equals(newName)) {
                        living.setCustomName(net.minecraft.text.Text.of(newName));
                    }
                    living.setCustomNameVisible(true);
                }
            } else {
                for (var e : client.world.getEntities()) {
                    if (e instanceof LivingEntity le && le.hasCustomName()) {
                        le.setCustomNameVisible(false);
                    }
                }
            }
        });

    }
}
