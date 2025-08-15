package com.kaplandev.mixin.client;

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

    @Inject(method = "<init>", at = @At("TAIL"))
    private void onInit(CubeMapRenderer original, CallbackInfo ci) {
        Identifier myPanorama = Identifier.of("mobpvp", "textures/gui/title/panorama");
        this.cubeMap = new CubeMapRenderer(myPanorama);
    }
}
