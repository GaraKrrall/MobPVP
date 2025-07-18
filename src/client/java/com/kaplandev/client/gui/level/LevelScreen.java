package com.kaplandev.client.gui.level;

import com.kaplandev.api.annotation.Bug;
import com.kaplandev.level.player.PlayerLevelData;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.text.Text;

import java.util.UUID;
@Bug("Sunucu tarafında çalışmıyor.")
public class LevelScreen extends Screen {

    public LevelScreen() {
        super(Text.literal("Seviye Bilgisi"));
    }

    @Override
    protected void init() {
        super.init();
        int centerX = this.width / 2;
        int centerY = this.height / 2;

        // Kapat butonu
        this.addDrawableChild(ButtonWidget.builder(
                Text.translatable("screen.kaplandev.level.close"),
                button -> this.close()
        ).dimensions(centerX - 50, centerY + 40, 100, 20).build());
    }
    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        MinecraftClient client = MinecraftClient.getInstance();
        if (client.player == null || client.world == null) return;

        // Eğer oyuncu bir sunucudaysa (entegre değilse yani local değilse)
        if (!client.isInSingleplayer()) {
            client.setScreen(null); // Ekranı kapat
            client.player.sendMessage(Text.translatable("key.mobpvp.screenerror"), true);
            return;
        }

        super.render(context, mouseX, mouseY, delta);
        UUID uuid = client.player.getUuid();
        int level = PlayerLevelData.getLevel(uuid);
        int xp = PlayerLevelData.getXp(uuid);
        int xpToNext = PlayerLevelData.getXpToLevelUp(level);

        int centerX = this.width / 2;
        int centerY = this.height / 2;

        context.drawCenteredTextWithShadow(textRenderer,
                Text.translatable("screen.kaplandev.level.title"),
                centerX, centerY - 60, 0xFFFFFF);

        context.drawCenteredTextWithShadow(textRenderer,
                Text.translatable("screen.kaplandev.level.level", level),
                centerX, centerY - 30, 0xFFFFFF);

        context.drawCenteredTextWithShadow(textRenderer,
                Text.translatable("screen.kaplandev.level.xp", xp, xpToNext),
                centerX, centerY - 10, 0xFFFFFF);

    }
    @Override
    public boolean shouldPause() {
        return false;
    }
}
