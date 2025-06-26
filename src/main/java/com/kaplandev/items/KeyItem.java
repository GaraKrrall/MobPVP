package com.kaplandev.items;

import com.kaplandev.items.tab.TabMobPVP;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class KeyItem {
        public static final Item KEY_ITEM = Registry.register(
                Registries.ITEM,
                Identifier.of("mobpvp:key"),  // ← YENİ SÜRÜMLERDE BU ŞART
                new Item(new Item.Settings())     // ← Artık FabricItemSettings yok
        );

        public static void registerCreativeTab() {
            ItemGroupEvents.modifyEntriesEvent(TabMobPVP.MOBPVP_GROUP_KEY)
                    .register(entries -> entries.add(KEY_ITEM));
        }

        public static void init() {
            registerCreativeTab();
        }
}


