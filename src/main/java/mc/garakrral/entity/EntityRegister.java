package mc.garakrral.entity;

import mc.garakrral.block.BlockType;
import mc.garakrral.entity.block.MobTableBlockEntity;
import mc.garakrral.entity.block.PvpSpawnerBlockEntity;
import mc.garakrral.entity.block.PvpSpawnerMaxBlockEntity;
import mc.garakrral.entity.block.UpgradedHopperBlockEntity;
import mc.garakrral.entity.boss.BulwarkEntity;
import mc.garakrral.entity.item.IronReinforcedCopperBallEntity;
import mc.garakrral.entity.mob.MadSkeletonEntity;
import mc.garakrral.entity.mob.MadZombieEntity;
import mc.garakrral.entity.mob.MiniCopperGolemEntity;
import mc.garakrral.entity.passive.MiniIronGolemEntity;
import com.kaplanlib.api.builder.EntityAttributeAndSpawnBuilder;
import com.kaplanlib.util.path.Paths;

import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricEntityTypeBuilder;
import net.minecraft.entity.EntityDimensions;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

import static mc.garakrral.entity.EntityType.BULWARK;
import static mc.garakrral.entity.EntityType.IRON_REINFORCED_COPPER_BALL;
import static mc.garakrral.entity.EntityType.MAD_SKELETON;
import static mc.garakrral.entity.EntityType.MAD_ZOMBIE;
import static mc.garakrral.entity.EntityType.MINIGOLEM;
import static mc.garakrral.entity.EntityType.MINIGOLEM_COPPER;
import static mc.garakrral.entity.EntityType.MOB_TABLE;
import static mc.garakrral.entity.EntityType.PVP_SPAWNER;
import static mc.garakrral.entity.EntityType.PVP_SPAWNER_MAX;
import static mc.garakrral.entity.EntityType.UPGREADED_HOPPER;
import static mc.garakrral.mobpvp.MOD_ID;
import static com.kaplanlib.util.path.Paths.MAD_SKELETON_KEY;
import static com.kaplanlib.util.path.Paths.MAD_ZOMBIE_KEY;
import static com.kaplanlib.util.path.Paths.MOB_TABLE_KEY;
import static com.kaplanlib.util.path.Paths.PVP_SPAWNER_KEY;
import static com.kaplanlib.util.path.Paths.PVP_SPAWNER_MAX_KEY;
import static com.kaplanlib.util.path.Paths.REINFORCED_COPPER_BALL_KEY;



public class EntityRegister {

    public static void register() {
        System.out.println("Custom entities registered.");
        EntityAttributeAndSpawnBuilder.create(BULWARK).attributes(BulwarkEntity.createAttributes()).build();
        EntityAttributeAndSpawnBuilder.create(MINIGOLEM).attributes(MiniIronGolemEntity.createAttributes()).build();
        EntityAttributeAndSpawnBuilder.create(MINIGOLEM_COPPER).attributes(MiniCopperGolemEntity.createAttributes()).build();
        System.out.println("Mob özellikleri kaydedildi");
    }

    static {
        MAD_ZOMBIE = Registry.register(Registries.ENTITY_TYPE, Identifier.of(MOD_ID, MAD_ZOMBIE_KEY), FabricEntityTypeBuilder.createMob().entityFactory(MadZombieEntity::new).defaultAttributes(MadZombieEntity::createCustomZombieAttributes).spawnGroup(SpawnGroup.MONSTER).dimensions(EntityDimensions.fixed(0.6f, 1.95f)).build());
        MAD_SKELETON = Registry.register(Registries.ENTITY_TYPE, Identifier.of(MOD_ID, MAD_SKELETON_KEY), FabricEntityTypeBuilder.createMob().entityFactory(MadSkeletonEntity::new).defaultAttributes(MadSkeletonEntity::createCustomSkeletonAttributes).spawnGroup(SpawnGroup.MONSTER).dimensions(EntityDimensions.fixed(0.6f, 1.99f)).build());
        BULWARK = Registry.register(Registries.ENTITY_TYPE, Identifier.of(MOD_ID, Paths.BULWARK), FabricEntityTypeBuilder.createMob().entityFactory(BulwarkEntity::new).spawnGroup(SpawnGroup.MONSTER).dimensions(EntityDimensions.fixed(2.0f, 5.0f)).trackRangeBlocks(80).build());
        MINIGOLEM = Registry.register(Registries.ENTITY_TYPE, Identifier.of(MOD_ID, Paths.MINIGOLEM), FabricEntityTypeBuilder.createMob().entityFactory(MiniIronGolemEntity::new).spawnGroup(SpawnGroup.CREATURE).dimensions(EntityDimensions.fixed(0.8f, 1.0f)).trackRangeBlocks(80).build());
        MINIGOLEM_COPPER = Registry.register(Registries.ENTITY_TYPE, Identifier.of(MOD_ID, "mini_copper_golem"), FabricEntityTypeBuilder.createMob().entityFactory(MiniCopperGolemEntity::new).spawnGroup(SpawnGroup.CREATURE).dimensions(EntityDimensions.fixed(0.8f, 1.0f)).trackRangeBlocks(160).build());
        PVP_SPAWNER = Registry.register(Registries.BLOCK_ENTITY_TYPE, Identifier.of(MOD_ID, PVP_SPAWNER_KEY), FabricBlockEntityTypeBuilder.create(PvpSpawnerBlockEntity::new, BlockType.PVP_SPAWNER).build());
        PVP_SPAWNER_MAX = Registry.register(Registries.BLOCK_ENTITY_TYPE, Identifier.of(MOD_ID, PVP_SPAWNER_MAX_KEY), FabricBlockEntityTypeBuilder.create(PvpSpawnerMaxBlockEntity::new, BlockType.PVP_SPAWNER_MAX).build());
        UPGREADED_HOPPER = Registry.register(Registries.BLOCK_ENTITY_TYPE, Identifier.of(MOD_ID, "upgraded_hopper"), FabricBlockEntityTypeBuilder.create(UpgradedHopperBlockEntity::new, BlockType.UPGREADED_HOPPER).build());
        MOB_TABLE = Registry.register(Registries.BLOCK_ENTITY_TYPE, Identifier.of(MOD_ID, MOB_TABLE_KEY), FabricBlockEntityTypeBuilder.create(MobTableBlockEntity::new, BlockType.MOB_TABLE).build());
        IRON_REINFORCED_COPPER_BALL = Registry.register(Registries.ENTITY_TYPE, MOD_ID, net.minecraft.entity.EntityType.Builder.<IronReinforcedCopperBallEntity>create(IronReinforcedCopperBallEntity::new, SpawnGroup.MISC).dimensions(0.25f, 0.25f).maxTrackingRange(4).trackingTickInterval(10).build(REINFORCED_COPPER_BALL_KEY));
    }

}