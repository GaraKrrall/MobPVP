package com.kaplandev.item;

import com.kaplandev.block.Blocks;
import com.kaplandev.item.feature.KalpItem;
import com.kaplandev.item.feature.UltraHeathItem;
import com.kaplandev.item.group.GroupItemBuilder;
import com.kaplandev.item.group.ItemGroups;
import com.kaplandev.entity.EntityType;

import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.SpawnEggItem;
import net.minecraft.util.Rarity;

import static com.kaplandev.util.path.Paths.DAMAGED_PVP_SPAWNER_ITEM_KEY;
import static com.kaplandev.util.path.Paths.DAMAGED_PVP_SPAWNER_MAX_ITEM_KEY;
import static com.kaplandev.util.path.Paths.EGG_BULWARK_KEY;
import static com.kaplandev.util.path.Paths.EGG_MINI_GOLEM_KEY;
import static com.kaplandev.util.path.Paths.EGG_SKELETON_KEY;
import static com.kaplandev.util.path.Paths.EGG_SUPER_ZOMBIE_KEY;
import static com.kaplandev.util.path.Paths.HEARTH_PART_KEY;
import static com.kaplandev.util.path.Paths.IRON_CHEST_ITEM_KEY;
import static com.kaplandev.util.path.Paths.KALP_ITEM_KEY;
import static com.kaplandev.util.path.Paths.MOB_TABLE_ITEM_KEY;
import static com.kaplandev.util.path.Paths.ORE_ITEM_KEY;
import static com.kaplandev.util.path.Paths.PVP_SPAWNER_ITEM_KEY;
import static com.kaplandev.util.path.Paths.PVP_SPAWNER_MAX_ITEM_KEY;
import static com.kaplandev.util.path.Paths.REINFORCED_COPPER_BLOCK_KEY;
import static com.kaplandev.util.path.Paths.REINFORCED_COPPER_INGOT_KEY;
import static com.kaplandev.util.path.Paths.ULTRA_HEARTH_KEY;

public class Items {
    // Item tanımları
    public static final Item ORE;
    public static final Item MOB_TABLE;
    public static final Item EGG_SKELETON;
    public static final Item EGG_SUPER_ZOMBIE;
    public static final Item EGG_MINI_GOLEM;
    public static final Item EGG_BULWARK;
    public static final Item HEARTH_PART;
    public static final Item REINFORCED_COPPER_INGOT;
    public static final Item ULTRA_HEARTH_ITEM;
    public static final Item KALP_ITEM;
    public static final Item PVP_SPAWNER_ITEM;
    public static final Item PVP_SPAWNER_MAX_ITEM;
    public static final Item DAMAGED_PVP_SPAWNER_ITEM;
    public static final Item DAMAGED_PVP_SPAWNER_MAX_ITEM;
    public static final Item REINFORCED_COPPER_BLOCK;
    //public static final Item IRON_CHEST_ITEM;

    public static void init() {
        GroupItemBuilder.AddGroup(EGG_SKELETON, ItemGroups.EGGS_GROUP_KEY);
        GroupItemBuilder.AddGroup(EGG_MINI_GOLEM, ItemGroups.EGGS_GROUP_KEY);
        GroupItemBuilder.AddGroup(EGG_SUPER_ZOMBIE, ItemGroups.EGGS_GROUP_KEY);
        GroupItemBuilder.AddGroup(EGG_BULWARK, ItemGroups.EGGS_GROUP_KEY);
        GroupItemBuilder.AddGroup(KALP_ITEM, ItemGroups.MOBPVP_GROUP_KEY);
        GroupItemBuilder.AddGroup(HEARTH_PART, ItemGroups.MOBPVP_GROUP_KEY);
        GroupItemBuilder.AddGroup(ULTRA_HEARTH_ITEM, ItemGroups.MOBPVP_GROUP_KEY);
        GroupItemBuilder.AddGroup(REINFORCED_COPPER_INGOT, ItemGroups.MOBPVP_GROUP_KEY);
        GroupItemBuilder.AddGroup(REINFORCED_COPPER_BLOCK, ItemGroups.MOBPVP_GROUP_KEY);
        GroupItemBuilder.AddGroup(MOB_TABLE, ItemGroups.MOBPVP_GROUP_KEY);
        GroupItemBuilder.AddGroup(ORE, ItemGroups.MOBPVP_GROUP_KEY);
        GroupItemBuilder.AddGroup(PVP_SPAWNER_ITEM, ItemGroups.MOBPVP_GROUP_KEY);
        GroupItemBuilder.AddGroup(PVP_SPAWNER_MAX_ITEM, ItemGroups.MOBPVP_GROUP_KEY);
        GroupItemBuilder.AddGroup(DAMAGED_PVP_SPAWNER_ITEM, ItemGroups.MOBPVP_GROUP_KEY);
        GroupItemBuilder.AddGroup(DAMAGED_PVP_SPAWNER_MAX_ITEM, ItemGroups.MOBPVP_GROUP_KEY);
       // GroupItemBuilder.AddGroup(IRON_CHEST_ITEM, ItemGroups.MOBPVP_GROUP_KEY);
    }

