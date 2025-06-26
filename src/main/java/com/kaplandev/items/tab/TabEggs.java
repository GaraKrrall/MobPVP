package com.kaplandev.items.tab;


import com.kaplandev.items.eggs.SkeletonEggItem;
import com.kaplandev.items.eggs.ZombieEggItem;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

public class TabEggs {

    public static final RegistryKey<ItemGroup> EGGS_GROUP_KEY = RegistryKey.of(Registries.ITEM_GROUP.getKey(),
            Identifier.of("mobpvp", "eggsmobpvp"));

    public static final ItemGroup EGGS_GROUP = FabricItemGroup.builder()
            .icon(() -> new ItemStack(SkeletonEggItem.SKELETON_EGG))
            .displayName(Text.translatable("itemGroup.spawnEggs"))
            .entries((context, entries) -> {
                entries.add(SkeletonEggItem.SKELETON_EGG);
                entries.add(ZombieEggItem.ZOMBIE_EGG);

            })
            .build();

    public static void registerItemGroups() {
        Registry.register(Registries.ITEM_GROUP, EGGS_GROUP_KEY, EGGS_GROUP);
    }
}
