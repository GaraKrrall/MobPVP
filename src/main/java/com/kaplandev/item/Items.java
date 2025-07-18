package com.kaplandev.item;

import com.kaplandev.block.Blocks;
import com.kaplandev.entity.EntitiyRegister;
import com.kaplandev.item.feature.KalpItemFeatures;
import com.kaplandev.item.group.ItemGroups;

import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.SpawnEggItem;
import net.minecraft.util.Rarity;

import static com.kaplandev.item.group.GroupRegister.registerToTab;
import static com.kaplandev.item.util.ItemRegister.register;
import static com.kaplandev.util.path.Paths.EGG_BULWARK_KEY;
import static com.kaplandev.util.path.Paths.EGG_MINI_GOLEM_KEY;
import static com.kaplandev.util.path.Paths.EGG_SKELETON_KEY;
import static com.kaplandev.util.path.Paths.EGG_SUPER_ZOMBIE_KEY;
import static com.kaplandev.util.path.Paths.HEARTH_PART_KEY;
import static com.kaplandev.util.path.Paths.KALP_ITEM_KEY;
import static com.kaplandev.util.path.Paths.MOB_TABLE_ITEM_KEY;
import static com.kaplandev.util.path.Paths.ORE_ITEM_KEY;

public class Items {
    // Item tanımları
    public static final Item ORE;
    public static final Item MOB_TABLE;
    public static final Item EGG_SKELETON;
    public static final Item EGG_SUPER_ZOMBIE;
    public static final Item EGG_MINI_GOLEM;
    public static final Item EGG_BULWARK;
    public static final Item HEARTH_PART;
    public static final Item KALP_ITEM;

    public static void init() {
        registerToTab(EGG_SKELETON, ItemGroups.EGGS_GROUP_KEY);
        registerToTab(EGG_MINI_GOLEM, ItemGroups.EGGS_GROUP_KEY);
        registerToTab(EGG_SUPER_ZOMBIE, ItemGroups.EGGS_GROUP_KEY);
        registerToTab(EGG_BULWARK, ItemGroups.EGGS_GROUP_KEY);
        registerToTab(KALP_ITEM, ItemGroups.MOBPVP_GROUP_KEY);
        registerToTab(HEARTH_PART, ItemGroups.MOBPVP_GROUP_KEY);
        registerToTab(MOB_TABLE, ItemGroups.MOBPVP_GROUP_KEY);
        registerToTab(ORE, ItemGroups.MOBPVP_GROUP_KEY);
    }

    static {
        ORE = register(ORE_ITEM_KEY, new BlockItem(Blocks.CRUDE_ACIDIC_ORE, new Item.Settings()));
        MOB_TABLE = register(MOB_TABLE_ITEM_KEY, new BlockItem(Blocks.MOB_TABLE, new Item.Settings()));
        EGG_SKELETON = register(EGG_SKELETON_KEY, new SpawnEggItem(EntitiyRegister.MAD_SKELETON, 0xC1C1C1,0x3A3A3A, new Item.Settings()));
        EGG_SUPER_ZOMBIE = register(EGG_SUPER_ZOMBIE_KEY, new SpawnEggItem(EntitiyRegister.MAD_ZOMBIE, 0xB71C1C, 0xFF5252, new Item.Settings()));
        EGG_MINI_GOLEM = register(EGG_MINI_GOLEM_KEY, new SpawnEggItem(EntitiyRegister.MINIGOLEM, 0xD8D8D8, 0x8A8A8A, new Item.Settings()));
        EGG_BULWARK = register(EGG_BULWARK_KEY, new SpawnEggItem(EntitiyRegister.BULWARK, 0x880E4F, 0xFF1744, new Item.Settings().rarity(Rarity.EPIC)));
        HEARTH_PART = register(HEARTH_PART_KEY, new Item(new Item.Settings()));
        KALP_ITEM = register(KALP_ITEM_KEY, new KalpItemFeatures(new Item.Settings().rarity(Rarity.RARE)));
    }
}
