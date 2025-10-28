package com.kaplandev.command.piece;

import com.kaplandev.build.ArenaTracker;
import com.mojang.brigadier.CommandDispatcher;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.text.Text;
import net.minecraft.util.math.BlockPos;

public class LocateArenaCommandPiece {
    public static void register(CommandDispatcher<ServerCommandSource> dispatcher) {
        dispatcher.register(CommandManager.literal("locatearena")
                .executes(context -> {
                    BlockPos playerPos = context.getSource().getPlayer().getBlockPos();
                    BlockPos nearest = ArenaTracker.getNearest(playerPos);
                    if (nearest == null) {
                        context.getSource().sendFeedback(() -> Text.of("Henüz hiçbir arena oluşmadı."), false);
                        return 0;
                    }
                    context.getSource().sendFeedback(() -> Text.of("En yakın arena: " + nearest.toShortString()), false);
                    return 1;
                }));
    }
}
