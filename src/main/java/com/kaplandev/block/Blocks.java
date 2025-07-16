package com.kaplandev.block;

import com.kaplandev.block.behavior.AcidicOreBehavior;
import com.kaplandev.block.behavior.MobTableBehavior;

import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.util.math.intprovider.UniformIntProvider;

import static com.kaplandev.block.util.BlockCreator.createCustomBlock;
import static com.kaplandev.block.util.BlockRegister.registerBlock;
import static com.kaplandev.util.path.Paths.CRUDE_ACIDIC_ORE_KEY;
import static com.kaplandev.util.path.Paths.MOB_TABLE_KEY;

public class Blocks {

    public static final Block CRUDE_ACIDIC_ORE;
    public static final Block MOB_TABLE;
    public static void init() {}

    static {
        CRUDE_ACIDIC_ORE = registerBlock(CRUDE_ACIDIC_ORE_KEY, createCustomBlock(AbstractBlock.Settings.create().strength(4.0f).requiresTool().sounds(BlockSoundGroup.STONE), new AcidicOreBehavior(), UniformIntProvider.create(0, 0)));
        MOB_TABLE = registerBlock(MOB_TABLE_KEY, createCustomBlock(AbstractBlock.Settings.create().strength(2.0f).requiresTool().sounds(BlockSoundGroup.WOOD), new MobTableBehavior(), UniformIntProvider.create(0, 0)));
    }
}
