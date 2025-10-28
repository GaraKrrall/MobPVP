package com.kaplandev.item.group;

import com.kaplandev.item.ItemType;
import com.kaplandev.item.Items;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

import static com.kaplandev.mobpvp.MOD_ID;

public class BetaItemGroups {
    public static final RegistryKey<ItemGroup> BETA_BLOCKS_GROUP_KEY;
    public static final RegistryKey<ItemGroup> BETA_ITEMS_GROUP_KEY;
    public static final ItemGroup BETA_BLOCKS;
    public static final ItemGroup BETA_ITEMS;
    public static void init() {System.out.println("[DEBUG] BetaItemGroups is active!");}

    static {
        BETA_BLOCKS_GROUP_KEY = RegistryKey.of(Registries.ITEM_GROUP.getKey(), Identifier.of(MOD_ID, "beta_blocks"));
        BETA_ITEMS_GROUP_KEY = RegistryKey.of(Registries.ITEM_GROUP.getKey(), Identifier.of(MOD_ID, "beta_items"));
        BETA_BLOCKS = Registry.register(Registries.ITEM_GROUP, "beta_blocks", FabricItemGroup.builder().icon(() -> new ItemStack(ItemType.COPPER_STICK)).displayName(Text.literal("Beta Items")).entries((ctx, entries) -> {entries.add(ItemType.TEST_ITEM);}).build());
        BETA_ITEMS = Registry.register(Registries.ITEM_GROUP, "beta_items", FabricItemGroup.builder().icon(() -> new ItemStack(ItemType.REINFORCED_COPPER_BLOCK)).displayName(Text.literal("Beta Blocks")).entries((ctx, entries) -> {}).build());
    }
}
