package com.kaplandev.items.blocks;

import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.util.Identifier;

public class CrudeAcidicLayerRockOre {

    public static final Block ORE =new Block(AbstractBlock.Settings
            .create()
            .strength(4.0f, 4.0f) // Dayanıklılık (taş ile aynı veya biraz daha fazla)
            .requiresTool() // El ile veya düşük kazmalarla kırılamasın
            .sounds(BlockSoundGroup.STONE));



    public static void register() {
        Registry.register(Registries.BLOCK, Identifier.of("mobpvp", "ore"), ORE);
    }
}
