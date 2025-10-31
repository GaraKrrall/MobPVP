package mc.garakrral.block;

import net.minecraft.block.AbstractBlock;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.intprovider.UniformIntProvider;

import mc.garakrral.block.behavior.AcidicOreBehavior;
import mc.garakrral.block.behavior.CopperRod;
import mc.garakrral.block.behavior.DamagedPvpSpawnerBlockBehavior;
import mc.garakrral.block.behavior.DamagedPvpSpawnerMaxBlockBehavior;
import mc.garakrral.block.behavior.HeavyCrusherHeadBehavior;
import mc.garakrral.block.behavior.IndustrialOvenBehaivor;
import mc.garakrral.block.behavior.IndustrialOvenBlockBehavior;
import mc.garakrral.block.behavior.MobTableBehavior;
import mc.garakrral.block.behavior.PvpSpawnerBlockBehavior;
import mc.garakrral.block.behavior.PvpSpawnerMaxBlockBehavior;
import mc.garakrral.block.behavior.ReinforcedCopperBlockBehavior;
import mc.garakrral.block.behavior.UpgradedHopperBlockBehavior;
import mc.garakrral.entity.block.PvpSpawnerBlockEntity;
import mc.garakrral.entity.block.UpgradedHopperBlockEntity;

import com.kaplanlib.api.builder.BlockBuilder;

import static com.kaplanlib.util.path.Paths.COPPER_STICK_KEY;
import static com.kaplanlib.util.path.Paths.CRUDE_ACIDIC_ORE_KEY;
import static com.kaplanlib.util.path.Paths.DAMAGED_PVP_SPAWNER_KEY;
import static com.kaplanlib.util.path.Paths.DAMAGED_PVP_SPAWNER_MAX_KEY;
import static com.kaplanlib.util.path.Paths.MOB_TABLE_KEY;
import static com.kaplanlib.util.path.Paths.PVP_SPAWNER_KEY;
import static com.kaplanlib.util.path.Paths.PVP_SPAWNER_MAX_KEY;
import static com.kaplanlib.util.path.Paths.REINFORCED_COPPER_BLOCK_KEY;
import static mc.garakrral.block.BlockType.CRUDE_ACIDIC_ORE;
import static mc.garakrral.block.BlockType.DAMAGED_PVP_SPAWNER;
import static mc.garakrral.block.BlockType.DAMAGED_PVP_SPAWNER_MAX;
import static mc.garakrral.block.BlockType.HEAVY_CRUSHER_HEAD;
import static mc.garakrral.block.BlockType.INDUSTRIAL_OVEN;
import static mc.garakrral.block.BlockType.INDUSTRIAL_OVEN_BLOCK;
import static mc.garakrral.block.BlockType.MOB_TABLE;
import static mc.garakrral.block.BlockType.PVP_SPAWNER;
import static mc.garakrral.block.BlockType.PVP_SPAWNER_MAX;
import static mc.garakrral.block.BlockType.REINFORCED_COPPER_BLOCK;
import static mc.garakrral.block.BlockType.REINFORCED_COPPER_STICK;
import static mc.garakrral.block.BlockType.UPGREADED_HOPPER;
import static mc.garakrral.mobpvp.MOD_ID;

public class Blocks {
    public static void init() {
    }

    static {
        CRUDE_ACIDIC_ORE = BlockBuilder.create(CRUDE_ACIDIC_ORE_KEY, AbstractBlock.Settings.create().strength(4.0f).requiresTool().sounds(BlockSoundGroup.STONE)).behavior(new AcidicOreBehavior()).xpDrop(UniformIntProvider.create(0, 0)).register();
        MOB_TABLE = Registry.register(Registries.BLOCK, Identifier.of(MOD_ID, MOB_TABLE_KEY), new MobTableBehavior(AbstractBlock.Settings.create().strength(2.0f).requiresTool().sounds(BlockSoundGroup.WOOD)));
        PVP_SPAWNER = BlockBuilder.RegisterCreatedBlockWithEntity(PVP_SPAWNER_KEY, new PvpSpawnerBlockBehavior(AbstractBlock.Settings.create().strength(5.0f).requiresTool().resistance(1200.0f).hardness(-1.0f).sounds(BlockSoundGroup.TRIAL_SPAWNER).nonOpaque()), PvpSpawnerBlockEntity::new);
        UPGREADED_HOPPER = BlockBuilder.RegisterCreatedBlockWithEntity("upgraded_hopper", new UpgradedHopperBlockBehavior(AbstractBlock.Settings.create().strength(8.0f).requiresTool().resistance(1.0f).hardness(1.0f).sounds(BlockSoundGroup.METAL).nonOpaque()), UpgradedHopperBlockEntity::new);
        PVP_SPAWNER_MAX = BlockBuilder.RegisterCreatedBlockWithEntity(PVP_SPAWNER_MAX_KEY, new PvpSpawnerMaxBlockBehavior(AbstractBlock.Settings.create().strength(5.0f).requiresTool().resistance(1200.0f).hardness(-1.0f).sounds(BlockSoundGroup.TRIAL_SPAWNER).nonOpaque()), PvpSpawnerBlockEntity::new);
        DAMAGED_PVP_SPAWNER = BlockBuilder.create(DAMAGED_PVP_SPAWNER_KEY, AbstractBlock.Settings.create().strength(5.0f).requiresTool().sounds(BlockSoundGroup.TRIAL_SPAWNER)).behavior(new DamagedPvpSpawnerBlockBehavior()).xpDrop(UniformIntProvider.create(0, 0)).register();
        DAMAGED_PVP_SPAWNER_MAX = BlockBuilder.create(DAMAGED_PVP_SPAWNER_MAX_KEY, AbstractBlock.Settings.create().strength(5.0f).requiresTool().sounds(BlockSoundGroup.TRIAL_SPAWNER)).behavior(new DamagedPvpSpawnerMaxBlockBehavior()).xpDrop(UniformIntProvider.create(0, 0)).register();
        REINFORCED_COPPER_BLOCK = BlockBuilder.create(REINFORCED_COPPER_BLOCK_KEY, AbstractBlock.Settings.create().strength(5.0f).requiresTool().sounds(BlockSoundGroup.COPPER)).behavior(new ReinforcedCopperBlockBehavior()).xpDrop(UniformIntProvider.create(0, 0)).register();
        REINFORCED_COPPER_STICK = Registry.register(Registries.BLOCK, Identifier.of(MOD_ID, COPPER_STICK_KEY), new CopperRod(AbstractBlock.Settings.create().strength(5.0f).requiresTool().sounds(BlockSoundGroup.COPPER)));
        HEAVY_CRUSHER_HEAD = BlockBuilder.create("heavy_crusher_head", AbstractBlock.Settings.create().strength(15.0f).requiresTool().sounds(BlockSoundGroup.ANVIL)).behavior(new HeavyCrusherHeadBehavior()).xpDrop(UniformIntProvider.create(0, 0)).register();
        INDUSTRIAL_OVEN_BLOCK = BlockBuilder.create("industrial_oven_block", AbstractBlock.Settings.create().strength(20.0f).requiresTool().sounds(BlockSoundGroup.METAL)).behavior(new IndustrialOvenBlockBehavior()).xpDrop(UniformIntProvider.create(0, 0)).register();
        INDUSTRIAL_OVEN = BlockBuilder.create("industrial_oven", AbstractBlock.Settings.create().strength(25.0f).requiresTool().sounds(BlockSoundGroup.METAL)).behavior(new IndustrialOvenBehaivor()).xpDrop(UniformIntProvider.create(0, 0)).register();
    }
}
