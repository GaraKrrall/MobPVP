package com.kaplandev.client.gui;

import com.kaplandev.api.version.VersionUtils;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.text.Text;

public class BetaNoticeScreen extends Screen {
    private final boolean isTurkish;
    private final Screen parent;
   // private final String betaVersionText = "MobPVP " + VersionUtils.getPermanentBetaVersionNumberWithFullVersion("3.1.5");
     private final String betaVersionText = "MobPVP 3.1.7";

    public BetaNoticeScreen(Screen parent) {
        super(Text.of("MobPVP 3.1.7"));
        this.parent = parent;

        String languageCode = MinecraftClient.getInstance().options.language;
        isTurkish = languageCode != null && languageCode.toLowerCase().startsWith("tr");
    }

    @Override
    protected void init() {
        int centerX = this.width / 2;
        int textY = this.height / 2 - 70;
        int buttonY = textY + 70; // Yazıların altına konumlandır

        this.addDrawableChild(ButtonWidget.builder(
                Text.of(isTurkish ? "Anladım" : "Got it"),
                button -> MinecraftClient.getInstance().setScreen(null)
        ).dimensions(centerX - 50, buttonY, 100, 20).build());
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        this.renderBackground(context, mouseX, mouseY, delta);
        super.render(context, mouseX, mouseY, delta);

        int textY = this.height / 2 - 70;

        String warningLine1 = isTurkish
                ? "MobPVP modunun beta sürümünü kullanıyorsunuz!"
                : "You are using a beta version of MobPVP!";
        String warningLine2 = isTurkish
                ? "Hatalar, eksikler veya çökmeler olabilir."
                : "There may be bugs, missing features, or crashes.";
        String warningLine3 = isTurkish
                ? "Geri bildirimleriniz bizim için çok önemli!"
                : "Your feedback is highly appreciated!";

        context.drawCenteredTextWithShadow(this.textRenderer, Text.of(warningLine1), this.width / 2, textY, 0xFFAA00);
        textY += 20;
        context.drawCenteredTextWithShadow(this.textRenderer, Text.of(warningLine2), this.width / 2, textY, 0xFF5555);
        textY += 20;
        context.drawCenteredTextWithShadow(this.textRenderer, Text.of(warningLine3), this.width / 2, textY, 0xAAAAFF);

        // Sağ alt köşeye beta sürüm yazısı
        context.drawTextWithShadow(
                this.textRenderer,
                Text.of(betaVersionText),
                this.width - this.textRenderer.getWidth(betaVersionText) - 5,
                this.height - 12,
                0xAAAAAA
        );
    }

    @Override
    public boolean shouldPause() {
        return false;
    }
}
