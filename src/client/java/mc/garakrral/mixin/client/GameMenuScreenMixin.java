package mc.garakrral.mixin.client;

import mc.garakrral.client.config.ConfigManager;

import me.shedaniel.autoconfig.AutoConfig;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.gui.screen.GameMenuScreen;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.text.Text;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Environment(EnvType.CLIENT)
@Mixin(GameMenuScreen.class)
public abstract class GameMenuScreenMixin extends Screen {
    private ButtonWidget addon$configButton;

    protected GameMenuScreenMixin(Text title) {
        super(title);
    }

    @Inject(method = "init", at = @At("TAIL"))
    private void addon$initButtons(CallbackInfo ci) {
        if (this.client == null) return;

        int iconSize = 20;
        int padding = 5;
        int x = this.width - iconSize - padding;
        int y = padding;

        // Config butonu
        addon$configButton = ButtonWidget.builder(Text.literal("⚙"),
                        (b) -> this.client.setScreen(
                                AutoConfig.getConfigScreen(ConfigManager.class, this).get()
                        ))
                .dimensions(x, y, iconSize, iconSize)
                .build();
        this.addDrawableChild(addon$configButton);
    }
}
