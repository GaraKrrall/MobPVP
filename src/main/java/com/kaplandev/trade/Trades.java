package com.kaplandev.trade;

import com.kaplandev.enchantment.EnchantmentGet;
import com.kaplandev.enchantment.list.EnchantmentList;
import com.kaplandev.villager.Villagers;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;
import net.fabricmc.fabric.api.object.builder.v1.trade.TradeOfferHelper;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.ItemEnchantmentsComponent;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentLevelEntry;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.Entity;
import net.minecraft.item.EnchantedBookItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.RegistryEntryLookup;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.random.Random;
import net.minecraft.village.TradeOffer;
import net.minecraft.village.TradedItem;
import net.minecraft.village.VillagerProfession;

import java.util.Optional;

import static com.kaplandev.mobpvp.MOD_ID;

public class Trades {

    private static RegistryEntryLookup<Enchantment> ENCHANTMENT_REG;

    public static void init() {
        // server başladığında enchantment registry'yi kaydet
        ServerLifecycleEvents.SERVER_STARTING.register(Trades::onServerStarting);
    }

    public static void register() {
        // Lookup kaynağını kaydet (server başlarken world registryden alınacak)



        TradeOfferHelper.registerVillagerOffers(
                VillagerProfession.LIBRARIAN,
                1,
                factories -> factories.add(Trades::create)
        );

        reg(1,  new TradeOffer(pay(Items.EMERALD, 1), out(Items.BREAD, 6),        24, 1, 0.02f));
        reg(1,  new TradeOffer(pay(Items.EMERALD, 4), out(Items.TORCH, 16),       24, 1, 0.02f));
        reg(1,  new TradeOffer(pay(Items.EMERALD, 1), out(Items.LEATHER, 4),      20, 2, 0.03f));
        reg(1,  new TradeOffer(pay(Items.EMERALD, 4), out(Items.COAL, 8),         20, 2, 0.03f));
        reg(1,  new TradeOffer(pay(Items.EMERALD, 2), out(Items.STICK, 16),       18, 2, 0.04f));
        reg(1,  new TradeOffer(pay(Items.EMERALD, 2), out(Items.ARROW, 16),       18, 3, 0.04f));
        reg2(1, pay(Items.EMERALD, 1), pay(Items.FLINT, 2), out(Items.STRING, 8), 16, 3, 0.05f);
        reg(1,  new TradeOffer(pay(Items.EMERALD, 8), out(Items.HONEY_BOTTLE, 2), 14, 3, 0.05f));
        reg(1,  new TradeOffer(pay(Items.EMERALD, 2), out(Items.SLIME_BALL, 2),   12, 4, 0.06f));
        reg2(1, pay(Items.EMERALD, 10), pay(Items.IRON_INGOT, 4), out(Items.REDSTONE, 8), 12, 4, 0.06f);
        reg(2, new TradeOffer(pay(Items.EMERALD, 8), out(Items.QUARTZ, 8),       12, 4, 0.07f));
        reg2(2, pay(Items.EMERALD, 3), pay(Items.GLOWSTONE_DUST, 2), out(Items.OBSIDIAN, 2), 8, 5, 0.08f);

        reg(2, new TradeOffer(pay(Items.EMERALD, 40), out(Items.ENDER_PEARL, 1),  2, 6, 0.10f));
        reg2(2, pay(Items.EMERALD, 4), pay(Items.BLAZE_POWDER, 1), out(Items.BLAZE_ROD, 1), 6, 7, 0.11f);
        reg(2, new TradeOffer(pay(Items.EMERALD, 12), out(Items.MAGMA_CREAM, 2),  6, 7, 0.11f));
        reg2(2, pay(Items.EMERALD, 8), pay(Items.GUNPOWDER, 4), out(Items.FIREWORK_ROCKET, 4), 8, 7, 0.12f);

        reg(2, new TradeOffer(pay(Items.EMERALD, 4), out(Items.NAUTILUS_SHELL, 1), 5, 8, 0.13f));
        reg2(2, pay(Items.EMERALD, 4), pay(Items.SPIDER_EYE, 2), out(Items.FERMENTED_SPIDER_EYE, 2), 6, 8, 0.13f);
        reg(2, new TradeOffer(pay(Items.EMERALD, 8), out(Items.GHAST_TEAR, 1),     4, 9, 0.15f));
        reg2(2, pay(Items.EMERALD, 4), pay(Items.GOLD_INGOT, 2), out(Items.GLISTERING_MELON_SLICE, 2), 6, 9, 0.15f);

        reg(3, new TradeOffer(pay(Items.EMERALD, 45), out(Items.PHANTOM_MEMBRANE, 1), 4, 10, 0.16f));
        reg2(3, pay(Items.EMERALD, 5), pay(Items.PUFFERFISH, 1), out(Items.TURTLE_SCUTE, 1), 3, 10, 0.17f);
        reg(3, new TradeOffer(pay(Items.EMERALD, 8), out(Items.SADDLE, 1),             3, 11, 0.18f));
        reg2(3, pay(Items.EMERALD, 14), pay(Items.TRIPWIRE_HOOK, 1), out(Items.NAME_TAG, 1), 3, 11, 0.18f);

        reg(3, new TradeOffer(pay(Items.DIAMOND, 2), out(Items.HEART_OF_THE_SEA, 1), 2, 14, 0.22f));
        reg2(3, pay(Items.EMERALD, 12), pay(Items.BLACKSTONE, 1), out(Items.BLACK_DYE, 1), 40, 14, 0.20f);
        reg(3, new TradeOffer(pay(Items.EMERALD, 19), out(Items.BREWING_STAND, 1), 5, 12, 0.19f));
        reg2(3, pay(Items.EMERALD, 6), pay(Items.OBSIDIAN, 2), out(Items.ENDER_CHEST, 1), 2, 16, 0.24f);

        reg(3, new TradeOffer(pay(Items.DIAMOND, 1), out(Items.SHULKER_SHELL, 1), 2, 16, 0.24f));
        reg2(3, pay(Items.EMERALD, 7), pay(Items.SHULKER_SHELL, 1), out(Items.SHULKER_BOX, 1), 2, 18, 0.26f);

        reg(4, new TradeOffer(pay(Items.EMERALD, 17), out(Items.EXPERIENCE_BOTTLE, 6), 3, 16, 0.22f));
        reg2(4, pay(Items.EMERALD, 7), pay(Items.PAPER, 6), out(Items.BOOK, 6), 6, 14, 0.20f);

        reg(4, new TradeOffer(pay(Items.EMERALD, 8), out(Items.OBSERVER, 2), 4, 16, 0.22f));
        reg2(4, pay(Items.EMERALD, 8), pay(Items.REDSTONE, 8), out(Items.DISPENSER, 1), 3, 16, 0.22f);
        reg(4, new TradeOffer(pay(Items.EMERALD, 8), out(Items.TINTED_GLASS, 8), 4, 16, 0.22f));
        reg2(5, pay(Items.EMERALD, 8), pay(Items.CRYING_OBSIDIAN, 1), out(Items.RESPAWN_ANCHOR, 1), 1, 20, 0.28f);

        reg(5, new TradeOffer(pay(Items.EMERALD, 10), book(Enchantments.UNBREAKING, 2), 3, 20, 0.25f));
        reg2(5, pay(Items.EMERALD, 12), pay(Items.LAPIS_LAZULI, 8), book(Enchantments.EFFICIENCY, 4), 2, 22, 0.28f);
        reg(5, new TradeOffer(pay(Items.DIAMOND, 1), book(EnchantmentGet.MAGMATIZATION, 4), 2, 24, 0.30f));
        reg(5, new TradeOffer(pay(Items.DIAMOND, 2), book(Enchantments.MENDING, 1), 1, 30, 0.35f));
    }

