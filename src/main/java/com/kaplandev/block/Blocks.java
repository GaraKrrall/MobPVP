package com.kaplandev.block;

import com.kaplandev.block.behavior.AcidicOreBehavior;
import com.kaplandev.block.behavior.MobTableBehavior;
import com.kaplandev.block.util.BlockBuilder;

import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.util.math.intprovider.UniformIntProvider;

import static com.kaplandev.util.path.Paths.CRUDE_ACIDIC_ORE_KEY;
import static com.kaplandev.util.path.Paths.MOB_TABLE_KEY;

public class Blocks {

    public static final Block CRUDE_ACIDIC_ORE;
    public static final Block MOB_TABLE;
    public static void init() {}

    static {
        CRUDE_ACIDIC_ORE = BlockBuilder.RegisterCreatedBlock(CRUDE_ACIDIC_ORE_KEY, BlockBuilder.BuildBlockAttribute(AbstractBlock.Settings.create().strength(4.0f).requiresTool().sounds(BlockSoundGroup.STONE), new AcidicOreBehavior(), UniformIntProvider.create(0, 0)));
        MOB_TABLE = BlockBuilder.RegisterCreatedBlock(MOB_TABLE_KEY, BlockBuilder.BuildBlockAttribute(AbstractBlock.Settings.create().strength(2.0f).requiresTool().sounds(BlockSoundGroup.WOOD), new MobTableBehavior(), UniformIntProvider.create(0, 0)));
    }
}
