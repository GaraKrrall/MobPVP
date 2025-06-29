package com.kaplandev.item.tab;

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
import static com.kaplandev.strings.path.Paths.TAB_MOBPVP_EGGS_KEY;
import static com.kaplandev.strings.path.Paths.TAB_MOBPVP_ITEMS_KEY;

public class Tabs {

    public static final RegistryKey<ItemGroup> MOBPVP_GROUP_KEY = RegistryKey.of(Registries.ITEM_GROUP.getKey(), Identifier.of(MOD_ID, TAB_MOBPVP_ITEMS_KEY));
    public static final ItemGroup MOBPVP_GROUP = Registry.register(Registries.ITEM_GROUP, MOBPVP_GROUP_KEY, FabricItemGroup.builder().icon(() -> new ItemStack(Items.KALP_ITEM)).displayName(Text.translatable("itemGroup.mobpvp")).entries((ctx, entries) -> {}).build());


    public static final RegistryKey<ItemGroup> EGGS_GROUP_KEY = RegistryKey.of(Registries.ITEM_GROUP.getKey(), Identifier.of(MOD_ID, TAB_MOBPVP_EGGS_KEY));
    public static final ItemGroup EGGS_GROUP = Registry.register(Registries.ITEM_GROUP, EGGS_GROUP_KEY, FabricItemGroup.builder().icon(() -> new ItemStack(Items.EGG_SKELETON)).displayName(Text.translatable("itemGroup.spawnEggs")).entries((ctx, entries) -> {}).build());


    public static void init() {

    }
}
