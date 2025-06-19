package com.kaplandev.client.gui;

import com.kaplandev.client.config.ModConfig;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.text.Text;

public class WelcomeScreen extends Screen {
    public WelcomeScreen() {
        super(Text.of("Orijinal KaplanBedwars"));
    }

    @Override
    protected void init() {
        int centerX = this.width / 2;
        int centerY = this.height / 2;

        this.addDrawableChild(ButtonWidget.builder(Text.of("Anladım"), button -> {
            ModConfig.showWelcomePopup = false; // ✅ Bu ekran bir daha açılmasın
            ModConfig.save();
            MinecraftClient.getInstance().setScreen(null);
        }).position(centerX - 50, centerY + 60).size(100, 20).build());
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        this.renderBackground(context, mouseX, mouseY, delta);
        super.render(context, mouseX, mouseY, delta);

        int textX = this.width / 2 - 120; // metin kutusu başlangıcı
        int textWidth = 240;              // maksimum genişlik
        int y = this.height / 2 - 60;

        context.drawCenteredTextWithShadow(this.textRenderer, Text.of("Bu, orijinal bir KaplanBedwars eklentisidir."), this.width / 2, y, 0xFFFFFF);
        y += 20;

        Text wrappedText = Text.of("Eklentide, her canlıya seviye atanır. Canlılar seviyesine göre daha fazla cana ve loota sahip olabilir. Dragon 2000 seviyedir. Bu da onu oyundaki en güçlü boss yapar. Ayrıca zombiler için özel seviye istemi bulunur.");
        context.drawTextWrapped(this.textRenderer, wrappedText, textX, y, textWidth, 0xAAAAAA);

        y += 60; // yaklaşık yükseklik ayarı
        context.drawCenteredTextWithShadow(this.textRenderer, Text.of("İyi oyunlar."), this.width / 2, y, 0x888888);
    }



    @Override
    public boolean shouldPause() {
        return false;
    }
}
