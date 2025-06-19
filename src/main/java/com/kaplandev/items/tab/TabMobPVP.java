package com.kaplandev.items.tab;

import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

import com.kaplandev.items.ModItems;

public class TabMobPVP {
    public static final ItemGroup MOBPVP_GROUP = FabricItemGroup.builder()
            .icon(() -> new ItemStack(ModItems.MY_ITEM)) // Sekme ikonu
            .displayName(Text.translatable("itemGroup.mobpvp")) // Dil dosyasında tanımlanacak
            .entries((context, entries) -> {
                // Bu sekmede gösterilecek öğeler
                entries.add(ModItems.MY_ITEM); // Kendi öğeniz

            })
            .build();

    // ItemGroup'ları kaydetme metodu
    public static void registerItemGroups() {
        Registry.register(Registries.ITEM_GROUP,
                Identifier.of("mobpvp", "itemsmobpvp"),
                MOBPVP_GROUP);
    }
}
