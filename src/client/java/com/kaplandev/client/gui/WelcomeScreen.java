package com.kaplandev.client.gui;

import com.kaplandev.client.config.ConfigManager;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.text.Text;

public class WelcomeScreen extends Screen {
    private boolean isTurkish;

    public WelcomeScreen() {
        super(Text.of("KaplanBedwars"));

        String languageCode = MinecraftClient.getInstance().options.language;
        isTurkish = languageCode.toLowerCase().startsWith("tr");
    }



    @Override
    protected void init() {
        int centerX = this.width / 2;
        int centerY = this.height / 2;

        this.addDrawableChild(ButtonWidget.builder(
                Text.of(isTurkish ? "Anladım" : "Got it"),
                button -> {
                    ConfigManager.showWelcomePopup = false;
                    ConfigManager.save();
                    MinecraftClient.getInstance().setScreen(null);
                }
        ).position(centerX - 50, centerY + 40).size(100, 20).build());
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        this.renderBackground(context, mouseX, mouseY, delta);
        super.render(context, mouseX, mouseY, delta);

        int y = this.height / 2 - 20;

        String thankYou = isTurkish
                ? "Modu indirdiğiniz için teşekkürler!"
                : "Thanks for downloading the mod!";
        String enjoy = isTurkish
                ? "İyi oyunlar!"
                : "Enjoy your game!";

        context.drawCenteredTextWithShadow(this.textRenderer, Text.of(thankYou), this.width / 2, y, 0xFFFFFF);
        y += 20;
        context.drawCenteredTextWithShadow(this.textRenderer, Text.of(enjoy), this.width / 2, y, 0xAAAAAA);
    }

    @Override
    public boolean shouldPause() {
        return false;
    }
}
