package mc.garakrral.command;

import mc.garakrral.build.ArenaTracker;
import mc.garakrral.command.piece.DashCommandPiece;
import mc.garakrral.data.LevelData;
import com.mojang.brigadier.CommandDispatcher;
import me.shedaniel.autoconfig.AutoConfig;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.text.Text;
import net.minecraft.util.math.BlockPos;

import java.util.HashSet;
import java.util.Set;

public class CommandManager {

    public static void register(CommandDispatcher<ServerCommandSource> dispatcher) {

        var mobpvp = net.minecraft.server.command.CommandManager.literal("mobpvp");

        // /mobpvp LocateArena
        mobpvp.then(net.minecraft.server.command.CommandManager.literal("LocateArena")
                .executes(context -> {
                    BlockPos playerPos = context.getSource().getPlayer().getBlockPos();
                    BlockPos nearest = ArenaTracker.getNearest(playerPos);

                    if (nearest == null) {
                        context.getSource().sendFeedback(() -> Text.literal("No arenas have been created yet."), false);
                        return 0;
                    }

                    context.getSource().sendFeedback(() ->
                            Text.literal("Nearest arena: " + nearest.getX() + " " + nearest.getY() + " " + nearest.getZ()), false);
                    return 1;
                })
        );

        // /mobpvp ClearMobLevelData
        mobpvp.then(net.minecraft.server.command.CommandManager.literal("ClearMobLevelData")
                .requires(source -> true)
                .executes(context -> {
                    MinecraftServer server = context.getSource().getServer();
                    LevelData data = AutoConfig.getConfigHolder(LevelData.class).getConfig();

                    Set<String> alive = new HashSet<>();

                    server.getWorlds().forEach(world ->
                            world.iterateEntities().forEach(entity ->
                                    alive.add(entity.getUuid().toString()))
                    );

                    int before = data.levels.size();
                    data.levels.keySet().removeIf(uuid -> !alive.contains(uuid));
                    int after = data.levels.size();

                    AutoConfig.getConfigHolder(LevelData.class).save();

                    int removed = before - after;
                    context.getSource().sendFeedback(() ->
                            Text.literal("§a" + removed + " dead entity records have been cleared. §7(" + after + " remaining)"), false);
                    return 1;
                })
        );

        // /mobpvp dash
        mobpvp.then(net.minecraft.server.command.CommandManager.literal("dash")
                .executes(DashCommandPiece::run)
        );

        // /mobpvp hi
        mobpvp.then(net.minecraft.server.command.CommandManager.literal("hi")
                .executes(context -> {
                    context.getSource().sendFeedback(() -> Text.literal("Hello!"), true);
                    return 1;
                })
        );

        dispatcher.register(mobpvp);
    }
}
