package com.kaplandev.api.builder;

import com.kaplandev.api.spawn.SpawnLocation;

import net.fabricmc.fabric.api.biome.v1.BiomeModifications;
import net.fabricmc.fabric.api.biome.v1.BiomeSelectionContext;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricDefaultAttributeRegistry;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.entity.SpawnRestriction;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.world.Heightmap;

import java.util.function.Predicate;

public class EntityAttributeAndSpawnBuilder {
    public static <T extends LivingEntity> void BuildAttribute(EntityType<T> entityType, DefaultAttributeContainer.Builder attributes) {
        FabricDefaultAttributeRegistry.register(entityType, attributes);
    }
    public static <T extends MobEntity> void BuildSpawn(
            Predicate<BiomeSelectionContext> biomePredicate,
            SpawnGroup group,
            EntityType<T> entityType,
            int weight,
            int minGroupSize,
            int maxGroupSize,
            SpawnLocation location,
            Heightmap.Type heightmapType,
            SpawnRestriction.SpawnPredicate<T> spawnPredicate
    ) {
        BiomeModifications.addSpawn(biomePredicate, group, entityType, weight, minGroupSize, maxGroupSize);
        SpawnRestriction.register(entityType, location, heightmapType, spawnPredicate);
    }
}
