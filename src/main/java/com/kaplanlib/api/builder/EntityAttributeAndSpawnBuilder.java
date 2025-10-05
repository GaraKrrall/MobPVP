package com.kaplanlib.api.builder;

import com.kaplanlib.api.spawn.SpawnLocation;
import net.fabricmc.fabric.api.biome.v1.BiomeModifications;
import net.fabricmc.fabric.api.biome.v1.BiomeSelectionContext;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricDefaultAttributeRegistry;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.entity.SpawnRestriction;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.world.Heightmap;

import java.util.function.Predicate;

public class EntityAttributeAndSpawnBuilder<T extends MobEntity> {

    private final EntityType<T> entityType;
    private DefaultAttributeContainer.Builder attributes;
    private Predicate<BiomeSelectionContext> biomePredicate;
    private SpawnGroup group;
    private int weight;
    private int minGroupSize;
    private int maxGroupSize;
    private SpawnLocation location;
    private Heightmap.Type heightmapType;
    private SpawnRestriction.SpawnPredicate<T> spawnPredicate;

    private EntityAttributeAndSpawnBuilder(EntityType<T> entityType) {
        this.entityType = entityType;
    }

    public static <T extends MobEntity> EntityAttributeAndSpawnBuilder<T> create(EntityType<T> entityType) {
        return new EntityAttributeAndSpawnBuilder<>(entityType);
    }

    public EntityAttributeAndSpawnBuilder<T> attributes(DefaultAttributeContainer.Builder attributes) {
        this.attributes = attributes;
        return this;
    }

    public EntityAttributeAndSpawnBuilder<T> spawn(
            Predicate<BiomeSelectionContext> biomePredicate,
            SpawnGroup group,
            int weight,
            int minGroupSize,
            int maxGroupSize,
            SpawnLocation location,
            Heightmap.Type heightmapType,
            SpawnRestriction.SpawnPredicate<T> spawnPredicate
    ) {
        this.biomePredicate = biomePredicate;
        this.group = group;
        this.weight = weight;
        this.minGroupSize = minGroupSize;
        this.maxGroupSize = maxGroupSize;
        this.location = location;
        this.heightmapType = heightmapType;
        this.spawnPredicate = spawnPredicate;
        return this;
    }

    public void build() {
        if (attributes != null) {
            FabricDefaultAttributeRegistry.register(entityType, attributes);
        }
        if (biomePredicate != null && group != null && location != null && heightmapType != null && spawnPredicate != null) {
            BiomeModifications.addSpawn(biomePredicate, group, entityType, weight, minGroupSize, maxGroupSize);
            SpawnRestriction.register(entityType, location, heightmapType, spawnPredicate);
        }
    }
}
