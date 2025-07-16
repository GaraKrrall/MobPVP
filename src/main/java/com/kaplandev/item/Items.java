package com.kaplandev.item;

import com.kaplandev.block.Blocks;
import com.kaplandev.entity.EntitiyRegister;
import com.kaplandev.item.feature.KalpItemFeatures;
import com.kaplandev.item.group.ItemGroups;

import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.SpawnEggItem;

import static com.kaplandev.item.group.GroupRegister.registerToTab;
import static com.kaplandev.item.util.ItemRegister.register;
import static com.kaplandev.util.path.Paths.EGG_SKELETON_KEY;
import static com.kaplandev.util.path.Paths.EGG_SUPER_ZOMBIE_KEY;
import static com.kaplandev.util.path.Paths.HEARTH_PART_KEY;
import static com.kaplandev.util.path.Paths.KALP_ITEM_KEY;
import static com.kaplandev.util.path.Paths.KEY_ITEM_KEY;
import static com.kaplandev.util.path.Paths.MOB_TABLE_ITEM_KEY;
import static com.kaplandev.util.path.Paths.ORE_ITEM_KEY;

public class Items {

    // Item tanımları
    public static final Item KALP_ITEM;
    @Deprecated(forRemoval = true)
    public static final Item KEY_ITEM;
    public static final Item ORE;
    public static final Item MOB_TABLE;
    public static final Item EGG_SKELETON;
    public static final Item EGG_SUPER_ZOMBIE;
    public static final Item HEARTH_PART;


    public static void init() {
        registerToTab(KALP_ITEM, ItemGroups.MOBPVP_GROUP_KEY);
        registerToTab(EGG_SKELETON, ItemGroups.EGGS_GROUP_KEY);
        registerToTab(EGG_SUPER_ZOMBIE, ItemGroups.EGGS_GROUP_KEY);
        registerToTab(HEARTH_PART, ItemGroups.MOBPVP_GROUP_KEY);
        registerToTab(MOB_TABLE, ItemGroups.MOBPVP_GROUP_KEY);
        registerToTab(ORE, ItemGroups.MOBPVP_GROUP_KEY);

    }

    static {
        KALP_ITEM = register(KALP_ITEM_KEY, new KalpItemFeatures(new Item.Settings()));
        /** Deprecated **/
        KEY_ITEM = register(KEY_ITEM_KEY, new Item(new Item.Settings()));
        ORE = register(ORE_ITEM_KEY, new BlockItem(Blocks.CRUDE_ACIDIC_ORE, new Item.Settings()));
        MOB_TABLE = register(MOB_TABLE_ITEM_KEY, new BlockItem(Blocks.MOB_TABLE, new Item.Settings()));
        EGG_SKELETON = register(EGG_SKELETON_KEY, new SpawnEggItem(EntitiyRegister.CUSTOM_SKELETON, 0xAAAAAA, 0x555555, new Item.Settings()));
        EGG_SUPER_ZOMBIE = register(EGG_SUPER_ZOMBIE_KEY, new SpawnEggItem(EntitiyRegister.CUSTOM_ZOMBIE, 0xAAAAAA, 0x555555, new Item.Settings()));
        HEARTH_PART = register(HEARTH_PART_KEY, new Item(new Item.Settings()));
    }
}
