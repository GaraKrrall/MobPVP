package mc.garakrral.entity;

import net.fabricmc.fabric.api.biome.v1.BiomeSelectors;
import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricEntityTypeBuilder;

import net.minecraft.entity.EntityDimensions;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import net.minecraft.world.Heightmap;

import mc.garakrral.block.BlockType;
import mc.garakrral.entity.block.MobTableBlockEntity;
import mc.garakrral.entity.block.PvpSpawnerBlockEntity;
import mc.garakrral.entity.block.PvpSpawnerMaxBlockEntity;
import mc.garakrral.entity.block.UpgradedHopperBlockEntity;
import mc.garakrral.entity.boss.BulwarkEntity;
import mc.garakrral.entity.item.IronReinforcedCopperBallEntity;
import mc.garakrral.entity.mob.GoblinEntity;
import mc.garakrral.entity.mob.GoblinHorseEntity;
import mc.garakrral.entity.mob.HunterEntity;
import mc.garakrral.entity.mob.MadSkeletonEntity;
import mc.garakrral.entity.mob.MadZombieEntity;
import mc.garakrral.entity.mob.MiniCopperGolemEntity;
import mc.garakrral.entity.passive.SnotBallEntity;
import mc.garakrral.entity.passive.MiniIronGolemEntity;
import mc.garakrral.entity.boss.TheGreatProtectorGolemEntity;

import com.kaplanlib.api.builder.EntityAttributeAndSpawnBuilder;
import com.kaplanlib.api.spawn.SpawnLocation;
import com.kaplanlib.util.path.Paths;

import static com.kaplanlib.util.path.Paths.MAD_SKELETON_KEY;
import static com.kaplanlib.util.path.Paths.MAD_ZOMBIE_KEY;
import static com.kaplanlib.util.path.Paths.MOB_TABLE_KEY;
import static com.kaplanlib.util.path.Paths.PVP_SPAWNER_KEY;
import static com.kaplanlib.util.path.Paths.PVP_SPAWNER_MAX_KEY;
import static com.kaplanlib.util.path.Paths.REINFORCED_COPPER_BALL_KEY;
import static mc.garakrral.entity.EntityType.BULWARK;
import static mc.garakrral.entity.EntityType.GOBLIN;
import static mc.garakrral.entity.EntityType.GOBLIN_HORSE;
import static mc.garakrral.entity.EntityType.HUNTER;
import static mc.garakrral.entity.EntityType.IRON_REINFORCED_COPPER_BALL;
import static mc.garakrral.entity.EntityType.MAD_SKELETON;
import static mc.garakrral.entity.EntityType.MAD_ZOMBIE;
import static mc.garakrral.entity.EntityType.MINIGOLEM;
import static mc.garakrral.entity.EntityType.MINIGOLEM_COPPER;
import static mc.garakrral.entity.EntityType.MOB_TABLE;
import static mc.garakrral.entity.EntityType.PVP_SPAWNER;
import static mc.garakrral.entity.EntityType.PVP_SPAWNER_MAX;
import static mc.garakrral.entity.EntityType.SNOT_BALL;
import static mc.garakrral.entity.EntityType.THE_GREAT_PROTECTOR_GOLEM;
import static mc.garakrral.entity.EntityType.UPGREADED_HOPPER;
import static mc.garakrral.mobpvp.MOD_ID;

public class EntityRegister {


