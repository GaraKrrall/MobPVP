package com.kaplandev.gen;

import com.kaplandev.build.ArenaFeature;

import net.fabricmc.fabric.api.biome.v1.BiomeModifications;
import net.fabricmc.fabric.api.biome.v1.BiomeSelectors;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.registry.tag.BiomeTags;
import net.minecraft.util.Identifier;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.GenerationStep;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.PlacedFeature;

import static com.kaplandev.util.path.Paths.MOBPVP;

public class WorldGen {

    public static final Identifier ARENA_ID = Identifier.of(MOBPVP, "arena_feature");
    public static final Feature<?> ARENA_FEATURE = new ArenaFeature();

    public static void register() {
        // Feature kaydı (sadece bu, diğerleri JSON olacak!)
        Registry.register(Registries.FEATURE, ARENA_ID, ARENA_FEATURE);
        // PlacedFeature JSON ile tanımlandığı için sadece anahtarla biyomlara bağlanır
        RegistryKey<PlacedFeature> placedArenaKey = RegistryKey.of(RegistryKeys.PLACED_FEATURE, ARENA_ID);

        BiomeModifications.addFeature(
                BiomeSelectors.foundInOverworld()
                        .and(context -> {
                            RegistryEntry<Biome> biomeEntry = context.getBiomeRegistryEntry();
                            return !biomeEntry.isIn(BiomeTags.IS_OCEAN)
                                    && !biomeEntry.isIn(BiomeTags.IS_RIVER)
                                    && !biomeEntry.isIn(BiomeTags.IS_BEACH);
                        }),
                GenerationStep.Feature.SURFACE_STRUCTURES,
                RegistryKey.of(RegistryKeys.PLACED_FEATURE, ARENA_ID)
        );
    }
}
