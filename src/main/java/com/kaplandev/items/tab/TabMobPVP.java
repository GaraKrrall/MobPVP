package com.kaplandev.items.tab;

import com.kaplandev.items.KalpItem;
import com.kaplandev.items.blocks.CrudeAcidicLayerRockOre;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

import com.kaplandev.items.ModItems;

public class TabMobPVP {
    // RegistryKey ekleyin
    public static final RegistryKey<ItemGroup> MOBPVP_GROUP_KEY = RegistryKey.of(Registries.ITEM_GROUP.getKey(),
            Identifier.of("mobpvp", "itemsmobpvp"));

    public static final ItemGroup MOBPVP_GROUP = FabricItemGroup.builder()
            .icon(() -> new ItemStack(KalpItem.MY_ITEM))
            .displayName(Text.translatable("itemGroup.mobpvp"))
            .entries((context, entries) -> {
                entries.add(CrudeAcidicLayerRockOre.ORE);

            })
            .build();

    public static void registerItemGroups() {
        Registry.register(Registries.ITEM_GROUP, MOBPVP_GROUP_KEY, MOBPVP_GROUP);
    }
}