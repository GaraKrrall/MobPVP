package com.kaplandev.items.blocks;

import com.kaplandev.items.CrudeAcidicLayerRockOreBlock;
import net.minecraft.block.AbstractBlock;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.util.Identifier;
import net.minecraft.block.Block;

public class CrudeAcidicLayerRockOre {

    public static final Block ORE = new CrudeAcidicLayerRockOreBlock(AbstractBlock.Settings
            .create()
            .strength(4.0f, 4.0f)
            .requiresTool()
            .sounds(BlockSoundGroup.STONE));

    public static void register() {
        Registry.register(Registries.BLOCK, Identifier.of("mobpvp", "ore"), ORE);
    }
}
