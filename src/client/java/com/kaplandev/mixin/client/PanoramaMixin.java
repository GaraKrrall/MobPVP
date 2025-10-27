package com.kaplandev.mixin.client;

import com.kaplandev.client.config.ConfigManager;

import me.shedaniel.autoconfig.AutoConfig;

import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.RotatingCubeMapRenderer;
import net.minecraft.client.gui.CubeMapRenderer;
import net.minecraft.util.Identifier;

import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(RotatingCubeMapRenderer.class)
public class PanoramaMixin {

    @Mutable
    @Shadow @Final
    private CubeMapRenderer cubeMap;

    private boolean addon$lastState = false;

    private static final Identifier VANILLA_PANORAMA =
            Identifier.of("minecraft","textures/gui/title/background/panorama");
    private static final Identifier MOBPVP_PANORAMA =
            Identifier.of("mobpvp", "textures/gui/title/panorama");

    @Inject(method = "render", at = @At("HEAD"))
    private void addon$onRender(float delta, float alpha, CallbackInfo ci) {
        ConfigManager config = AutoConfig.getConfigHolder(ConfigManager.class).getConfig();
        boolean enabled = config.showMobPvPPanorama;

        if (enabled != addon$lastState) {
            this.cubeMap = new CubeMapRenderer(enabled ? MOBPVP_PANORAMA : VANILLA_PANORAMA);
            addon$lastState = enabled;
        }
    }
}
