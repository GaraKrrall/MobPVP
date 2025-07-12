package com.kaplandev.client.gui.level;

import com.kaplandev.level.player.PlayerLevelData;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.util.math.Rect2i;
import net.minecraft.text.Text;

import java.util.UUID;

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
        super.render(context, mouseX, mouseY, delta);

        MinecraftClient client = MinecraftClient.getInstance();
        if (client.player == null) return;

        UUID uuid = client.player.getUuid();
        int level = PlayerLevelData.getLevel(uuid);
        int xp = PlayerLevelData.getXp(uuid);
        int xpToNext = PlayerLevelData.getXpToLevelUp(level);

        int centerX = this.width / 2;
        int centerY = this.height / 2;

        context.drawCenteredTextWithShadow(textRenderer, "§6SEVİYE PANELİ", centerX, centerY - 60, 0xFFFFFF);
        context.drawCenteredTextWithShadow(textRenderer, "§eSeviye: §a" + level, centerX, centerY - 30, 0xFFFFFF);
        context.drawCenteredTextWithShadow(textRenderer, "§eXP: §7" + xp + "§f/§7" + xpToNext, centerX, centerY - 10, 0xFFFFFF);



    }

    @Override
    public boolean shouldPause() {
        return false;
    }
}
