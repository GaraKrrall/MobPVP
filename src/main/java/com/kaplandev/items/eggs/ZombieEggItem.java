package com.kaplandev.items.eggs;

import com.kaplandev.entity.EntitiyRegister;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroups;
import net.minecraft.item.SpawnEggItem;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class ZombieEggItem {

    public static final Item ZOMBIE_EGG = Registry.register(
            Registries.ITEM,
            Identifier.of("mobpvp", "zombie_egg"),
            new SpawnEggItem(EntitiyRegister.CUSTOM_ZOMBIE, 0xAAAAAA, 0x555555, new Item.Settings())
    );

    public static void register() {
       /* ItemGroupEvents.modifyEntriesEvent(ItemGroups.SPAWN_EGGS).register(entries -> {
            entries.add(SKELETON_EGG);
        });*/
    }
}
