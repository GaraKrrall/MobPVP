package mc.garakrral.client.keybind;

import mc.garakrral.client.gui.level.LevelScreen;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import org.lwjgl.glfw.GLFW;

public class Keybinds implements ClientModInitializer {
    public static KeyBinding dashKey;
    public static KeyBinding level;

    @Override
    public void onInitializeClient() {
        dashKey = KeyBindingHelper.registerKeyBinding(new KeyBinding(
                "key.mobpvp.dash",
                InputUtil.Type.KEYSYM,
                GLFW.GLFW_KEY_R,
                "category.mobpvp"
        ));

       level =  KeyBindingHelper.registerKeyBinding(new KeyBinding(
                "key.kaplandev.open_level_gui",
                InputUtil.Type.KEYSYM,
                GLFW.GLFW_KEY_L,
                "category.mobpvp"
        ));

        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            if (dashKey.wasPressed() && client.player != null) {
                client.player.networkHandler.sendChatCommand("mobpvp dash");
            }
        });

        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            while (level.wasPressed()) {
                client.setScreen(new LevelScreen());
            }
        });
    }
}
