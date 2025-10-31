package mc.garakrral.mixin.client;

import mc.garakrral.client.config.ConfigManager;

import me.shedaniel.autoconfig.AutoConfig;
import net.minecraft.client.gui.screen.TitleScreen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.text.Text;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(TitleScreen.class)
public abstract class TitleScreenMixin extends Screen {
    protected TitleScreenMixin(Text title) {
        super(title);
    }

    @Inject(method = "init", at = @At("TAIL"))
    private void mobpvp$injectCornerIcon(CallbackInfo ci) {
        if (this.client == null) return;

        int iconSize = 20;
        int padding = 5;
        int x = this.width - iconSize - padding;
        int y = padding;

        this.addDrawableChild(ButtonWidget.builder(
                Text.literal("⚙"),
                b -> this.client.setScreen(AutoConfig.getConfigScreen(ConfigManager.class, this).get())
        ).dimensions(x, y, iconSize, iconSize).build());
    }
}
