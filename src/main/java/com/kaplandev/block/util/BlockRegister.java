package com.kaplandev.block.util;

import net.minecraft.block.Block;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

import static com.kaplandev.mobpvp.MOD_ID;
@Deprecated(forRemoval = true)
public class BlockRegister {
    @Deprecated(forRemoval = true)
    public static Block registerBlock(String name, Block block) {
        // Bloğu kaydet
        Block registeredBlock = Registry.register(Registries.BLOCK, Identifier.of(MOD_ID, name), block);
        return registeredBlock;
    }
}
