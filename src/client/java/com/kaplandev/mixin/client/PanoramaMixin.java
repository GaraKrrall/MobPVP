package com.kaplandev.mixin.client;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.RotatingCubeMapRenderer;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

    @Mixin(RotatingCubeMapRenderer.class)
    public class PanoramaMixin {

        // Kendi görselimizin konumu
        private static final Identifier CUSTOM_BACKGROUND =  Identifier.of("mobpvp", "textures/gui/panorama_overlay.png");

        @Inject(method = "render", at = @At("HEAD"), cancellable = true)
        private void onRender(DrawContext context, int width, int height, float alpha, float tickDelta, CallbackInfo ci) {
            RenderSystem.enableBlend();
            context.setShaderColor(1.0F, 1.0F, 1.0F, alpha);

            // Ekranı tamamen kaplayacak şekilde resmi çiz
            context.drawTexture(CUSTOM_BACKGROUND, 0, 0, 0, 0, width, height, width, height);

            context.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
            RenderSystem.disableBlend();

            // Orijinal render işlemini iptal et
            ci.cancel();
        }
    }


