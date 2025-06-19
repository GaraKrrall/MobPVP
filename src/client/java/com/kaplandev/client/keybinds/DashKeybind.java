package com.kaplandev.client.keybinds;



import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import org.lwjgl.glfw.GLFW;

public class DashKeybind implements ClientModInitializer {
    public static KeyBinding dashKey;

    @Override
    public void onInitializeClient() {
        dashKey = KeyBindingHelper.registerKeyBinding(new KeyBinding(
                "key.mobpvp.dash",
                InputUtil.Type.KEYSYM,
                GLFW.GLFW_KEY_R,
                "category.mobpvp"
        ));

        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            if (dashKey.wasPressed() && client.player != null) {
                client.player.networkHandler.sendChatCommand("trigger dash");
            }
        });
    }
}