    private static TradeOffer create(Entity entity, Random random) {
        if (random.nextFloat() >= 0.01f) return null;

        ItemStack enchantedBook = new ItemStack(Items.ENCHANTED_BOOK);
        var registry = entity.getWorld().getRegistryManager().get(RegistryKeys.ENCHANTMENT);

        String[] enchantments = EnchantmentList.getAllNames();
        String selectedEnchantment = enchantments[random.nextInt(enchantments.length)];

        RegistryKey<Enchantment> key = RegistryKey.of(RegistryKeys.ENCHANTMENT,  Identifier.of(MOD_ID, selectedEnchantment));
        RegistryEntry<Enchantment> entry = registry.getEntry(key).orElseThrow(
                () -> new IllegalStateException("Enchantment not found: " + key.getValue())
        );

        ItemEnchantmentsComponent.Builder builder = new ItemEnchantmentsComponent.Builder(ItemEnchantmentsComponent.DEFAULT);
        builder.add(entry, 1);
        enchantedBook.set(DataComponentTypes.STORED_ENCHANTMENTS, builder.build());

        TradedItem price = new TradedItem(Items.EMERALD, 20);

        return new TradeOffer(price, enchantedBook, 1, 2, 0.3f);
    }

    private static void onServerStarting(MinecraftServer server) {
        ENCHANTMENT_REG = server.getRegistryManager().getWrapperOrThrow(RegistryKeys.ENCHANTMENT);
    }

    private static ItemStack book(RegistryKey<Enchantment> key, int level) {
        if (ENCHANTMENT_REG == null) {
            throw new IllegalStateException("Enchantment registry not initialized yet!");
        }

        RegistryEntry<Enchantment> entry = ENCHANTMENT_REG.getOptional(key)
                .orElseThrow(() -> new IllegalStateException("Enchantment not found: " + key.getValue()));

        return EnchantedBookItem.forEnchantment(new EnchantmentLevelEntry(entry, level));
    }


    private static TradedItem pay(Item item, int count) {
        return new TradedItem(item, count);
    }

    private static ItemStack out(Item item, int count) {
        return new ItemStack(item, count);
    }

    private static void reg(int level, TradeOffer offer) {
        TradeOfferHelper.registerVillagerOffers(Villagers.SECRET_TRADER, level, factories -> factories.add((entity, random) -> offer));
    }

    private static void reg(int level, TradedItem payment, ItemStack result, int uses, int xp, float priceMul) {
        TradeOfferHelper.registerVillagerOffers(Villagers.SECRET_TRADER, level, factories ->
                factories.add((entity, random) -> new TradeOffer(payment, Optional.empty(), result, uses, xp, priceMul)));
    }

    private static void reg2(int level, TradedItem firstPayment, TradedItem secondPayment, ItemStack result, int uses, int xp, float priceMul) {
        TradeOfferHelper.registerVillagerOffers(Villagers.SECRET_TRADER, level, factories ->
                factories.add((entity, random) -> new TradeOffer(firstPayment, Optional.of(secondPayment), result, uses, xp, priceMul)));
    }
}
