package com.kaplandev.items.eggs;


import com.kaplandev.entity.EntitiyRegister;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.item.*;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;



public class SkeletonEggItem {
    public static final Item SKELETON_EGG = Registry.register(
            Registries.ITEM,
            Identifier.of("mobpvp", "skeleton_egg"),
            new SpawnEggItem(EntitiyRegister.CUSTOM_SKELETON, 0xAAAAAA, 0x555555, new Item.Settings())
    );

    public static void register() {
       /* ItemGroupEvents.modifyEntriesEvent(ItemGroups.SPAWN_EGGS).register(entries -> {
            entries.add(SKELETON_EGG);
        });*/
    }
}
