package mc.garakrral.handler.oven;

import me.shedaniel.autoconfig.AutoConfig;

import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;

import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;

import mc.garakrral.data.OvenData;
import mc.garakrral.event.totem.machine.INDUSTRIAL_OVEN;
import mc.garakrral.event.totem.machine.recipe.IndustrialOvenRecipes;

public class OvenTickHandler {
    public static void register() {
        ServerTickEvents.END_WORLD_TICK.register(OvenTickHandler::tick);
    }

    private static void tick(ServerWorld world) {
        var configHolder = AutoConfig.getConfigHolder(OvenData.class);
        OvenData data = configHolder.getConfig();

        if (data.activeOvens.isEmpty()) return;

        for (var task : data.activeOvens.toArray(new OvenData.OvenTask[0])) {
            task.remainingTicks--;
            if (task.remainingTicks <= 0) {
                try {
                    BlockPos pos = parsePos(task.pos);
                    var recipe = IndustrialOvenRecipes.getRecipeById(task.recipeId);
                    if (recipe != null) {
                        INDUSTRIAL_OVEN.finishProcess(world, pos, recipe);
                    }
                    data.deactivate(task.pos);
                    configHolder.save();
                } catch (Exception ignored) {
                }
            }
        }
    }

    private static BlockPos parsePos(String str) {
        String[] s = str.split(",");
        return new BlockPos(Integer.parseInt(s[0]), Integer.parseInt(s[1]), Integer.parseInt(s[2]));
    }
}
