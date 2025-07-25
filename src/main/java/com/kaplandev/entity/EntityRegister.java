package com.kaplandev.entity;

import com.kaplandev.block.Blocks;
import com.kaplandev.entity.block.PvpSpawnerMaxBlockEntity;
import com.kaplandev.entity.boss.BulwarkEntity;
import com.kaplandev.entity.mob.MadSkeletonEntity;
import com.kaplandev.entity.passive.MiniIronGolemEntity;
import com.kaplandev.entity.mob.MadZombieEntity;
import com.kaplandev.entity.spawn.LuckySpawnLocation;
import com.kaplandev.entity.util.EntityAttributeAndSpawnBuilder;
import com.kaplandev.util.path.Paths;
import com.kaplandev.entity.block.PvpSpawnerBlockEntity;

import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricEntityTypeBuilder;
import net.minecraft.entity.*;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.registry.tag.BiomeTags;
import net.minecraft.util.Identifier;
import net.minecraft.registry.*;
import net.minecraft.entity.EntityDimensions;
import net.minecraft.world.Heightmap;

import static com.kaplandev.entity.EntityType.BULWARK;
import static com.kaplandev.entity.EntityType.MINIGOLEM;
import static com.kaplandev.entity.EntityType.MAD_SKELETON;
import static com.kaplandev.entity.EntityType.MAD_ZOMBIE;
import static com.kaplandev.entity.EntityType.PVP_SPAWNER;
import static com.kaplandev.entity.EntityType.PVP_SPAWNER_MAX;
import static com.kaplandev.mobpvp.MOD_ID;
import static com.kaplandev.util.path.Paths.MAD_SKELETON_KEY;
import static com.kaplandev.util.path.Paths.MAD_ZOMBIE_KEY;
import static com.kaplandev.util.path.Paths.PVP_SPAWNER_KEY;
import static com.kaplandev.util.path.Paths.PVP_SPAWNER_MAX_KEY;


public class EntityRegister {

    public static void register() {
        System.out.println("Custom entities registered.");
        registerSpawns();
        EntityAttributeAndSpawnBuilder.BuildAttribute(BULWARK, BulwarkEntity.createAttributes());
        EntityAttributeAndSpawnBuilder.BuildAttribute(MINIGOLEM, MiniIronGolemEntity.createAttributes());
        System.out.println("Mob özellikleri kaydedildi");
    }
    @SuppressWarnings("removal")
    private static void registerSpawns() {
        EntityAttributeAndSpawnBuilder.BuildSpawn(context -> context.hasTag(BiomeTags.IS_OVERWORLD), SpawnGroup.MONSTER, MAD_SKELETON, 80, 1, 3, LuckySpawnLocation.LUCKY_SPAWN, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, MobEntity::canMobSpawn);
    }

    static {
        MAD_ZOMBIE = Registry.register(Registries.ENTITY_TYPE, Identifier.of(MOD_ID, MAD_ZOMBIE_KEY), FabricEntityTypeBuilder.createMob().entityFactory(MadZombieEntity::new).defaultAttributes(MadZombieEntity::createCustomZombieAttributes).spawnGroup(SpawnGroup.MONSTER).dimensions(EntityDimensions.fixed(0.6f, 1.95f)).build());
        MAD_SKELETON = Registry.register(Registries.ENTITY_TYPE, Identifier.of(MOD_ID, MAD_SKELETON_KEY), FabricEntityTypeBuilder.createMob().entityFactory(MadSkeletonEntity::new).defaultAttributes(MadSkeletonEntity::createCustomSkeletonAttributes).spawnGroup(SpawnGroup.MONSTER).dimensions(EntityDimensions.fixed(0.6f, 1.99f)).build());
        BULWARK = Registry.register(Registries.ENTITY_TYPE, Identifier.of(MOD_ID, Paths.BULWARK), FabricEntityTypeBuilder.createMob().entityFactory(BulwarkEntity::new).spawnGroup(SpawnGroup.MONSTER).dimensions(EntityDimensions.fixed(0.8f, 2.0f)).trackRangeBlocks(80).build());
        MINIGOLEM = Registry.register(Registries.ENTITY_TYPE, Identifier.of(MOD_ID, Paths.MINIGOLEM), FabricEntityTypeBuilder.createMob().entityFactory(MiniIronGolemEntity::new).spawnGroup(SpawnGroup.MONSTER).dimensions(EntityDimensions.fixed(0.8f, 2.0f)).trackRangeBlocks(80).build());
        PVP_SPAWNER = Registry.register(Registries.BLOCK_ENTITY_TYPE, Identifier.of(MOD_ID, PVP_SPAWNER_KEY),FabricBlockEntityTypeBuilder.create(PvpSpawnerBlockEntity::new, Blocks.PVP_SPAWNER).build());
        PVP_SPAWNER_MAX = Registry.register(Registries.BLOCK_ENTITY_TYPE, Identifier.of(MOD_ID, PVP_SPAWNER_MAX_KEY),FabricBlockEntityTypeBuilder.create(PvpSpawnerMaxBlockEntity::new, Blocks.PVP_SPAWNER_MAX).build());
    }

}
/*   BiomeModifications.addSpawn(
                context -> context.hasTag(BiomeTags.IS_OVERWORLD), // Tüm overworld biyomları
                SpawnGroup.MONSTER,
                CUSTOM_ZOMBIE,
                80, // spawn weight (daha yüksek = daha sık)
                1,  // min group
                3   // max group
        );*/

// Ayrıca onların gece gerçekten spawn olabilmesi için şuna da ihtiyacımız var:
    /*    SpawnRestriction.register(
                CUSTOM_ZOMBIE,
                com.kaplandev.entity.spawn.SpawnLocation.ON_GROUND,
                Heightmap.Type.MOTION_BLOCKING_NO_LEAVES,
                MobEntity::canMobSpawn
        );*/