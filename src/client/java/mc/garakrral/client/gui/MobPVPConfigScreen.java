package mc.garakrral.client.gui;

/*import com.kaplandev.client.config.ConfigManager;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.gui.widget.CheckboxWidget;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.text.Text;

public class MobPVPConfigScreen extends Screen {
    private final Screen parent;
    private CheckboxWidget showAKOTCheckbox;
    private final boolean isTurkish;

    public MobPVPConfigScreen(Screen parent) {
        super(Text.literal("MobPVP Settings"));
        this.parent = parent;

        String lang = MinecraftClient.getInstance().options.language;
        this.isTurkish = lang != null && lang.toLowerCase().startsWith("tr");
    }

    @Override
    protected void init() {
        String checkboxText = isTurkish ? "AKOT Orijinal Mesajını Göster" : "Show AKOT Original Message";
        String saveText = isTurkish ? "Ayarları Kaydet" : "Save Settings";
        String cancelText = isTurkish ? "Vazgeç" : "Cancel";

        // Checkbox
        showAKOTCheckbox = CheckboxWidget.builder(
                        Text.literal(checkboxText),
                        this.textRenderer
                )
                .pos(this.width / 2 - 155, this.height / 2 - 10)
                .checked(ConfigManager.showAKOTOriginalMessage)
                .build();

        this.addDrawableChild(showAKOTCheckbox);

        // Save button
        this.addDrawableChild(ButtonWidget.builder(
                        Text.literal(saveText),
                        button -> {
                            ConfigManager.showAKOTOriginalMessage = showAKOTCheckbox.isChecked();
                            ConfigManager.save();
                            this.client.setScreen(parent);
                        }
                )
                .dimensions(this.width / 2 - 155, this.height / 2 + 30, 150, 20)
                .build());

        // Cancel button
        this.addDrawableChild(ButtonWidget.builder(
                        Text.literal(cancelText),
                        button -> this.client.setScreen(parent)
                )
                .dimensions(this.width / 2 + 5, this.height / 2 + 30, 150, 20)
                .build());
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        this.renderBackground(context, mouseX, mouseY, delta);
        super.render(context, mouseX, mouseY, delta);

        String titleText = isTurkish ? "MobPVP Ayarları" : "MobPVP Settings";
        String description = isTurkish
                ? "AKOT mesajlarının orijinal halini gösterip göstermeyeceğini ayarlayın"
                : "Toggle whether AKOT messages are shown in their original form";

        context.drawCenteredTextWithShadow(
                this.textRenderer,
                Text.literal(titleText),
                this.width / 2,
                30,
                0xFFFFFF
        );

        context.drawTextWrapped(
                this.textRenderer,
                Text.literal(description),
                this.width / 2 - 150,
                60,
                300,
                0xAAAAAA
        );
    }
}
*/