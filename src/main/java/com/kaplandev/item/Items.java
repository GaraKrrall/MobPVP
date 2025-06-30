package com.kaplandev.item;

import com.kaplandev.block.Blocks;
import com.kaplandev.entity.EntitiyRegister;
import com.kaplandev.item.feature.KalpItemFeatures;
import com.kaplandev.item.tab.Tabs;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.SpawnEggItem;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.util.Identifier;

import static com.kaplandev.mobpvp.MOD_ID;
import static com.kaplandev.strings.path.Paths.EGG_SKELETON_KEY;
import static com.kaplandev.strings.path.Paths.EGG_SUPER_ZOMBIE_KEY;
import static com.kaplandev.strings.path.Paths.HEARTH_PART_KEY;
import static com.kaplandev.strings.path.Paths.KALP_ITEM_KEY;
import static com.kaplandev.strings.path.Paths.KEY_ITEM_KEY;
import static com.kaplandev.strings.path.Paths.ORE_ITEM_KEY;


public class Items {

    // Item tanımları
    public static final Item KALP_ITEM = register(KALP_ITEM_KEY, new KalpItemFeatures(new Item.Settings()));
    public static final Item KEY_ITEM = register(KEY_ITEM_KEY, new Item(new Item.Settings()));
    public static final Item ORE = register(ORE_ITEM_KEY, new BlockItem(Blocks.CRUDE_ACIDIC_ORE, new Item.Settings()));
    public static final Item EGG_SKELETON = register(EGG_SKELETON_KEY, new SpawnEggItem(EntitiyRegister.CUSTOM_SKELETON, 0xAAAAAA, 0x555555, new Item.Settings()));
    public static final Item EGG_SUPER_ZOMBIE = register(EGG_SUPER_ZOMBIE_KEY, new SpawnEggItem(EntitiyRegister.CUSTOM_ZOMBIE, 0xAAAAAA, 0x555555, new Item.Settings()));
    public static final Item HEARTH_PART = register(HEARTH_PART_KEY, new Item(new Item.Settings()));


    public static void init() {
        // creative tab'a ekleme
        registerToTab(KALP_ITEM, Tabs.MOBPVP_GROUP_KEY);
        //registerToTab(KEY_ITEM, Tabs.MOBPVP_GROUP_KEY); //TODO: Başka bir zaman lazım olur!
        registerToTab(ORE, Tabs.MOBPVP_GROUP_KEY);
        registerToTab(EGG_SKELETON, Tabs.EGGS_GROUP_KEY);
        registerToTab(EGG_SUPER_ZOMBIE, Tabs.EGGS_GROUP_KEY);
        registerToTab(HEARTH_PART, Tabs.MOBPVP_GROUP_KEY);


        // diğer item ve block kayıtları


    }

    // Yardımcı fonksiyonlar
    private static Item register(String name, Item item) {
        return Registry.register(Registries.ITEM, Identifier.of(MOD_ID, name), item);
    }

    private static void registerToTab(Item item, RegistryKey<ItemGroup> tab) {
        ItemGroupEvents.modifyEntriesEvent(tab)
                .register(entries -> entries.add(item));
    }

}
