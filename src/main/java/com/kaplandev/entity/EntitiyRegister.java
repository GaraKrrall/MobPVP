package com.kaplandev.entity;

import com.kaplandev.entity.boss.BulwarkEntity;
import com.kaplandev.entity.skeleton.CustomSkeletonEntity;
import com.kaplandev.entity.zombie.CustomZombieEntity;
import net.fabricmc.fabric.api.biome.v1.BiomeModifications;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricDefaultAttributeRegistry;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricEntityTypeBuilder;
import net.minecraft.entity.SpawnRestriction.SpawnPredicate;
import net.minecraft.entity.*;
import net.minecraft.entity.attribute.DefaultAttributeRegistry;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.registry.tag.BiomeTags;
import net.minecraft.util.Identifier;
import net.minecraft.registry.*;
import net.minecraft.entity.EntityDimensions;
import net.minecraft.world.Heightmap;

import static com.kaplandev.entity.spawn.LuckySpawnLocation.LUCK;

public class EntitiyRegister {

    public static final EntityType<CustomZombieEntity> CUSTOM_ZOMBIE = Registry.register(
            Registries.ENTITY_TYPE,
            Identifier.of("mobpvp", "custom_zombie"),
            FabricEntityTypeBuilder.createMob()
                    .entityFactory(CustomZombieEntity::new)
                    .defaultAttributes(CustomZombieEntity::createCustomZombieAttributes)
                    .spawnGroup(SpawnGroup.MONSTER)
                    .dimensions(EntityDimensions.fixed(0.6f, 1.95f))
                    .build()
    );

    public static final EntityType<CustomSkeletonEntity> CUSTOM_SKELETON = Registry.register(
            Registries.ENTITY_TYPE,
            Identifier.of("mobpvp", "custom_skeleton"),
            FabricEntityTypeBuilder.createMob()
                    .entityFactory(CustomSkeletonEntity::new)
                    .defaultAttributes(CustomSkeletonEntity::createCustomSkeletonAttributes)
                    .spawnGroup(SpawnGroup.MONSTER)
                    .dimensions(EntityDimensions.fixed(0.6f, 1.99f))
                    .build()
    );

    public static final EntityType<BulwarkEntity> BULWARK = Registry.register(
            Registries.ENTITY_TYPE,
            Identifier.of("modid", "bulwark"),
            FabricEntityTypeBuilder.createMob()
                    .entityFactory(BulwarkEntity::new)
                    .spawnGroup(SpawnGroup.MONSTER)
                    .dimensions(EntityDimensions.fixed(0.8f, 2.0f))
                    .trackRangeBlocks(80)
                    .build()
    );




    public static void register() {
        System.out.println("Custom entities registered.");
        registerSpawns();
        registerAttributes();
        System.out.println("Mob özellikleri kaydedildi");
    }
    private static void registerSpawns() {
        // Custom ZOMBIE her biyomda gece doğsun
     /*   BiomeModifications.addSpawn(
                context -> context.hasTag(BiomeTags.IS_OVERWORLD), // Tüm overworld biyomları
                SpawnGroup.MONSTER,
                CUSTOM_ZOMBIE,
                80, // spawn weight (daha yüksek = daha sık)
                1,  // min group
                3   // max group
        );*/

        // Custom SKELETON her biyomda gece doğsun
        BiomeModifications.addSpawn(
                context -> context.hasTag(BiomeTags.IS_OVERWORLD),
                SpawnGroup.MONSTER,
                CUSTOM_SKELETON,
                80,
                1,
                3
        );

        // Ayrıca onların gece gerçekten spawn olabilmesi için şuna da ihtiyacımız var:
    /*    SpawnRestriction.register(
                CUSTOM_ZOMBIE,
                com.kaplandev.entity.spawn.SpawnLocation.ON_GROUND,
                Heightmap.Type.MOTION_BLOCKING_NO_LEAVES,
                MobEntity::canMobSpawn
        );*/

        SpawnRestriction.register(
                CUSTOM_SKELETON,
                LUCK,
                Heightmap.Type.MOTION_BLOCKING_NO_LEAVES,
                MobEntity::canMobSpawn
        );

    }

    public static void registerAttributes() {
        FabricDefaultAttributeRegistry.register(BULWARK, BulwarkEntity.createAttributes());
    }
}
