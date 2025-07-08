package com.kaplandev.commands;

import com.kaplandev.build.ArenaTracker;
import com.kaplandev.commands.dashSys.DashCommand;
import com.mojang.brigadier.CommandDispatcher;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.text.Text;
import net.minecraft.util.math.BlockPos;

public class ModCommands {
    public static void register(CommandDispatcher<ServerCommandSource> dispatcher) {
         /*
        dispatcher.register(CommandManager.literal("selam")
                .executes(context -> {
                    context.getSource().sendFeedback(() -> Text.literal("Merhaba! Bu bir test komutudur."), false);
                    return 1;
                }));
*/
        // Dash komutu
        dispatcher.register(CommandManager.literal("trigger")
                .then(CommandManager.literal("dash")
                        .executes(DashCommand::run)
                ));

        // Yeni locatearena komutu
        dispatcher.register(CommandManager.literal("locatearena")
                .executes(context -> {
                    BlockPos playerPos = context.getSource().getPlayer().getBlockPos();
                    BlockPos nearest = ArenaTracker.getNearest(playerPos);

                    if (nearest == null) {
                        context.getSource().sendFeedback(() -> Text.literal("Henüz hiçbir arena oluşmadı."), false);
                        return 0;
                    }

                    context.getSource().sendFeedback(() ->
                            Text.literal("En yakın arena: " + nearest.getX() + " " + nearest.getY() + " " + nearest.getZ()), false);
                    return 1;
                }));
    }
}
