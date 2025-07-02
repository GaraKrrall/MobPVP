package com.kaplandev.block;


import com.kaplandev.block.behavior.AcidicOreBehavior;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.intprovider.UniformIntProvider;

import static com.kaplandev.block.util.BlockCreator.createCustomBlock;
import static com.kaplandev.mobpvp.MOD_ID;
import static com.kaplandev.strings.path.Paths.CRUDE_ACIDIC_ORE_KEY;


public class Blocks {

    // Block Tanımları
    public static final Block CRUDE_ACIDIC_ORE = registerBlock(CRUDE_ACIDIC_ORE_KEY, createCustomBlock(AbstractBlock.Settings.create().strength(4.0f).requiresTool().sounds(BlockSoundGroup.STONE), new AcidicOreBehavior(), UniformIntProvider.create(0, 0)));


    public static void init() {


    }

    // Yardımcı fonksiyonlar
    public static Block registerBlock(String name, Block block) {
        // Bloğu kaydet
        Block registeredBlock = Registry.register(Registries.BLOCK, Identifier.of(MOD_ID, name), block);
        return registeredBlock;
    }


}