    public static void register() {
        EntityAttributeAndSpawnBuilder.create(BULWARK).attributes(BulwarkEntity.createAttributes()).build();
        EntityAttributeAndSpawnBuilder.create(MINIGOLEM).attributes(MiniIronGolemEntity.createAttributes()).build();
        EntityAttributeAndSpawnBuilder.create(MINIGOLEM_COPPER).attributes(MiniCopperGolemEntity.createAttributes()).build();
        EntityAttributeAndSpawnBuilder.create(HUNTER).attributes(HunterEntity.createHunterAttributes()).build();
        EntityAttributeAndSpawnBuilder.create(SNOT_BALL).attributes(SnotBallEntity.createAttributes()).spawn(BiomeSelectors.all(), SpawnGroup.CREATURE, 25, 1, 2, SpawnLocation.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, (type, world, reason, pos, random) -> random.nextFloat() < 0.20f).build();
        EntityAttributeAndSpawnBuilder.create(GOBLIN).attributes(GoblinEntity.createGoblinAttributes()).spawn(BiomeSelectors.foundInOverworld(), SpawnGroup.MONSTER, 100, 2, 4, SpawnLocation.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, (type, world, reason, pos, random) -> {
            long lunarTime = world.getLunarTime() % 24000;
            boolean isNight = lunarTime >= 12000 && lunarTime <= 24000;
            return isNight && world.getLightLevel(pos) < 8 && MobEntity.canMobSpawn(type, world, reason, pos, random);
        }).build();
        EntityAttributeAndSpawnBuilder.create(THE_GREAT_PROTECTOR_GOLEM).attributes(TheGreatProtectorGolemEntity.createAttributes()).build();
        EntityAttributeAndSpawnBuilder.create(GOBLIN_HORSE)
                .attributes(GoblinHorseEntity.createGoblinHorseAttributes())
                .spawn(
                        BiomeSelectors.foundInOverworld(),
                        SpawnGroup.MONSTER,
                        30, // sık değil, ama var
                        1, 1,
                        SpawnLocation.ON_GROUND,
                        Heightmap.Type.MOTION_BLOCKING_NO_LEAVES,
                        (type, worldAccess, spawnReason, pos, random) -> {
                            if (!(worldAccess instanceof net.minecraft.server.world.ServerWorld world)) return false;

                            if (world.isDay()) return false;
                            boolean canSpawn = world.getLightLevel(pos) < 8 && MobEntity.canMobSpawn(type, world, spawnReason, pos, random);
                            if (!canSpawn) return false;

                            if (random.nextFloat() < 0.8f) return true;

                            GoblinHorseEntity horse = new GoblinHorseEntity(GOBLIN_HORSE, world);
                            horse.refreshPositionAndAngles(pos.getX() + 0.5, pos.getY(), pos.getZ() + 0.5, random.nextFloat() * 360F, 0);

                            GoblinEntity goblin = new GoblinEntity(GOBLIN, world);
                            goblin.refreshPositionAndAngles(pos.getX() + 0.5, pos.getY(), pos.getZ() + 0.5, random.nextFloat() * 360F, 0);

                            world.spawnEntity(horse);
                            world.spawnEntity(goblin);
                            goblin.startRiding(horse);

                            return false; // elle spawn ettik
                        }
                )
                .build();

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
        GOBLIN = Registry.register(Registries.ENTITY_TYPE, Identifier.of(MOD_ID, "goblin"), FabricEntityTypeBuilder.createMob().entityFactory(GoblinEntity::new).spawnGroup(SpawnGroup.MONSTER).dimensions(EntityDimensions.fixed(0.6f, 1.95f)).trackRangeBlocks(40).build());
        GOBLIN_HORSE = Registry.register(Registries.ENTITY_TYPE, Identifier.of(MOD_ID, "goblin_horse"), FabricEntityTypeBuilder.createMob().entityFactory(GoblinHorseEntity::new).spawnGroup(SpawnGroup.MONSTER).dimensions(EntityDimensions.fixed(1.3964844F, 1.4F)).trackRangeBlocks(20).build());
        HUNTER = Registry.register(Registries.ENTITY_TYPE, Identifier.of(MOD_ID, "hunter"), FabricEntityTypeBuilder.createMob().entityFactory(HunterEntity::new).spawnGroup(SpawnGroup.MONSTER).dimensions(EntityDimensions.fixed(0.6F, 1.95F)).trackRangeBlocks(20).build());
        THE_GREAT_PROTECTOR_GOLEM = Registry.register(Registries.ENTITY_TYPE, Identifier.of(MOD_ID, "the_great_protector"), FabricEntityTypeBuilder.createMob().entityFactory(TheGreatProtectorGolemEntity::new).spawnGroup(SpawnGroup.AMBIENT).dimensions(EntityDimensions.fixed(12F, 12F)).trackRangeBlocks(15).build());
        SNOT_BALL = Registry.register(Registries.ENTITY_TYPE, Identifier.of(MOD_ID, "snot_ball"), FabricEntityTypeBuilder.createMob().entityFactory(SnotBallEntity::new).spawnGroup(SpawnGroup.CREATURE).dimensions(EntityDimensions.fixed(1F, 1F)).trackRangeBlocks(25).build());
    }
}