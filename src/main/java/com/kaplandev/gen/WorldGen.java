package com.kaplandev.gen;

import com.kaplandev.build.ArenaFeature;
import com.kaplandev.gen.piece.VillagePiece;
import com.kaplandev.gen.piece.WaterPondPiece;
import com.kaplandev.gen.structure.House;
import com.kaplandev.gen.structure.PvETower;
import com.kaplandev.gen.structure.Village;
import com.kaplandev.gen.structure.WaterPondStructure;
import com.kaplandev.gen.piece.PvETowerPiece;
import com.kaplandev.gen.piece.HousePiece;

import net.fabricmc.fabric.api.biome.v1.BiomeModifications;
import net.fabricmc.fabric.api.biome.v1.BiomeSelectors;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.registry.tag.BiomeTags;
import net.minecraft.structure.StructurePieceType;
import net.minecraft.util.Identifier;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.GenerationStep;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.PlacedFeature;
import net.minecraft.world.gen.structure.StructureType;

import static com.kaplandev.util.path.Paths.MOBPVP;

public class WorldGen {

    // Arena
    public static final Identifier ARENA_ID = Identifier.of(MOBPVP, "arena_feature");
    public static final Feature<?> ARENA_FEATURE = new ArenaFeature();
    public static final RegistryKey<PlacedFeature> ARENA_PLACED_KEY = RegistryKey.of(RegistryKeys.PLACED_FEATURE, ARENA_ID);
    // Water Pond
    public static final Identifier WATER_POND_ID = Identifier.of(MOBPVP, "water_pond");
    public static final StructureType<WaterPondStructure> WATER_POND = () -> WaterPondStructure.CODEC;
    public static final StructurePieceType WATER_POND_PIECE = WaterPondPiece::new;//PVE TOWER
    public static final Identifier PVE_TOWER_ID = Identifier.of(MOBPVP, "pve_tower");
    public static final StructureType<PvETower> PVE_TOWER_POND = () -> PvETower.CODEC;
    public static final StructurePieceType PVE_TOWER_POND_PIECE = PvETowerPiece::new;
    //EV
    public static final StructurePieceType HOUSE_PIECE = HousePiece::new;
    //PVE TOWER
    public static final Identifier HOUSE_ID = Identifier.of(MOBPVP, "house");
    public static final StructureType<House> HOUSE_POND = () -> House.CODEC;
    public static final StructurePieceType HOUSE_POND_PIECE = HousePiece::new;
    //VILLAGE
    public static final Identifier VILLAGE_ID = Identifier.of(MOBPVP, "mini_village");
    public static final StructureType<Village> VILLAGE_POND = () -> Village.CODEC;
    public static final StructurePieceType VILLAGE_POND_PIECE = VillagePiece::new;




    public static void register() {
        // Arena Feature kaydı
        Registry.register(Registries.FEATURE, ARENA_ID, ARENA_FEATURE);

        // Structure kaydı
        Registry.register(Registries.STRUCTURE_TYPE, WATER_POND_ID, WATER_POND);
        Registry.register(Registries.STRUCTURE_PIECE, WATER_POND_ID, WATER_POND_PIECE);
        Registry.register(Registries.STRUCTURE_TYPE, PVE_TOWER_ID, PVE_TOWER_POND);
        Registry.register(Registries.STRUCTURE_PIECE, PVE_TOWER_ID, PVE_TOWER_POND_PIECE);
        Registry.register(Registries.STRUCTURE_TYPE, HOUSE_ID, HOUSE_POND);
        Registry.register(Registries.STRUCTURE_PIECE, HOUSE_ID, HOUSE_POND_PIECE);
        Registry.register(Registries.STRUCTURE_TYPE, VILLAGE_ID, VILLAGE_POND);
        Registry.register(Registries.STRUCTURE_PIECE, VILLAGE_ID, VILLAGE_POND_PIECE);

        // Arena placed feature -> JSON’da tanımlanmalı
        BiomeModifications.addFeature(
                BiomeSelectors.foundInOverworld()
                        .and(context -> {
                            RegistryEntry<Biome> biomeEntry = context.getBiomeRegistryEntry();
                            return !biomeEntry.isIn(BiomeTags.IS_OCEAN)
                                    && !biomeEntry.isIn(BiomeTags.IS_RIVER)
                                    && !biomeEntry.isIn(BiomeTags.IS_BEACH);
                        }),
                GenerationStep.Feature.SURFACE_STRUCTURES,
                ARENA_PLACED_KEY
        );


    }
}
