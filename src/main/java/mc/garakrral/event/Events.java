package mc.garakrral.event;

import mc.garakrral.event.totem.COPPER_BLOCK;
import mc.garakrral.event.totem.IRON_BLOCK;
import mc.garakrral.event.totem.machine.INDUSTRIAL_OVEN;

import net.fabricmc.fabric.api.event.player.UseBlockCallback;

public class Events {

    public static void register() {
        UseBlockCallback.EVENT.register(IRON_BLOCK::onUseBlock);
        UseBlockCallback.EVENT.register(COPPER_BLOCK::onUseBlock);
        UseBlockCallback.EVENT.register(INDUSTRIAL_OVEN::onUseBlock);
    }
}
