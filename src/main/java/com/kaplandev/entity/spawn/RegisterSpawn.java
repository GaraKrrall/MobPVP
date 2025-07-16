package com.kaplandev.entity.spawn;

import net.fabricmc.fabric.api.biome.v1.BiomeModifications;
import net.fabricmc.fabric.api.biome.v1.BiomeSelectionContext;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.entity.SpawnRestriction;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.world.Heightmap;

import java.util.function.Predicate;

public class RegisterSpawn {
    public static <T extends MobEntity> void registerSpawn(
            Predicate<BiomeSelectionContext> biomePredicate,
            SpawnGroup group,
            EntityType<T> entityType,
            int weight,
            int minGroupSize,
            int maxGroupSize,
            SpawnLocation location, // <-- Güncellendi
            Heightmap.Type heightmapType,
            SpawnRestriction.SpawnPredicate<T> spawnPredicate
    ) {
        BiomeModifications.addSpawn(biomePredicate, group, entityType, weight, minGroupSize, maxGroupSize);
        SpawnRestriction.register(entityType, location, heightmapType, spawnPredicate);
    }

}
