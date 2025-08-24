package com.kaplandev.item;

import com.kaplandev.block.Blocks;
import com.kaplandev.item.feature.IronReinforcedCopperBallItem;
import com.kaplandev.item.feature.KalpItem;
import com.kaplandev.item.feature.ReinforcedCopperKnifeItem;
import com.kaplandev.item.feature.UltraHeathItem;
import com.kaplandev.item.feature.ReinforcedCopperMaceItem;
import com.kaplandev.item.group.ItemGroups;
import com.kaplandev.entity.EntityType;

import com.kaplanlib.api.builder.GroupItemBuilder;

import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.SpawnEggItem;
import net.minecraft.util.Rarity;

import static com.kaplanlib.util.path.Paths.COPPER_STICK_KEY;
import static com.kaplanlib.util.path.Paths.DAMAGED_PVP_SPAWNER_ITEM_KEY;
import static com.kaplanlib.util.path.Paths.DAMAGED_PVP_SPAWNER_MAX_ITEM_KEY;
import static com.kaplanlib.util.path.Paths.EGG_BULWARK_KEY;
import static com.kaplanlib.util.path.Paths.EGG_MINI_GOLEM_KEY;
import static com.kaplanlib.util.path.Paths.EGG_SKELETON_KEY;
import static com.kaplanlib.util.path.Paths.EGG_SUPER_ZOMBIE_KEY;
import static com.kaplanlib.util.path.Paths.HEARTH_PART_KEY;
import static com.kaplanlib.util.path.Paths.KALP_ITEM_KEY;
import static com.kaplanlib.util.path.Paths.MOB_TABLE_ITEM_KEY;
import static com.kaplanlib.util.path.Paths.ORE_ITEM_KEY;
import static com.kaplanlib.util.path.Paths.PVP_SPAWNER_ITEM_KEY;
import static com.kaplanlib.util.path.Paths.PVP_SPAWNER_MAX_ITEM_KEY;
import static com.kaplanlib.util.path.Paths.REINFORCED_COPPER_BALL_KEY;
import static com.kaplanlib.util.path.Paths.REINFORCED_COPPER_BLOCK_KEY;
import static com.kaplanlib.util.path.Paths.REINFORCED_COPPER_INGOT_KEY;
import static com.kaplanlib.util.path.Paths.REINFORCED_COPPER_KNIFE_KEY;
import static com.kaplanlib.util.path.Paths.REINFORCED_COPPER_MACE_KEY;
import static com.kaplanlib.util.path.Paths.ULTRA_HEARTH_KEY;


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
    public static final Item COPPER_STICK;
    //public static final Item IRON_CHEST_ITEM;
    public static final Item REINFORCED_COPPER_MACE;
    public static final Item REINFORCED_COPPER_KNIFE;
    public static final Item TEST_ITEM;
    public static final Item REINFORCED_COPPER_BALL;
    public static void init() {}

    static {
        ORE = GroupItemBuilder.create(ORE_ITEM_KEY, new BlockItem(Blocks.CRUDE_ACIDIC_ORE, new Item.Settings())).addToGroup(ItemGroups.MOBPVP_GROUP_KEY).register();
        MOB_TABLE = GroupItemBuilder.create(MOB_TABLE_ITEM_KEY, new BlockItem(Blocks.MOB_TABLE, new Item.Settings())).register();
        PVP_SPAWNER_ITEM = GroupItemBuilder.create(PVP_SPAWNER_ITEM_KEY, new BlockItem(Blocks.PVP_SPAWNER, new Item.Settings())).addToGroup(ItemGroups.MOBPVP_GROUP_KEY).register();
        PVP_SPAWNER_MAX_ITEM = GroupItemBuilder.create(PVP_SPAWNER_MAX_ITEM_KEY, new BlockItem(Blocks.PVP_SPAWNER_MAX, new Item.Settings())).addToGroup(ItemGroups.MOBPVP_GROUP_KEY).register();
        DAMAGED_PVP_SPAWNER_ITEM = GroupItemBuilder.create(DAMAGED_PVP_SPAWNER_ITEM_KEY, new BlockItem(Blocks.DAMAGED_PVP_SPAWNER, new Item.Settings())).addToGroup(ItemGroups.MOBPVP_GROUP_KEY).register();
        DAMAGED_PVP_SPAWNER_MAX_ITEM = GroupItemBuilder.create(DAMAGED_PVP_SPAWNER_MAX_ITEM_KEY, new BlockItem(Blocks.DAMAGED_PVP_SPAWNER_MAX, new Item.Settings())).addToGroup(ItemGroups.MOBPVP_GROUP_KEY).register();
        REINFORCED_COPPER_BLOCK = GroupItemBuilder.create(REINFORCED_COPPER_BLOCK_KEY, new BlockItem(Blocks.REINFORCED_COPPER_BLOCK, new Item.Settings())).addToGroup(ItemGroups.MOBPVP_GROUP_KEY).register();
       // IRON_CHEST_ITEM = GroupItemBuilder.BuildItem(IRON_CHEST_ITEM_KEY, new BlockItem(Blocks.IRON_CHEST, new Item.Settings()));
        EGG_SKELETON = GroupItemBuilder.create(EGG_SKELETON_KEY, new SpawnEggItem(EntityType.MAD_SKELETON, 0xC1C1C1,0x3A3A3A, new Item.Settings())).addToGroup(ItemGroups.EGGS_GROUP_KEY).register();
        EGG_SUPER_ZOMBIE = GroupItemBuilder.create(EGG_SUPER_ZOMBIE_KEY, new SpawnEggItem(EntityType.MAD_ZOMBIE, 0xB71C1C, 0xFF5252, new Item.Settings())).addToGroup(ItemGroups.EGGS_GROUP_KEY).register();
        EGG_MINI_GOLEM = GroupItemBuilder.create(EGG_MINI_GOLEM_KEY, new SpawnEggItem(EntityType.MINIGOLEM, 0xD8D8D8, 0x8A8A8A, new Item.Settings())).addToGroup(ItemGroups.EGGS_GROUP_KEY).register();
        EGG_BULWARK = GroupItemBuilder.create(EGG_BULWARK_KEY, new SpawnEggItem(EntityType.BULWARK, 0x880E4F, 0xFF1744, new Item.Settings().rarity(Rarity.EPIC))).addToGroup(ItemGroups.EGGS_GROUP_KEY).register();
        HEARTH_PART = GroupItemBuilder.create(HEARTH_PART_KEY, new Item(new Item.Settings())).addToGroup(ItemGroups.MOBPVP_GROUP_KEY).register();
        REINFORCED_COPPER_INGOT = GroupItemBuilder.create(REINFORCED_COPPER_INGOT_KEY, new Item(new Item.Settings())).addToGroup(ItemGroups.MOBPVP_GROUP_KEY).register();
        KALP_ITEM = GroupItemBuilder.create(KALP_ITEM_KEY, new KalpItem(new Item.Settings().rarity(Rarity.UNCOMMON))).addToGroup(ItemGroups.MOBPVP_GROUP_KEY).register();
        ULTRA_HEARTH_ITEM = GroupItemBuilder.create(ULTRA_HEARTH_KEY, new UltraHeathItem(new Item.Settings().rarity(Rarity.RARE))).addToGroup(ItemGroups.MOBPVP_GROUP_KEY).register();
        COPPER_STICK = GroupItemBuilder.create(COPPER_STICK_KEY, new BlockItem(Blocks.REINFORCED_COPPER_STICK, new Item.Settings())).addToGroup(ItemGroups.MOBPVP_GROUP_KEY).register();
        REINFORCED_COPPER_MACE = GroupItemBuilder.create(REINFORCED_COPPER_MACE_KEY, new ReinforcedCopperMaceItem(new Item.Settings().maxDamage(250).maxCount(1).rarity(Rarity.RARE), 2.0F,  -3.5F, 2.8F, 0.5F)).addToGroup(ItemGroups.MOBPVP_GROUP_KEY).register();
        REINFORCED_COPPER_KNIFE = GroupItemBuilder.create(REINFORCED_COPPER_KNIFE_KEY, new ReinforcedCopperKnifeItem(new Item.Settings().maxCount(1).maxDamage(200), 5.0F, -2.0F)).addToGroup(ItemGroups.MOBPVP_GROUP_KEY).register();
        TEST_ITEM = GroupItemBuilder.create("test", new Item(new Item.Settings())).register();
        REINFORCED_COPPER_BALL = GroupItemBuilder.create(REINFORCED_COPPER_BALL_KEY, new IronReinforcedCopperBallItem(new Item.Settings())).addToGroup(ItemGroups.MOBPVP_GROUP_KEY).register();
    }
}
