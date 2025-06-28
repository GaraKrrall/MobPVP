package com.kaplandev.block;


import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.SpawnEggItem;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.util.Identifier;

import static com.kaplandev.mobpvp.MOD_ID;
import static com.kaplandev.strings.path.Paths.*;


public class Blocks {

    // Block Tanımları
    public static final Block CRUDE_ACIDIC_ORE = registerBlock(CRUDE_ACIDIC_ORE_KEY, new Block(AbstractBlock.Settings.create().strength(4.0f, 4.0f).requiresTool().sounds(BlockSoundGroup.STONE)));


    public static void init() {


    }

    // Yardımcı fonksiyonlar
    public static Block registerBlock(String name, Block block) {
        // Bloğu kaydet
        Block registeredBlock = Registry.register(Registries.BLOCK, Identifier.of(MOD_ID, name), block);
        return registeredBlock;
    }



}
