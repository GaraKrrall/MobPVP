package com.kaplandev.trade;

import com.kaplandev.enchantment.EnchantmentGet;
import com.kaplandev.enchantment.EnchantmentsAndEffects;
import com.kaplandev.enchantment.list.EnchantmentList;
import com.kaplandev.villager.Villagers;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;
import net.fabricmc.fabric.api.object.builder.v1.trade.TradeOfferHelper;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentLevelEntry;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.item.EnchantedBookItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.random.Random;
import net.minecraft.village.TradeOffer;
import net.minecraft.village.VillagerProfession;
import net.minecraft.village.TradeOffers;

import java.util.Optional;

import static com.kaplandev.mobpvp.MOD_ID;

public class Trades {

    private static net.minecraft.registry.Registry<Enchantment> ENCHANTMENT_REG;

    public static void init() {
        ServerLifecycleEvents.SERVER_STARTING.register(Trades::onServerStarting);
    }

    public static void register() {
        TradeOfferHelper.registerVillagerOffers(
                VillagerProfession.LIBRARIAN,
                1,
                factories -> factories.add(Trades::create)
        );

        reg(1, new TradeOffer(pay(Items.EMERALD, 1), out(Items.BREAD, 6), 24, 1, 0.02f));
        reg(1, new TradeOffer(pay(Items.EMERALD, 4), out(Items.TORCH, 16), 24, 1, 0.02f));
        reg(1, new TradeOffer(pay(Items.EMERALD, 1), out(Items.LEATHER, 4), 20, 2, 0.03f));
        reg(1, new TradeOffer(pay(Items.EMERALD, 4), out(Items.COAL, 8), 20, 2, 0.03f));
        reg(1, new TradeOffer(pay(Items.EMERALD, 2), out(Items.STICK, 16), 18, 2, 0.04f));
        reg(1, new TradeOffer(pay(Items.EMERALD, 2), out(Items.ARROW, 16), 18, 3, 0.04f));
        reg(1, new TradeOffer(pay(Items.EMERALD, 8), out(Items.HONEY_BOTTLE, 2), 14, 3, 0.05f));
        reg(1, new TradeOffer(pay(Items.EMERALD, 2), out(Items.SLIME_BALL, 2), 12, 4, 0.06f));
        reg(2, new TradeOffer(pay(Items.EMERALD, 8), out(Items.QUARTZ, 8), 12, 4, 0.07f));
        reg(2, new TradeOffer(pay(Items.EMERALD, 40), out(Items.ENDER_PEARL, 1), 2, 6, 0.10f));
        reg(2, new TradeOffer(pay(Items.EMERALD, 12), out(Items.MAGMA_CREAM, 2), 6, 7, 0.11f));
        reg(2, new TradeOffer(pay(Items.EMERALD, 4), out(Items.NAUTILUS_SHELL, 1), 5, 8, 0.13f));
        reg(2, new TradeOffer(pay(Items.EMERALD, 8), out(Items.GHAST_TEAR, 1), 4, 9, 0.15f));
        reg(3, new TradeOffer(pay(Items.EMERALD, 45), out(Items.PHANTOM_MEMBRANE, 1), 4, 10, 0.16f));
        reg(3, new TradeOffer(pay(Items.EMERALD, 8), out(Items.SADDLE, 1), 3, 11, 0.18f));
        reg(3, new TradeOffer(pay(Items.DIAMOND, 2), out(Items.HEART_OF_THE_SEA, 1), 2, 14, 0.22f));
        reg(3, new TradeOffer(pay(Items.EMERALD, 19), out(Items.BREWING_STAND, 1), 5, 12, 0.19f));
        reg(3, new TradeOffer(pay(Items.DIAMOND, 1), out(Items.SHULKER_SHELL, 1), 2, 16, 0.24f));
        reg(4, new TradeOffer(pay(Items.EMERALD, 17), out(Items.EXPERIENCE_BOTTLE, 6), 3, 16, 0.22f));
        reg(4, new TradeOffer(pay(Items.EMERALD, 8), out(Items.OBSERVER, 2), 4, 16, 0.22f));
        reg(4, new TradeOffer(pay(Items.EMERALD, 8), out(Items.TINTED_GLASS, 8), 4, 16, 0.22f));
        reg(5, new TradeOffer(pay(Items.EMERALD, 10), book(Enchantments.UNBREAKING, 2), 3, 20, 0.25f));
        reg(5, new TradeOffer(pay(Items.EMERALD, 12), book(Enchantments.EFFICIENCY, 4), 2, 22, 0.28f));
        reg(5, new TradeOffer(pay(Items.DIAMOND, 1), book(EnchantmentsAndEffects.MAGMATIZATION_STRIKE, 4), 2, 24, 0.30f));
        reg(5, new TradeOffer(pay(Items.DIAMOND, 2), book(Enchantments.MENDING, 1), 1, 30, 0.35f));
    }

    private static TradeOffer create(net.minecraft.entity.Entity entity, Random random) {
        if (random.nextFloat() >= 0.01f) return null;

        ItemStack enchantedBook = new ItemStack(Items.ENCHANTED_BOOK);
        String[] enchantments = EnchantmentList.getAllNames();
        String selectedEnchantment = enchantments[random.nextInt(enchantments.length)];

        Enchantment enchantment = ENCHANTMENT_REG.get(Identifier.of(MOD_ID, selectedEnchantment));
        if (enchantment == null) return null;

        EnchantedBookItem.addEnchantment(enchantedBook, new EnchantmentLevelEntry(enchantment, 1));
        ItemStack price = new ItemStack(Items.EMERALD, 20);

        return new TradeOffer(price, enchantedBook, 1, 2, 0.3f);
    }

    private static void onServerStarting(MinecraftServer server) {
        ENCHANTMENT_REG = server.getRegistryManager().get(RegistryKeys.ENCHANTMENT);
    }

    private static ItemStack book(Enchantment enchantment, int level) {
        ItemStack stack = new ItemStack(Items.ENCHANTED_BOOK);
        EnchantedBookItem.addEnchantment(stack, new EnchantmentLevelEntry(enchantment, level));
        return stack;
    }


    private static ItemStack pay(Item item, int count) {
        return new ItemStack(item, count);
    }

    private static ItemStack out(Item item, int count) {
        return new ItemStack(item, count);
    }

    private static void reg(int level, TradeOffer offer) {
        TradeOfferHelper.registerVillagerOffers(Villagers.SECRET_TRADER, level,
                factories -> factories.add((entity, random) -> offer));
    }
}
