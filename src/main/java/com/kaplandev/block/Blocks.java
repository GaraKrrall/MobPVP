package com.kaplandev.block;

import com.kaplandev.block.behavior.AcidicOreBehavior;
import com.kaplandev.block.behavior.CopperRod;
import com.kaplandev.block.behavior.DamagedPvpSpawnerBlockBehavior;
import com.kaplandev.block.behavior.DamagedPvpSpawnerMaxBlockBehavior;
import com.kaplandev.block.behavior.MobTableBehavior;
import com.kaplandev.block.behavior.PvpSpawnerBlockBehavior;
import com.kaplandev.block.behavior.PvpSpawnerMaxBlockBehavior;
import com.kaplandev.block.behavior.ReinforcedCopperBlockBehavior;
import com.kaplandev.entity.block.PvpSpawnerBlockEntity;
import com.kaplanlib.api.builder.BlockBuilder;

import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.intprovider.UniformIntProvider;

import static com.kaplandev.mobpvp.MOD_ID;
import static com.kaplanlib.util.path.Paths.COPPER_STICK_KEY;
import static com.kaplanlib.util.path.Paths.CRUDE_ACIDIC_ORE_KEY;
import static com.kaplanlib.util.path.Paths.DAMAGED_PVP_SPAWNER_KEY;
import static com.kaplanlib.util.path.Paths.DAMAGED_PVP_SPAWNER_MAX_KEY;
import static com.kaplanlib.util.path.Paths.MOB_TABLE_KEY;
import static com.kaplanlib.util.path.Paths.PVP_SPAWNER_KEY;
import static com.kaplanlib.util.path.Paths.PVP_SPAWNER_MAX_KEY;
import static com.kaplanlib.util.path.Paths.REINFORCED_COPPER_BLOCK_KEY;

public class Blocks {

    public static final Block CRUDE_ACIDIC_ORE;
    public static final Block MOB_TABLE;
    public static final Block PVP_SPAWNER;
    public static final Block PVP_SPAWNER_MAX;
    public static final Block DAMAGED_PVP_SPAWNER;
    public static final Block DAMAGED_PVP_SPAWNER_MAX;
    public static final Block REINFORCED_COPPER_BLOCK;
    //public static final Block IRON_CHEST;
    public static final Block REINFORCED_COPPER_STICK;
    public static void init() {}

    static {
        CRUDE_ACIDIC_ORE = BlockBuilder.create(CRUDE_ACIDIC_ORE_KEY, AbstractBlock.Settings.create().strength(4.0f).requiresTool().sounds(BlockSoundGroup.STONE)).behavior(new AcidicOreBehavior()).xpDrop(UniformIntProvider.create(0, 0)).register();
        MOB_TABLE = Registry.register(Registries.BLOCK, Identifier.of(MOD_ID, MOB_TABLE_KEY), new MobTableBehavior(AbstractBlock.Settings.create().strength(2.0f).requiresTool().sounds(BlockSoundGroup.WOOD)));
        PVP_SPAWNER = BlockBuilder.RegisterCreatedBlockWithEntity(PVP_SPAWNER_KEY, new PvpSpawnerBlockBehavior(AbstractBlock.Settings.create().strength(5.0f).requiresTool().resistance(1200.0f).hardness(-1.0f).sounds(BlockSoundGroup.TRIAL_SPAWNER).nonOpaque()), PvpSpawnerBlockEntity::new);
        PVP_SPAWNER_MAX = BlockBuilder.RegisterCreatedBlockWithEntity(PVP_SPAWNER_MAX_KEY, new PvpSpawnerMaxBlockBehavior(AbstractBlock.Settings.create().strength(5.0f).requiresTool().resistance(1200.0f).hardness(-1.0f).sounds(BlockSoundGroup.TRIAL_SPAWNER).nonOpaque()), PvpSpawnerBlockEntity::new);
        DAMAGED_PVP_SPAWNER = BlockBuilder.create(DAMAGED_PVP_SPAWNER_KEY, AbstractBlock.Settings.create().strength(5.0f).requiresTool().sounds(BlockSoundGroup.TRIAL_SPAWNER)).behavior(new DamagedPvpSpawnerBlockBehavior()).xpDrop(UniformIntProvider.create(0, 0)).register();
        DAMAGED_PVP_SPAWNER_MAX = BlockBuilder.create(DAMAGED_PVP_SPAWNER_MAX_KEY, AbstractBlock.Settings.create().strength(5.0f).requiresTool().sounds(BlockSoundGroup.TRIAL_SPAWNER)).behavior(new DamagedPvpSpawnerMaxBlockBehavior()).xpDrop(UniformIntProvider.create(0, 0)).register();
        // IRON_CHEST = BlockBuilder
        //     .create(IRON_CHEST_KEY, AbstractBlock.Settings.create().strength(8.0f).requiresTool().resistance(1200.0f).sounds(BlockSoundGroup.WOOD))
        //     .withEntity((pos, state) -> new IronChestBlockEntity(pos, state))
        //     .register();
        REINFORCED_COPPER_BLOCK = BlockBuilder.create(REINFORCED_COPPER_BLOCK_KEY, AbstractBlock.Settings.create().strength(5.0f).requiresTool().sounds(BlockSoundGroup.COPPER)).behavior(new ReinforcedCopperBlockBehavior()).xpDrop(UniformIntProvider.create(0, 0)).register();
        REINFORCED_COPPER_STICK = Registry.register(Registries.BLOCK, Identifier.of(MOD_ID, COPPER_STICK_KEY), new CopperRod(AbstractBlock.Settings.create().strength(5.0f).requiresTool().sounds(BlockSoundGroup.COPPER)));
    }
}
