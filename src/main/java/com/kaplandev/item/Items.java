package com.kaplandev.item;

import com.kaplandev.block.BlockType;
import com.kaplandev.item.feature.IronReinforcedCopperBallItem;
import com.kaplandev.item.feature.KalpItem;
import com.kaplandev.item.feature.KnifeItem;
import com.kaplandev.item.feature.UltraHeathItem;
import com.kaplandev.item.feature.MaceItem;
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
import static com.kaplandev.item.ItemType.*;


public class Items {
    public static void init() {}

    static {
        ORE = GroupItemBuilder.create(ORE_ITEM_KEY, new BlockItem(BlockType.CRUDE_ACIDIC_ORE, new Item.Settings())).addToGroup(ItemGroups.MOBPVP_GROUP_KEY).register();
        MOB_TABLE = GroupItemBuilder.create(MOB_TABLE_ITEM_KEY, new BlockItem(BlockType.MOB_TABLE, new Item.Settings())).addToGroup(ItemGroups.MOBPVP_GROUP_KEY).register();
        PVP_SPAWNER_ITEM = GroupItemBuilder.create(PVP_SPAWNER_ITEM_KEY, new BlockItem(BlockType.PVP_SPAWNER, new Item.Settings())).addToGroup(ItemGroups.MOBPVP_GROUP_KEY).register();
        PVP_SPAWNER_MAX_ITEM = GroupItemBuilder.create(PVP_SPAWNER_MAX_ITEM_KEY, new BlockItem(BlockType.PVP_SPAWNER_MAX, new Item.Settings())).addToGroup(ItemGroups.MOBPVP_GROUP_KEY).register();
        DAMAGED_PVP_SPAWNER_ITEM = GroupItemBuilder.create(DAMAGED_PVP_SPAWNER_ITEM_KEY, new BlockItem(BlockType.DAMAGED_PVP_SPAWNER, new Item.Settings())).addToGroup(ItemGroups.MOBPVP_GROUP_KEY).register();
        DAMAGED_PVP_SPAWNER_MAX_ITEM = GroupItemBuilder.create(DAMAGED_PVP_SPAWNER_MAX_ITEM_KEY, new BlockItem(BlockType.DAMAGED_PVP_SPAWNER_MAX, new Item.Settings())).addToGroup(ItemGroups.MOBPVP_GROUP_KEY).register();
        REINFORCED_COPPER_BLOCK = GroupItemBuilder.create(REINFORCED_COPPER_BLOCK_KEY, new BlockItem(BlockType.REINFORCED_COPPER_BLOCK, new Item.Settings())).addToGroup(ItemGroups.MOBPVP_GROUP_KEY).register();
        HEAVY_CRUSHER_HEAD_ITEM = GroupItemBuilder.create("heavy_crusher_head", new BlockItem(BlockType.HEAVY_CRUSHER_HEAD, new Item.Settings())).addToGroup(ItemGroups.MOBPVP_GROUP_KEY).register();
        UPGREADED_HOPPER_ITEM = GroupItemBuilder.create("upgraded_hopper_item", new BlockItem(BlockType.UPGREADED_HOPPER, new Item.Settings())).addToGroup(ItemGroups.MOBPVP_GROUP_KEY).register();
        REINFORCED_COPPER_INGOT = GroupItemBuilder.create(REINFORCED_COPPER_INGOT_KEY, new Item(new Item.Settings())).addToGroup(ItemGroups.MOBPVP_GROUP_KEY).register();
        HEARTH_PART = GroupItemBuilder.create(HEARTH_PART_KEY, new Item(new Item.Settings())).addToGroup(ItemGroups.MOBPVP_GROUP_KEY).register();
        KALP_ITEM = GroupItemBuilder.create(KALP_ITEM_KEY, new KalpItem(new Item.Settings().rarity(Rarity.UNCOMMON))).addToGroup(ItemGroups.MOBPVP_GROUP_KEY).register();
        ULTRA_HEARTH_ITEM = GroupItemBuilder.create(ULTRA_HEARTH_KEY, new UltraHeathItem(new Item.Settings().rarity(Rarity.RARE))).addToGroup(ItemGroups.MOBPVP_GROUP_KEY).register();
        COPPER_STICK = GroupItemBuilder.create(COPPER_STICK_KEY, new BlockItem(BlockType.REINFORCED_COPPER_STICK, new Item.Settings())).addToGroup(ItemGroups.MOBPVP_GROUP_KEY).register();
        GOLD_MACE = GroupItemBuilder.create("gold_mace", new MaceItem(new Item.Settings().maxDamage(50).maxCount(1).rarity(Rarity.COMMON), 0.5F,  -3.5F, 0.8F, 0.1F)).addToGroup(ItemGroups.MOBPVP_GROUP_KEY).register();
        IRON_MACE = GroupItemBuilder.create("iron_mace", new MaceItem(new Item.Settings().maxDamage(150).maxCount(1).rarity(Rarity.RARE), 1.5F,  -3.5F, 2.2F, 0.2F)).addToGroup(ItemGroups.MOBPVP_GROUP_KEY).register();
        REINFORCED_COPPER_MACE = GroupItemBuilder.create(REINFORCED_COPPER_MACE_KEY, new MaceItem(new Item.Settings().maxDamage(200).maxCount(1).rarity(Rarity.RARE), 2.0F,  -3.5F, 2.8F, 0.5F)).addToGroup(ItemGroups.MOBPVP_GROUP_KEY).register();
        DIAMOND_MACE = GroupItemBuilder.create("diamond_mace", new MaceItem(new Item.Settings().maxDamage(250).maxCount(1).rarity(Rarity.RARE), 2.3F,  -2.5F, 3.0F, 0.7F)).addToGroup(ItemGroups.MOBPVP_GROUP_KEY).register();
        NETHERITE_MACE = GroupItemBuilder.create("netherite_mace", new MaceItem(new Item.Settings().maxDamage(300).maxCount(1).rarity(Rarity.EPIC), 2.8F,  -2.0F, 3.5F, 1.0F)).addToGroup(ItemGroups.MOBPVP_GROUP_KEY).register();
        REINFORCED_COPPER_KNIFE = GroupItemBuilder.create(REINFORCED_COPPER_KNIFE_KEY, new KnifeItem(new Item.Settings().maxCount(1).maxDamage(200), 5.0F, -2.0F)).addToGroup(ItemGroups.MOBPVP_GROUP_KEY).register();
        TEST_ITEM = GroupItemBuilder.create("test", new Item(new Item.Settings())).register();
        REINFORCED_COPPER_BALL = GroupItemBuilder.create(REINFORCED_COPPER_BALL_KEY, new IronReinforcedCopperBallItem(new Item.Settings())).addToGroup(ItemGroups.MOBPVP_GROUP_KEY).register();
        EGG_SKELETON = GroupItemBuilder.create(EGG_SKELETON_KEY, new SpawnEggItem(EntityType.MAD_SKELETON, 0xC1C1C1,0x3A3A3A, new Item.Settings())).addToGroup(ItemGroups.MOBPVP_GROUP_KEY).register();
        EGG_SUPER_ZOMBIE = GroupItemBuilder.create(EGG_SUPER_ZOMBIE_KEY, new SpawnEggItem(EntityType.MAD_ZOMBIE, 0xB71C1C, 0xFF5252, new Item.Settings())).addToGroup(ItemGroups.MOBPVP_GROUP_KEY).register();
        EGG_MINI_GOLEM = GroupItemBuilder.create(EGG_MINI_GOLEM_KEY, new SpawnEggItem(EntityType.MINIGOLEM, 0xD8D8D8, 0x8A8A8A, new Item.Settings())).addToGroup(ItemGroups.MOBPVP_GROUP_KEY).register();
        EGG_MINI_COPPER_GOLEM = GroupItemBuilder.create("egg_mini_copper_golem", new SpawnEggItem(EntityType.MINIGOLEM_COPPER, 0xCE7760, 0xA85E4D, new Item.Settings())).addToGroup(ItemGroups.MOBPVP_GROUP_KEY).register();
        EGG_BULWARK = GroupItemBuilder.create(EGG_BULWARK_KEY, new SpawnEggItem(EntityType.BULWARK, 0x880E4F, 0xFF1744, new Item.Settings().rarity(Rarity.EPIC))).addToGroup(ItemGroups.MOBPVP_GROUP_KEY).register();
    }
}
