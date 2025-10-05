package com.kaplandev.level;

import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;

import java.util.function.Supplier;

public enum ArmorSet {
    NETHERITE(() -> new ItemStack[]{
            new ItemStack(Items.NETHERITE_HELMET),
            new ItemStack(Items.NETHERITE_CHESTPLATE),
            new ItemStack(Items.NETHERITE_LEGGINGS),
            new ItemStack(Items.NETHERITE_BOOTS)
    }),
    DIAMOND(() -> new ItemStack[]{
            new ItemStack(Items.DIAMOND_HELMET),
            new ItemStack(Items.DIAMOND_CHESTPLATE),
            new ItemStack(Items.DIAMOND_LEGGINGS),
            new ItemStack(Items.DIAMOND_BOOTS)
    }),
    IRON(() -> new ItemStack[]{
            new ItemStack(Items.IRON_HELMET),
            new ItemStack(Items.IRON_CHESTPLATE),
            new ItemStack(Items.IRON_LEGGINGS),
            new ItemStack(Items.IRON_BOOTS)
    });

    private final Supplier<ItemStack[]> armorSupplier;

    ArmorSet(Supplier<ItemStack[]> armorSupplier) {
        this.armorSupplier = armorSupplier;
    }

    public ItemStack[] getArmor() {
        return armorSupplier.get();
    }
}
