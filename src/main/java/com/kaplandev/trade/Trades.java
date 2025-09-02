package com.kaplandev.trade;

import com.kaplandev.enchantment.EnchantmentEffects;
import net.fabricmc.fabric.api.object.builder.v1.trade.TradeOfferHelper;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.ItemEnchantmentsComponent;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentLevelEntry;
import net.minecraft.entity.Entity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtList;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.random.Random;
import net.minecraft.village.TradeOffer;
import net.minecraft.village.TradedItem;
import net.minecraft.village.VillagerProfession;


public class Trades {
    public static void register() {
        TradeOfferHelper.registerVillagerOffers(
                VillagerProfession.LIBRARIAN,
                1, // köylü seviyesi
                factories -> factories.add(Trades::create)
        );
    }
    private static TradeOffer create(Entity entity, Random random) {
        // %5 ihtimalle bu trade eklenir
        if (random.nextFloat() >= 0.05f) return null;

        ItemStack enchantedBook = new ItemStack(Items.ENCHANTED_BOOK);
        var registry = entity.getWorld().getRegistryManager().get(RegistryKeys.ENCHANTMENT);

        // Büyülerin listesi
        String[] enchantments = {"thundering", "certainty"}; // ikinci büyü örnek

        // Rastgele bir büyü seç
        String selectedEnchantment = enchantments[random.nextInt(enchantments.length)];

        RegistryKey<Enchantment> key = RegistryKey.of(RegistryKeys.ENCHANTMENT, Identifier.of("mobpvp", selectedEnchantment));
        RegistryEntry<Enchantment> entry = registry.getEntry(key).orElseThrow(
                () -> new IllegalStateException("Enchantment not found: " + key.getValue())
        );

        ItemEnchantmentsComponent.Builder builder = new ItemEnchantmentsComponent.Builder(ItemEnchantmentsComponent.DEFAULT);
        builder.add(entry, 1); // seviye 1
        enchantedBook.set(DataComponentTypes.STORED_ENCHANTMENTS, builder.build());

        TradedItem price = new TradedItem(Items.EMERALD, 20);

        return new TradeOffer(price, enchantedBook, 1, 2, 0.3f); // çok nadir
    }


}