    static {
        ORE = GroupItemBuilder.BuildItem(ORE_ITEM_KEY, new BlockItem(Blocks.CRUDE_ACIDIC_ORE, new Item.Settings()));
        MOB_TABLE = GroupItemBuilder.BuildItem(MOB_TABLE_ITEM_KEY, new BlockItem(Blocks.MOB_TABLE, new Item.Settings()));
        PVP_SPAWNER_ITEM = GroupItemBuilder.BuildItem(PVP_SPAWNER_ITEM_KEY, new BlockItem(Blocks.PVP_SPAWNER, new Item.Settings()));
        PVP_SPAWNER_MAX_ITEM = GroupItemBuilder.BuildItem(PVP_SPAWNER_MAX_ITEM_KEY, new BlockItem(Blocks.PVP_SPAWNER_MAX, new Item.Settings()));
        DAMAGED_PVP_SPAWNER_ITEM = GroupItemBuilder.BuildItem(DAMAGED_PVP_SPAWNER_ITEM_KEY, new BlockItem(Blocks.DAMAGED_PVP_SPAWNER, new Item.Settings()));
        DAMAGED_PVP_SPAWNER_MAX_ITEM = GroupItemBuilder.BuildItem(DAMAGED_PVP_SPAWNER_MAX_ITEM_KEY, new BlockItem(Blocks.DAMAGED_PVP_SPAWNER_MAX, new Item.Settings()));
        REINFORCED_COPPER_BLOCK = GroupItemBuilder.BuildItem(REINFORCED_COPPER_BLOCK_KEY, new BlockItem(Blocks.REINFORCED_COPPER_BLOCK, new Item.Settings()));
       // IRON_CHEST_ITEM = GroupItemBuilder.BuildItem(IRON_CHEST_ITEM_KEY, new BlockItem(Blocks.IRON_CHEST, new Item.Settings()));
        EGG_SKELETON = GroupItemBuilder.BuildItem(EGG_SKELETON_KEY, new SpawnEggItem(EntityType.MAD_SKELETON, 0xC1C1C1,0x3A3A3A, new Item.Settings()));
        EGG_SUPER_ZOMBIE = GroupItemBuilder.BuildItem(EGG_SUPER_ZOMBIE_KEY, new SpawnEggItem(EntityType.MAD_ZOMBIE, 0xB71C1C, 0xFF5252, new Item.Settings()));
        EGG_MINI_GOLEM = GroupItemBuilder.BuildItem(EGG_MINI_GOLEM_KEY, new SpawnEggItem(EntityType.MINIGOLEM, 0xD8D8D8, 0x8A8A8A, new Item.Settings()));
        EGG_BULWARK = GroupItemBuilder.BuildItem(EGG_BULWARK_KEY, new SpawnEggItem(EntityType.BULWARK, 0x880E4F, 0xFF1744, new Item.Settings().rarity(Rarity.EPIC)));
        HEARTH_PART = GroupItemBuilder.BuildItem(HEARTH_PART_KEY, new Item(new Item.Settings()));
        REINFORCED_COPPER_INGOT = GroupItemBuilder.BuildItem(REINFORCED_COPPER_INGOT_KEY, new Item(new Item.Settings()));
        KALP_ITEM = GroupItemBuilder.BuildItem(KALP_ITEM_KEY, new KalpItem(new Item.Settings().rarity(Rarity.UNCOMMON)));
        ULTRA_HEARTH_ITEM = GroupItemBuilder.BuildItem(ULTRA_HEARTH_KEY, new UltraHeathItem(new Item.Settings().rarity(Rarity.RARE)));
    }
}
