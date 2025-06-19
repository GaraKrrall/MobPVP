package com.kaplandev.commands;

import com.kaplandev.commands.dashSys.DashCommand;
import com.mojang.brigadier.CommandDispatcher;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.text.Text;

public class ModCommands {
    public static void register(CommandDispatcher<ServerCommandSource> dispatcher) {
        dispatcher.register(CommandManager.literal("selam").executes(context -> {
            context.getSource().sendFeedback(() -> Text.literal("Merhaba! Bu bir test komutudur."), false);
            return 1;
        }));

        dispatcher.register(CommandManager.literal("trigger")
                .then(CommandManager.literal("dash")
                        .executes(ctx -> DashCommand.run(ctx))
                ));
    }
}
