package mc.garakrral.client.xpjump;

import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.hud.InGameHud;
import net.minecraft.util.Identifier;

import mc.garakrral.xpjump.XpJump;
import mc.garakrral.xpjump.network.payload.RideInputPayload;
import mc.garakrral.xpjump.network.payload.SetStrengthPayload;
import mc.garakrral.xpjump.network.payload.StopJumpPayload;

import com.mojang.blaze3d.systems.RenderSystem;
import org.lwjgl.glfw.GLFW;

public class XpJumpClient {

    private static boolean charging = false;
    private static int strength = 0;

    private static int savedLevel = 0;
    private static float savedProgress = 0f;
    private static boolean wasRiding = false;

    public static void init() {
        var client = MinecraftClient.getInstance();

        // --- XP BAR ve kontrol ---
        ClientTickEvents.END_CLIENT_TICK.register(mc -> {
            if (mc.player == null) return;

            boolean isRidingXpJump = mc.player.getVehicle() instanceof XpJump;
            boolean pressed = GLFW.glfwGetKey(mc.getWindow().getHandle(), GLFW.GLFW_KEY_SPACE) == GLFW.GLFW_PRESS;
            boolean canJump = isRidingXpJump && mc.player.getVehicle().isOnGround();

            // --- W / Shift input ---
            boolean forward = GLFW.glfwGetKey(mc.getWindow().getHandle(), GLFW.GLFW_KEY_W) == GLFW.GLFW_PRESS;
            boolean sprintBrake = GLFW.glfwGetKey(mc.getWindow().getHandle(), GLFW.GLFW_KEY_LEFT_ALT) == GLFW.GLFW_PRESS;
            if (isRidingXpJump) {
                ClientPlayNetworking.send(new RideInputPayload(forward, sprintBrake));
            }

            // --- XP bar state ---
            if (isRidingXpJump && !wasRiding) {
                savedLevel = mc.player.experienceLevel;
                savedProgress = mc.player.experienceProgress;
                wasRiding = true;
            } else if (!isRidingXpJump && wasRiding) {
                mc.player.experienceLevel = savedLevel;
                mc.player.experienceProgress = savedProgress;
                wasRiding = false;
            }

            if (isRidingXpJump) {
                mc.player.experienceLevel = strength;
                mc.player.experienceProgress = strength / 100f;
            }

            // --- Jump charging ---
            if (pressed && canJump) {
                charging = true;
                strength += 2;
                if (strength > 100) strength = 100;
                ClientPlayNetworking.send(new SetStrengthPayload(strength));
            }

            if (!pressed && charging) {
                charging = false;
                ClientPlayNetworking.send(new StopJumpPayload());
                strength = 0;
            }
        });
    }
}
