package com.kaplandev.items;

import com.kaplandev.mobpvp;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroups;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class ModItems {
    public static final KalpItem MY_ITEM = Registry.register(
            Registries.ITEM,
            Identifier.of("mobpvp:kalp"),  // ← YENİ SÜRÜMLERDE BU ŞART
            new KalpItem(new Item.Settings())     // ← Artık FabricItemSettings yok
    );

    public static void registerCreativeTab() {
        ItemGroupEvents.modifyEntriesEvent(ItemGroups.INGREDIENTS)
                .register(entries -> entries.add(MY_ITEM));
    }

    public static void init() {
        registerCreativeTab();
    }
}
