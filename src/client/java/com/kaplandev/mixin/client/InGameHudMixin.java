package com.kaplandev.mixin.client;


import com.kaplandev.level.player.PlayerLevelData;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.hud.InGameHud;
import net.minecraft.client.render.RenderTickCounter;
import net.minecraft.client.util.math.MatrixStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.UUID;

@Mixin(InGameHud.class)
public class InGameHudMixin {

    @Inject(method = "render", at = @At("TAIL"))
    private void onRender(DrawContext context, RenderTickCounter tickCounter, CallbackInfo ci) {
        MinecraftClient client = MinecraftClient.getInstance();

        if (client.player == null || client.options.hudHidden) return;

        UUID uuid = client.player.getUuid();
        int level = PlayerLevelData.getLevel(uuid);
        int xp = PlayerLevelData.getXp(uuid);
        int xpToNext = PlayerLevelData.getXpToLevelUp(level);

        String text = "§eSeviye: " + level + " §7XP: " + xp + "/" + xpToNext;

        TextRenderer textRenderer = client.textRenderer;
        int x = client.getWindow().getScaledWidth() - textRenderer.getWidth(text) - 5;
        int y = 5;

        context.drawText(textRenderer, text, x, y, 0xFFFFFF, true);
    }
}

