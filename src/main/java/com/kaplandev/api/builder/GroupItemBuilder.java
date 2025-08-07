package com.kaplandev.api.builder;

import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.util.Identifier;

import static com.kaplandev.mobpvp.MOD_ID;

public class GroupItemBuilder {
    public static void AddGroup(Item item, RegistryKey<ItemGroup> tab) {
        ItemGroupEvents.modifyEntriesEvent(tab)
                .register(entries -> entries.add(item));
    }
    public static Item BuildItem(String name, Item item) {
        return Registry.register(Registries.ITEM, Identifier.of(MOD_ID, name), item);
    }

}
