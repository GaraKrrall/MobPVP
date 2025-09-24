package com.kaplandev.mixin.client;

import com.kaplandev.client.config.ConfigManager;
import com.kaplandev.level.player.PlayerLevelData;
import me.shedaniel.autoconfig.AutoConfig;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.hud.InGameHud;
import net.minecraft.client.render.RenderTickCounter;
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

        ConfigManager config = AutoConfig.getConfigHolder(ConfigManager.class).getConfig();
        if (!config.showLevelHud) return;

        UUID uuid = client.player.getUuid();
        int level = PlayerLevelData.getLevel(uuid);
        int xp = PlayerLevelData.getXp(uuid);
        int xpToNext = Math.max(1, PlayerLevelData.getXpToLevelUp(level));

        TextRenderer textRenderer = client.textRenderer;

        // Gösterilecek metinler
        String levelText = "Lv " + level;
        String xpText = xp + " / " + xpToNext;

        // HUD konumu (sağ üst)
        int hudWidth = Math.max(textRenderer.getWidth(levelText), textRenderer.getWidth(xpText)) + 20;
        int hudHeight = 36;
        int x = client.getWindow().getScaledWidth() - hudWidth - 8;
        int y = 8;

        // Panel arka plan (yarı saydam kutu)
        int bgColor = 0xAA101014;
        context.fill(x, y, x + hudWidth, y + hudHeight, bgColor);

        // İnce kenarlık
        context.fill(x, y, x + hudWidth, y + 1, 0x55000000);
        context.fill(x, y + hudHeight - 1, x + hudWidth, y + hudHeight, 0x55000000);
        context.fill(x, y, x + 1, y + hudHeight, 0x55000000);
        context.fill(x + hudWidth - 1, y, x + hudWidth, y + hudHeight, 0x55000000);

        // Seviye yazısı (üstte)
        context.drawCenteredTextWithShadow(textRenderer, levelText,
                x + hudWidth / 2, y + 4, config.levelColor);

        // XP bar (altta)
        int barW = hudWidth - 12;
        int barH = 8;
        int barX = x + 6;
        int barY = y + hudHeight - barH - 6;

        // arka plan
        context.fill(barX, barY, barX + barW, barY + barH, 0xFF202020);

        // dolum
        float progress = (float) xp / xpToNext;
        int filled = (int) (barW * progress);
        if (filled > 0) {
            context.fillGradient(barX, barY, barX + filled, barY + barH,
                    config.xpColor, 0xFF55FFFF);
        }

        // XP yazısı barın ortasında
        context.drawCenteredTextWithShadow(textRenderer, xpText,
                x + hudWidth / 2, barY - 10, config.xpColor);
    }
}
