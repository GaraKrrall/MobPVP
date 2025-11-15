package mc.garakrral.event;

import mc.garakrral.event.craft.INDUSTRIAL_TO_HEAVY;
import mc.garakrral.event.craft.INDUSTRIAL_TO_OVEN;
import mc.garakrral.event.craft.IRON_TO_INDUSTRIAL;
import mc.garakrral.event.totem.COPPER_BLOCK;
import mc.garakrral.event.totem.IRON_BLOCK;
import mc.garakrral.event.totem.machine.INDUSTRIAL_OVEN;

import net.fabricmc.fabric.api.event.player.UseBlockCallback;

public class Events {

    public static void register() {
        UseBlockCallback.EVENT.register(IRON_BLOCK::onUseBlock);
        UseBlockCallback.EVENT.register(COPPER_BLOCK::onUseBlock);
        UseBlockCallback.EVENT.register(INDUSTRIAL_OVEN::onUseBlock);
        UseBlockCallback.EVENT.register(IRON_TO_INDUSTRIAL::onUseBlock);
        UseBlockCallback.EVENT.register(INDUSTRIAL_TO_OVEN::onUseBlock);
        UseBlockCallback.EVENT.register(INDUSTRIAL_TO_HEAVY::onUseBlock);
    }
}
