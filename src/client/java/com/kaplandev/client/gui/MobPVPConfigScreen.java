package com.kaplandev.client.gui;

import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.gui.widget.CheckboxWidget;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.text.Text;
import com.kaplandev.client.config.ModConfig;

public class MobPVPConfigScreen extends Screen {
    private final Screen parent;
    private CheckboxWidget showAKOTCheckbox;

    public MobPVPConfigScreen(Screen parent) {
        super(Text.literal("MobPVP Ayarları"));
        this.parent = parent;
    }

    @Override
    protected void init() {
        // Checkbox oluştur
        showAKOTCheckbox = CheckboxWidget.builder(
                        Text.literal("AKOT Orijinal Mesajını Göster"),
                        this.textRenderer
                )
                .pos(this.width / 2 - 155, this.height / 2 - 10)
                .checked(ModConfig.showAKOTOriginalMessage)
                .build();

        this.addDrawableChild(showAKOTCheckbox);

        // Kaydet butonu
        this.addDrawableChild(ButtonWidget.builder(
                        Text.literal("Ayarları Kaydet"),
                        button -> {
                            ModConfig.showAKOTOriginalMessage = showAKOTCheckbox.isChecked();
                            ModConfig.save();
                            this.client.setScreen(parent);
                        }
                )
                .dimensions(this.width / 2 - 155, this.height / 2 + 30, 150, 20)
                .build());

        // Vazgeç butonu
        this.addDrawableChild(ButtonWidget.builder(
                        Text.literal("Vazgeç"),
                        button -> this.client.setScreen(parent)
                )
                .dimensions(this.width / 2 + 5, this.height / 2 + 30, 150, 20)
                .build());
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        // renderBackground hatası düzeltildi
        this.renderBackground(context, mouseX, mouseY, delta);
        super.render(context, mouseX, mouseY, delta);

        // Başlık ve açıklama
        context.drawCenteredTextWithShadow(
                this.textRenderer,
                this.title,
                this.width / 2,
                30,
                0xFFFFFF
        );

        context.drawTextWrapped(
                this.textRenderer,
                Text.literal("AKOT mesajlarının orijinal halini gösterip göstermeyeceğini ayarlayın"),
                this.width / 2 - 150,
                60,
                300,
                0xAAAAAA
        );
    }
}