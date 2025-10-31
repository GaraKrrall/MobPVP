package mc.garakrral.client.info; // Kendi mod paketinize göre değiştirin

import mc.garakrral.client.config.ConfigManager;

import me.shedaniel.autoconfig.AutoConfig;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.toast.SystemToast;
import net.minecraft.text.Text;
import net.minecraft.text.TextColor;

public class dink {
    public static void showToast(String originalTitle, String originalMessage) {
        ConfigManager config = AutoConfig.getConfigHolder(ConfigManager.class).getConfig();

// Başlık / mesaj
        String title = config.customToastTitle != null && !config.customToastTitle.isEmpty()
                ? config.customToastTitle
                : originalTitle;

        String message = config.customToastMessage != null && !config.customToastMessage.isEmpty()
                ? config.customToastMessage
                : originalMessage;

// Renk
        Text titleText = Text.literal(title).styled(s -> s.withColor(TextColor.fromRgb(config.customToastColor)));
        Text messageText = Text.literal(message);

// Toast tipi enum’dan geliyor
        SystemToast.Type toastType = config.customToastType.getVanilla();

        MinecraftClient.getInstance().getToastManager().add(
                new SystemToast(toastType, titleText, messageText)
        );
    }
}
