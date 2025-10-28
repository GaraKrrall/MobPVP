package com.kaplandev.villager;

import com.google.common.collect.ImmutableSet;
import com.kaplandev.block.BlockType;
import com.kaplandev.block.Blocks;
import net.fabricmc.fabric.api.object.builder.v1.world.poi.PointOfInterestHelper;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.Identifier;
import net.minecraft.village.VillagerProfession;
import net.minecraft.world.poi.PointOfInterestType;

import static com.kaplandev.mobpvp.MOD_ID;

public class Villagers {

    public static final RegistryKey<PointOfInterestType> SECRET_TRADER_POI_KEY =
            RegistryKey.of(RegistryKeys.POINT_OF_INTEREST_TYPE, Identifier.of(MOD_ID, "secret_trader_poi"));

    public static final PointOfInterestType SECRET_TRADER_POI = PointOfInterestHelper.register(
             Identifier.of(MOD_ID, "secret_trader_poi"),
            1, 1,
            BlockType.MOB_TABLE
    );

    public static final VillagerProfession SECRET_TRADER = Registry.register(
            Registries.VILLAGER_PROFESSION,
             Identifier.of(MOD_ID, "secret_trader"),
            new VillagerProfession(
                    "secret_trader",
                    entry -> entry.matchesKey(SECRET_TRADER_POI_KEY),
                    entry -> entry.matchesKey(SECRET_TRADER_POI_KEY),
                    ImmutableSet.of(),
                    ImmutableSet.of(),
                    SoundEvents.ENTITY_VILLAGER_WORK_LIBRARIAN
            )
    );

    public static void registerVillagers() {
        System.out.println("Registered custom villager professions for " + MOD_ID);
    }
}
