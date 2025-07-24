package com.kaplandev.item.group;

import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.registry.RegistryKey;

public class GroupItemBuilder {
    public static void AddGroup(Item item, RegistryKey<ItemGroup> tab) {
        ItemGroupEvents.modifyEntriesEvent(tab)
                .register(entries -> entries.add(item));
    }
}
