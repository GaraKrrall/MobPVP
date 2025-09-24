package com.kaplandev.client.gui;

/*import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.gui.widget.MultilineTextWidget;
import net.minecraft.text.Style;
import net.minecraft.text.Text;
import net.minecraft.util.Util;

public class MobPVPAboutScreen extends Screen {

    private final Screen parent;

    public MobPVPAboutScreen(Screen parent) {
        super(Text.literal("MobPVP - About"));
        this.parent = parent;
    }

    @Override
    protected void init() {
        int centerX = this.width / 2;
        int y = 30;

        // Açıklama
        MultilineTextWidget description = new MultilineTextWidget(
                Text.literal("""
MobPVP adds a level system to every mob.
Stronger mobs drop better loot and may become minibosses.
"""),
                this.textRenderer
        );
        description.setMaxWidth(this.width - 40);
        description.setPosition(centerX - description.getWidth() / 2, y);
        this.addDrawableChild(description);
        y += description.getHeight() + 20;

        // GitHub
        this.addDrawableChild(ButtonWidget.builder(
                Text.literal("🔗 GitHub").setStyle(Style.EMPTY.withColor(0x4287f5).withUnderline(true)),
                b -> Util.getOperatingSystem().open("https://github.com/KaplanBedwars/MobPVP")
        ).dimensions(centerX - 60, y, 120, 20).build());
        y += 22;

        // CurseForge
        this.addDrawableChild(ButtonWidget.builder(
                Text.literal("📦 CurseForge").setStyle(Style.EMPTY.withColor(0xFFA500).withUnderline(true)),
                b -> Util.getOperatingSystem().open("https://www.curseforge.com/minecraft/mc-mods/mobpvp")
        ).dimensions(centerX - 60, y, 120, 20).build());
        y += 22;

        // Modrinth
        this.addDrawableChild(ButtonWidget.builder(
                Text.literal("🧪 Modrinth").setStyle(Style.EMPTY.withColor(0x00CC88).withUnderline(true)),
                b -> Util.getOperatingSystem().open("https://modrinth.com/mod/mobpvp")
        ).dimensions(centerX - 60, y, 120, 20).build());
        y += 30;

        // Ayarlar butonu
        this.addDrawableChild(ButtonWidget.builder(
                Text.literal("⚙ Open Settings"),
                b -> this.client.setScreen(new MobPVPConfigScreen(this))
        ).dimensions(centerX - 75, this.height - 50, 150, 20).build());

        // Geri butonu
        this.addDrawableChild(ButtonWidget.builder(
                Text.literal("← Back"),
                b -> this.client.setScreen(parent)
        ).dimensions(centerX - 75, this.height - 25, 150, 20).build());
    }

    @Override
    public boolean shouldPause() {
        return false;
    }
}
*/