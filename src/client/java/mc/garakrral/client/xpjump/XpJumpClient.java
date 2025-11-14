package mc.garakrral.client.xpjump;

import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;

import mc.garakrral.xpjump.XpJump;
import mc.garakrral.xpjump.network.payload.RideInputPayload;
import mc.garakrral.xpjump.network.payload.SetStrengthPayload;
import mc.garakrral.xpjump.network.payload.StopJumpPayload;

import org.lwjgl.glfw.GLFW;

public class XpJumpClient {

    private static boolean charging = false;
    private static int strength = 0;

    private static int savedLevel = 0;
    private static float savedProgress = 0f;
    private static boolean wasRiding = false;

    public static void init() {

        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            if (client.player == null) return;

            boolean isRidingXpJump = client.player.getVehicle() instanceof XpJump;
            boolean pressed = GLFW.glfwGetKey(client.getWindow().getHandle(), GLFW.GLFW_KEY_SPACE) == GLFW.GLFW_PRESS;
            boolean canJump = isRidingXpJump && client.player.getVehicle().isOnGround();

            // --- W / Shift input ---
            boolean forward = GLFW.glfwGetKey(client.getWindow().getHandle(), GLFW.GLFW_KEY_W) == GLFW.GLFW_PRESS;
            boolean sprintBrake = GLFW.glfwGetKey(client.getWindow().getHandle(), GLFW.GLFW_KEY_LEFT_SHIFT) == GLFW.GLFW_PRESS;
            if (isRidingXpJump) {
                ClientPlayNetworking.send(new RideInputPayload(forward, sprintBrake));
            }


            // --- XP bar güncelle ---
            if (isRidingXpJump && !wasRiding) {
                savedLevel = client.player.experienceLevel;
                savedProgress = client.player.experienceProgress;
                wasRiding = true;
            } else if (!isRidingXpJump && wasRiding) {
                client.player.experienceLevel = savedLevel;
                client.player.experienceProgress = savedProgress;
                wasRiding = false;
            }

            if (isRidingXpJump) {
                client.player.experienceLevel = strength;
                client.player.experienceProgress = strength / 100f;
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
