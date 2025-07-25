package com.kaplandev.block;

import com.kaplandev.block.behavior.AcidicOreBehavior;
import com.kaplandev.block.behavior.DamagedPvpSpawnerBlockBehavior;
import com.kaplandev.block.behavior.DamagedPvpSpawnerMaxBlockBehavior;
import com.kaplandev.block.behavior.MobTableBehavior;
import com.kaplandev.block.behavior.PvpSpawnerBlockBehavior;
import com.kaplandev.block.behavior.PvpSpawnerMaxBlockBehavior;
import com.kaplandev.entity.block.PvpSpawnerBlockEntity;
import com.kaplandev.block.util.BlockBuilder;

import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.util.math.intprovider.UniformIntProvider;

import static com.kaplandev.util.path.Paths.CRUDE_ACIDIC_ORE_KEY;
import static com.kaplandev.util.path.Paths.DAMAGED_PVP_SPAWNER_KEY;
import static com.kaplandev.util.path.Paths.DAMAGED_PVP_SPAWNER_MAX_KEY;
import static com.kaplandev.util.path.Paths.MOB_TABLE_KEY;
import static com.kaplandev.util.path.Paths.PVP_SPAWNER_KEY;
import static com.kaplandev.util.path.Paths.PVP_SPAWNER_MAX_KEY;

public class Blocks {

    public static final Block CRUDE_ACIDIC_ORE;
    public static final Block MOB_TABLE;
    public static final Block PVP_SPAWNER;
    public static final Block PVP_SPAWNER_MAX;
    public static final Block DAMAGED_PVP_SPAWNER;
    public static final Block DAMAGED_PVP_SPAWNER_MAX;
    public static void init() {}

    static {
        CRUDE_ACIDIC_ORE = BlockBuilder.RegisterCreatedBlock(CRUDE_ACIDIC_ORE_KEY, BlockBuilder.BuildBlockAttribute(AbstractBlock.Settings.create().strength(4.0f).requiresTool().sounds(BlockSoundGroup.STONE), new AcidicOreBehavior(), UniformIntProvider.create(0, 0)));
        MOB_TABLE = BlockBuilder.RegisterCreatedBlock(MOB_TABLE_KEY, BlockBuilder.BuildBlockAttribute(AbstractBlock.Settings.create().strength(2.0f).requiresTool().sounds(BlockSoundGroup.WOOD), new MobTableBehavior(), UniformIntProvider.create(0, 0)));
        PVP_SPAWNER = BlockBuilder.RegisterCreatedBlockWithEntity(PVP_SPAWNER_KEY, new PvpSpawnerBlockBehavior(AbstractBlock.Settings.create().strength(5.0f).requiresTool().resistance(1200.0f).hardness(-1.0f).sounds(BlockSoundGroup.TRIAL_SPAWNER)), PvpSpawnerBlockEntity::new);
        PVP_SPAWNER_MAX = BlockBuilder.RegisterCreatedBlockWithEntity(PVP_SPAWNER_MAX_KEY, new PvpSpawnerMaxBlockBehavior(AbstractBlock.Settings.create().strength(5.0f).requiresTool().resistance(1200.0f).hardness(-1.0f).sounds(BlockSoundGroup.TRIAL_SPAWNER)), PvpSpawnerBlockEntity::new);
        DAMAGED_PVP_SPAWNER = BlockBuilder.RegisterCreatedBlock(DAMAGED_PVP_SPAWNER_KEY, BlockBuilder.BuildBlockAttribute(AbstractBlock.Settings.create().strength(5.0f).requiresTool().sounds(BlockSoundGroup.TRIAL_SPAWNER), new DamagedPvpSpawnerBlockBehavior(), UniformIntProvider.create(0, 0)));
        DAMAGED_PVP_SPAWNER_MAX = BlockBuilder.RegisterCreatedBlock(DAMAGED_PVP_SPAWNER_MAX_KEY, BlockBuilder.BuildBlockAttribute(AbstractBlock.Settings.create().strength(5.0f).requiresTool().sounds(BlockSoundGroup.TRIAL_SPAWNER), new DamagedPvpSpawnerMaxBlockBehavior(), UniformIntProvider.create(0, 0)));
    }
}
