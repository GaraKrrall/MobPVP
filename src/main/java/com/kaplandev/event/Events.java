package com.kaplandev.event;

import com.kaplandev.event.totem.COPPER_BLOCK;
import com.kaplandev.event.totem.IRON_BLOCK;
import com.kaplandev.event.totem.machine.INDUSTRIAL_OVEN;

import net.fabricmc.fabric.api.event.player.UseBlockCallback;

public class Events {

    public static void register() {
        UseBlockCallback.EVENT.register(IRON_BLOCK::onUseBlock);
        UseBlockCallback.EVENT.register(COPPER_BLOCK::onUseBlock);
        UseBlockCallback.EVENT.register(INDUSTRIAL_OVEN::onUseBlock);
    }
}
