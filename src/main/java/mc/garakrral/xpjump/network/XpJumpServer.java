package mc.garakrral.xpjump.network;

import net.fabricmc.fabric.api.networking.v1.PayloadTypeRegistry;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;

import mc.garakrral.entity.passive.SnotBallEntity;
import mc.garakrral.xpjump.XpJump;
import mc.garakrral.xpjump.network.payload.RideInputPayload;
import mc.garakrral.xpjump.network.payload.SetStrengthPayload;
import mc.garakrral.xpjump.network.payload.StopJumpPayload;

public class XpJumpServer {

    public static void register() {

        PayloadTypeRegistry.playC2S().register(SetStrengthPayload.ID, SetStrengthPayload.CODEC);
        PayloadTypeRegistry.playC2S().register(StopJumpPayload.ID, StopJumpPayload.CODEC);
        PayloadTypeRegistry.playC2S().register(RideInputPayload.ID, RideInputPayload.CODEC);

        ServerPlayNetworking.registerGlobalReceiver(RideInputPayload.ID,
                (payload, context) -> {

                    var player = context.player();
                    var server = context.server();

                    server.execute(() -> {
                        if (player.getVehicle() instanceof SnotBallEntity s) {
                            s.setRiderForward(payload.forward());
                            s.setRiderSprintBrake(payload.sprintBrake());
                        }
                    });
                });



        // --- Jump ---
        ServerPlayNetworking.registerGlobalReceiver(SetStrengthPayload.ID, (payload, context) -> {
            var player = context.player();
            var server = context.server();
            server.execute(() -> {
                if (player.getVehicle() instanceof XpJump x && x.canJump()) {
                    x.setJumpStrength(payload.strength());
                }
            });
        });

        ServerPlayNetworking.registerGlobalReceiver(StopJumpPayload.ID, (payload, context) -> {
            var player = context.player();
            var server = context.server();
            server.execute(() -> {
                if (player.getVehicle() instanceof XpJump x) {
                    x.stopJumping();
                }
            });
        });
    }
}
